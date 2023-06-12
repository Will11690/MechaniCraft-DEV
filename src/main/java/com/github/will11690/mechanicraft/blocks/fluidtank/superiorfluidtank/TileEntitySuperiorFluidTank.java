package com.github.will11690.mechanicraft.blocks.fluidtank.superiorfluidtank;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySuperiorFluidTank extends TileEntity implements ITickableTileEntity {

	private FluidTank fluidTank = createFluidTank();
	private final LazyOptional<IFluidHandler> fluidHandler  = LazyOptional.of(() -> fluidTank);
	
    public static final int capacity = ModConfigs.superiorTankCapacityInt;

    public TileEntitySuperiorFluidTank() {
		
		super(TileEntityHandler.TILE_ENTITY_SUPERIOR_FLUID_TANK.get());
		
	}
	
	private FluidTank createFluidTank() {
		
		return new FluidTank(capacity) {
			
			@Override
            protected void onContentsChanged() {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
            }
			
			@Override
		    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
				
				return true;
		    }
		};
	}

	@Override
	public void tick() {
		if (this.level == null || this.level.isClientSide) {

			return;
		}
		
		BlockPos neighborPosDown = worldPosition.below();
		TileEntity neighborEntity = level.getBlockEntity(neighborPosDown);
		
		if(this.getBlockState().getValue(SuperiorFluidTank.OUTPUT_BOTTOM) == Boolean.valueOf(true)) {
		
			if(neighborEntity != null) {
			
				if(neighborEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent()) {
				
					if(this.fluidHandler.isPresent()) {
						
						LazyOptional<IFluidHandler> neighborFluidHandlerCap = neighborEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
						IFluidHandler neighborFluidHandler = neighborFluidHandlerCap.orElseThrow(IllegalStateException::new);
						IFluidHandler outputFluidHandler = fluidHandler.orElseThrow(IllegalStateException::new);
					
						if(fluidTank.getFluidInTank(0).getAmount() > 0) {
							
							FluidUtil.tryFluidTransfer(neighborFluidHandler, outputFluidHandler, Math.min(fluidTank.getFluidInTank(0).getAmount(), FluidAttributes.BUCKET_VOLUME * 8), true);
						}
					}
				}
			}
		}
	}
	
	void setOutputBottomTrue() {
		
		if(this.getBlockState().getValue(SuperiorFluidTank.OUTPUT_BOTTOM) == Boolean.valueOf(false)) {
			
			this.level.setBlockAndUpdate(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(SuperiorFluidTank.OUTPUT_BOTTOM, Boolean.valueOf(true)));
		}
	}
	
	void setOutputBottomFalse() {
		
		if(this.getBlockState().getValue(SuperiorFluidTank.OUTPUT_BOTTOM) == Boolean.valueOf(true)) {
			
			this.level.setBlockAndUpdate(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(SuperiorFluidTank.OUTPUT_BOTTOM, Boolean.valueOf(false)));
		}
	}

    @Override
    public void load(BlockState state, CompoundNBT tags) {
    	
        super.load(state, tags);
        if(tags.contains("superiorFluidTank", Constants.NBT.TAG_COMPOUND)) {
			
            CompoundNBT fluidTankTag = tags.getCompound("superiorFluidTank");
            this.fluidTank.readFromNBT(fluidTankTag);
        }
        
        tags.getBoolean("autoOutput");
    }
	
	@Override
    public CompoundNBT save(CompoundNBT tags) {
    	
        super.save(tags);
        CompoundNBT fluidTankTag = new CompoundNBT();
        this.fluidTank.writeToNBT(fluidTankTag);
        tags.put("superiorFluidTank", fluidTankTag);
        
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
        CompoundNBT fluidTankTag = new CompoundNBT();
		
		this.fluidTank.writeToNBT(fluidTankTag);
        tags.put("superiorFluidTank", fluidTankTag);
        
        this.save(tags);
        
        return tags;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tags) {
		
		this.fluidTank.readFromNBT(tags);
        tags.get("superiorFluidTank");
        
        this.load(state, tags);
        
    	super.handleUpdateTag(state, tags);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
    	
    	fluidTank.readFromNBT(packet.getTag().getCompound("superiorFluidTank"));
    	
        this.load(this.getBlockState(), packet.getTag());
        this.level.getBlockTicks().scheduleTick(this.worldPosition, this.getBlockState().getBlock(), 100);
        super.onDataPacket(net, packet);
    }

	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {

		if (!this.remove) {

			if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {

				return fluidHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {

		fluidHandler.invalidate();
		super.setRemoved();
	}

	public World getWorld() {
		
		return this.level;
	}
}