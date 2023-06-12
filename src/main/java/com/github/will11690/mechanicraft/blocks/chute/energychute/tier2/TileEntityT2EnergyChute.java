package com.github.will11690.mechanicraft.blocks.chute.energychute.tier2;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier2.CapabilityT2EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier2.T2EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

public class TileEntityT2EnergyChute extends TileEntity implements ITickableTileEntity {
	
	private MechaniCraftEnergyStorage energyStorage = createEnergy();
	private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	
	//TODO Add config options
	
	private static int capacity = 8192;
	private static int transfer = 8192;
	
	public TileEntityT2EnergyChute() {
		
		super(TileEntityHandler.TILE_ENTITY_T2_ENERGY_CHUTE.get());
	}
	
	private MechaniCraftEnergyStorage createEnergy() {

		return new MechaniCraftEnergyStorage(capacity, transfer) {

			@Override
			protected void onEnergyChanged() {

				setChanged();
			}
		};
	}
	
	@Override
	public void tick() {
		
		if(this.level == null || this.level.isClientSide) {

			return;
		}

		this.updateEnergyStorage();

		if(energyStorage.getEnergyStored() == 0) {
			return;
		}
		
		for(Direction direction : Direction.values()) {
			
			if(canConnectBlock(direction)) {
				
				sendPower(direction);
			}
		}
	}

	private void updateEnergyStorage() {
		
		if(energy.isPresent()) {
				
			energyStorage.updateEnergyStorageNoUpgrades(capacity, transfer, transfer);
		}
	}
	
	private void sendPower(Direction direction) {
		
		TileEntity neighborTile = level.getBlockEntity(worldPosition.relative(direction));
		LazyOptional<IEnergyStorage> neighborStorageCap = neighborTile.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite());
		
		if(neighborStorageCap.isPresent()) {
			
			IEnergyStorage neighborStorage = neighborStorageCap.orElseThrow(IllegalStateException::new);
			
			if(neighborStorage.canReceive()) {
				
				int energyTransfer = energyStorage.extractEnergy(Math.min(neighborStorage.getMaxEnergyStored() - neighborStorage.getEnergyStored(), Math.min(energyStorage.getMaxExtract(), energyStorage.getEnergyStored())), true);
			
				neighborStorage.receiveEnergy(energyTransfer, false);
				energyStorage.extractEnergy(energyTransfer, false);
			}
		}
	}
	
	private boolean canConnectBlock(Direction direction) {
			
		TileEntity tile = level.getBlockEntity(worldPosition.relative(direction));
			
		if(tile != null && !(tile instanceof TileEntityT2EnergyChute) && tile.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent()) {
				
			return true;
		}
		return false;
	}
	
	@Override
	public void onLoad() {
		
		LazyOptional<T2EnergyChuteNetworkList> t2ChuteNetworkCap = level.getCapability(CapabilityT2EnergyChuteNetwork.T2_NETWORK_LIST);
		
		if(t2ChuteNetworkCap.isPresent()) {
			T2EnergyChuteNetworkList t2ChuteNetwork = t2ChuteNetworkCap.orElseThrow(IllegalStateException::new);
			t2ChuteNetwork.addConnection(this.worldPosition);
		}
		super.onLoad();
	}
	
	@Override
	public void load(BlockState state, CompoundNBT tags) {
	
		super.load(state, tags);
		this.energyStorage.deserializeNBT(tags.getCompound("energy"));
	}
	
	@Override
	public CompoundNBT save(CompoundNBT tags) {
	
		super.save(tags);
		tags.put("energy", energyStorage.serializeNBT());
		return tags;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
	
		CompoundNBT tags = this.getUpdateTag();
		return new SUpdateTileEntityPacket(this.worldPosition, 1, tags);
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return save(new CompoundNBT());
	}

	@Override
	public void handleUpdateTag(BlockState stateIn, CompoundNBT tag) {
		load(stateIn, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		load(this.getBlockState(), pkt.getTag());
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {

		if (!this.remove) {

			if (cap == CapabilityEnergy.ENERGY) {

				return energy.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {

		energy.invalidate();
		super.setRemoved();
	}
}