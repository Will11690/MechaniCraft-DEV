package com.github.will11690.mechanicraft.blocks.chute.universalchute.tier1;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;
import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

public class TileEntityT1UniversalChute /*extends TileEntity implements ITickableTileEntity*/ {
	
	private ItemStackHandler items = createItemHandler();
	private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> items);
	
	private MechaniCraftEnergyStorage energyStorage = createEnergyHandler();
	private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	
	private FluidTank fluidTank = createFluidHandler();
	private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> fluidTank);
	
	//TODO implement item transfer
	//TODO implement fluid transfer
	//TODO implement energy transfer
	//TODO create network to draw connection paths
	
	/*public TileEntityT1UniversalChute() {
		
		super(TileEntityHandler.TILE_ENTITY_T1_UNIVERSAL_CHUTE.get());
		
	}*/
	
	private ItemStackHandler createItemHandler() {
		
		return new ItemStackHandler(1) {
		
			@Override
			protected void onContentsChanged(int slot) {
				//BlockState state = level.getBlockState(worldPosition);
				//level.sendBlockUpdated(worldPosition, state, state, 3);
				//setChanged();
			}
		
			@Override
			public int getSlotLimit(int slot) {
			
				return 1;
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
					
				return true;
			
			}
		};
	}
	
	private MechaniCraftEnergyStorage createEnergyHandler() {

		return new MechaniCraftEnergyStorage(40960, 4096) {

			@Override
			protected void onEnergyChanged() {

				//setChanged();
			}
		};
	}
	
	private FluidTank createFluidHandler() {
		
		return new FluidTank(1000) {
			
			@Override
			protected void onContentsChanged() {
				
				//setChanged();
			}
			
			@Override
			public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
					
				return true;
			}
		};
	}

	/*@Override
	public void tick() {
		
		if (this.level == null || this.level.isClientSide) {

			return;
		}
		
		BlockPos neighborPosUp = worldPosition.above();
		BlockState neighborStateUp = level.getBlockState(neighborPosUp);
		
		BlockPos neighborPosDown = worldPosition.below();
		BlockState neighborStateDown = level.getBlockState(neighborPosDown);
		
		BlockPos neighborPosNorth = worldPosition.north();
		BlockState neighborStateNorth = level.getBlockState(neighborPosNorth);
		
		BlockPos neighborPosSouth = worldPosition.south();
		BlockState neighborStateSouth = level.getBlockState(neighborPosSouth);
		
		BlockPos neighborPosEast = worldPosition.east();
		BlockState neighborStateEast = level.getBlockState(neighborPosEast);
		
		BlockPos neighborPosWest = worldPosition.west();
		BlockState neighborStateWest = level.getBlockState(neighborPosWest);
		
		if(canConnectItem()) {
			
			if(!(neighborStateUp.getBlock().equals(Blocks.AIR)) && neighborStateUp.hasTileEntity()) { 
				
				if(level.getBlockEntity(neighborPosUp).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).isPresent()) {
			
					IItemHandler neighborItemUp = level.getBlockEntity(neighborPosUp).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).orElse(null);
				
					if(neighborItemUp != null && !(neighborStateUp.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
						//receiveItem();
					
					} else if(neighborItemUp != null) {
					
						//sendItem();
					}
				}
			}
			
			if(!(neighborStateDown.getBlock().equals(Blocks.AIR)) && neighborStateDown.hasTileEntity()) {
				
				if(level.getBlockEntity(neighborPosDown).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
			
					IItemHandler neighborItemDown = level.getBlockEntity(neighborPosDown).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElse(null);
				
					if(neighborItemDown != null && !(neighborStateDown.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
						//receiveItem();
					
					} else if(neighborItemDown != null) {
					
						//sendItem();
					}
				}
			}
			
			if(!(neighborStateNorth.getBlock().equals(Blocks.AIR)) && neighborStateNorth.hasTileEntity()) {
				
					if(level.getBlockEntity(neighborPosNorth).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).isPresent()) {
			
						IItemHandler neighborItemNorth = level.getBlockEntity(neighborPosNorth).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.NORTH).orElse(null);
				
						if(neighborItemNorth != null && !(neighborStateNorth.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
							//receiveItem();
					
						} else if(neighborItemNorth != null) {
					
							//sendItem();
						}
					}
			}
			
			if(!(neighborStateSouth.getBlock().equals(Blocks.AIR)) && neighborStateSouth.hasTileEntity()) {
				
				if(level.getBlockEntity(neighborPosSouth).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.SOUTH).isPresent()) {
			
					IItemHandler neighborItemSouth = level.getBlockEntity(neighborPosSouth).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.SOUTH).orElse(null);
				
					if(neighborItemSouth != null && !(neighborStateSouth.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
						//receiveItem();
					
					} else if(neighborItemSouth != null) {
					
						//sendItem();
					}
				}
			}
			
			if(!(neighborStateEast.getBlock().equals(Blocks.AIR)) && neighborStateEast.hasTileEntity()) {
				
				if(level.getBlockEntity(neighborPosEast).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.EAST).isPresent()) {
			
					IItemHandler neighborItemEast = level.getBlockEntity(neighborPosEast).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.EAST).orElse(null);
				
					if(neighborItemEast != null && !(neighborStateEast.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
						//receiveItem();
					
					} else if(neighborItemEast != null) {
					
						//sendItem();
					}
				}
			}
			
			if(!(neighborStateWest.getBlock().equals(Blocks.AIR)) && neighborStateWest.hasTileEntity()) {
				
				if(level.getBlockEntity(neighborPosWest).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.WEST).isPresent()) {
			
				
					IItemHandler neighborItemWest = level.getBlockEntity(neighborPosWest).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.WEST).orElse(null);
				
					if(neighborItemWest != null && !(neighborStateWest.getBlock().equals(ModBlocks.T1_ITEM_CHUTE.get()))) {
					
						//receiveItem();
					
					} else if(neighborItemWest != null) {
					
						//sendItem();
					}
				}
			}
		}
	}

	public boolean canConnectItem() {
		
		if(T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.UP) || T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.UP) ||
			T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.DOWN) || T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.DOWN) ||
			T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.NORTH) || T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.NORTH) ||
			T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.SOUTH) || T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.SOUTH) ||
			T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.EAST) ||T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.EAST) ||
			T1UniversalChute.canChuteConnectChute(level, worldPosition, Direction.WEST) || T1UniversalChute.canChuteConnectBlock(level, worldPosition, Direction.WEST)) {
			
			return true;
			
		} else
			
			return false;
	}
	
	private void receiveItem() {
		
		//TODO Item Transport
	}

	private void sendItem() {
		
		//TODO Item Transport
	}

	@Override
	public void load(BlockState state, CompoundNBT tags) {
	
		super.load(state, tags);
		this.items.deserializeNBT(tags.getCompound("items"));
	}
	
	@Override
	public CompoundNBT save(CompoundNBT tags) {
	
		super.save(tags);
		tags.put("items", items.serializeNBT());
		return tags;
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
	
		CompoundNBT tags = this.getUpdateTag();
		this.save(tags);
		return new SUpdateTileEntityPacket(this.worldPosition, 1, tags);
	}

	@Override
	public CompoundNBT getUpdateTag() {
	
		CompoundNBT tags = new CompoundNBT();
		this.save(tags);

		return tags;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tags) {

		this.load(state, tags);

		super.handleUpdateTag(state, tags);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
	
		this.load(this.getBlockState(), packet.getTag());
		this.level.getBlockTicks().scheduleTick(this.worldPosition, this.getBlockState().getBlock(), 100);
		super.onDataPacket(net, packet);
	}

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {

		if (!this.remove) {

			if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

				return itemHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {

		itemHandler.invalidate();
		super.setRemoved();
	}*/
}