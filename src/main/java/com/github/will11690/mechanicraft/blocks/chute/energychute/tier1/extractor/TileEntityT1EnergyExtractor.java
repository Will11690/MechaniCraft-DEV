package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;
import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityT1EnergyExtractor extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
    private MechaniCraftEnergyStorage energyStorage = createEnergy();
    
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    
    private Map<Direction, TileEntity> connections = new HashMap<>();
    
	//TODO SET THESE TO CONCRETE VALUES UNTIL CONFIGS ARE ADDED
    private static int capacity = ModConfigs.t1EnergyCubeCapacityInt;
    static int transfer = ModConfigs.t1EnergyCubeTransferInt;
    
    private int upTransfer = 0;
    private int downTransfer = 0;
    private int northTransfer = 0;
    private int southTransfer = 0;
    private int eastTransfer = 0;
    private int westTransfer = 0;
    
    private int upPriority = 0;
    private int downPriority = 0;
    private int northPriority = 0;
    private int southPriority = 0;
    private int eastPriority = 0;
    private int westPriority = 0;
    
    private boolean upEnabled;
    private boolean downEnabled;
    private boolean northEnabled;
    private boolean southEnabled;
    private boolean eastEnabled;
    private boolean westEnabled;
    
    private boolean redstoneHigh = false;
    private boolean redstoneLow = false;
    private boolean comparatorHigh = false;
    private boolean comparatorLow = false;
    
    private boolean configured = false;
    //TODO move transfer to dedicated clientbound packet to sidestep 16bit limit
    //TODO if energy network is connected set comparator modes and capacity to track the network else track the block it is connected to(SHIFT WILL SHOW CAPACITY IN BLOCK)
    
   private final IIntArray fields = new IIntArray() {
    	
        @Override
        public int get(int index) {
        	
            switch (index) {
            
    			case 0:
        			return worldPosition.getX();
    			case 1:
        			return worldPosition.getY();
    			case 2:
        			return worldPosition.getZ();
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
        	
            switch (index) {
            
            	default:
            		break;
            }
        }

        @Override
        public int getCount() {
        	
            return 4;
        }
    };
    
    private MechaniCraftEnergyStorage createEnergy() {
    	
        return new MechaniCraftEnergyStorage(capacity) {
        	
            @Override
            protected void onEnergyChanged() {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
            }
        };
    }
    
    public TileEntityT1EnergyExtractor() {
    	
        super(TileEntityHandler.TILE_ENTITY_T1_ENERGY_EXTRACTOR.get());
    }
    
	@Override
    public void tick() {
    	
        if (this.level == null || this.level.isClientSide) {
        	
            return;
        }
        
        //if(energyStorage.getEnergyStored() == 0 /*|| get network that neighbor is connected to and check if energy is empty*/) {
        	
        	//return;
        //}
    	
        this.updateEnergyStorage();
        this.configureList();
        //TODO set up priority to determine where to draw power from first
        
        if(connections.isEmpty()) {
        	
        	return;
        }
        
        if(connections.containsKey(Direction.UP) && upEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.UP);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), upTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.receiveEnergy(energyToReceive, false);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.DOWN) && downEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.DOWN);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.UP);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), downTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.addEnergy(energyToReceive);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.NORTH) && northEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.NORTH);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), northTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.addEnergy(energyToReceive);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.SOUTH) && southEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.SOUTH);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), southTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.addEnergy(energyToReceive);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.EAST) && eastEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.EAST);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.WEST);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), eastTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.addEnergy(energyToReceive);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.WEST) && westEnabled == true) {
        	
        	TileEntity tile = connections.get(Direction.WEST);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.EAST);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getCapacity() - energyStorage.getEnergyStored() > 0) {
        		
        			int energyToReceive = tileEnergyStorage.extractEnergy(Math.min(tileEnergyStorage.getEnergyStored(), westTransfer), true);
        			tileEnergyStorage.extractEnergy(energyToReceive, false);
        			energyStorage.addEnergy(energyToReceive);
        		}
        	}
        }
    }
	
	public void setSideEnabled(int sideSelectID, int extractorToggle) {
		//TODO
		if(sideSelectID == 1) {
			
			if(extractorToggle == 0) {
				
				upEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				upEnabled = false;
				configured = true;
				setChanged();
			}
		}
		
		if(sideSelectID == 2) {
			
			if(extractorToggle == 0) {
				
				downEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				downEnabled = false;
				configured = true;
				setChanged();
			}
		}
		
		if(sideSelectID == 3) {
			
			if(extractorToggle == 0) {
				
				northEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				northEnabled = false;
				configured = true;
				setChanged();
			}
		}
		
		if(sideSelectID == 4) {
			
			if(extractorToggle == 0) {
				
				southEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				southEnabled = false;
				configured = true;
				setChanged();
			}
		}
		
		if(sideSelectID == 5) {
			
			if(extractorToggle == 0) {
				
				eastEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				eastEnabled = false;
				configured = true;
				setChanged();
			}
		}
		
		if(sideSelectID == 6) {
			
			if(extractorToggle == 0) {
				
				westEnabled = true;
				configured = true;
				setChanged();
				
			} else {
				
				westEnabled = false;
				configured = true;
				setChanged();
			}
		}
	}
	
	public void setSideTransfer(int sideSelectID, int setTransfer) {
		//TODO
		if(setTransfer > transfer) {
			
			setTransfer = transfer;
		}
		
		if(setTransfer < 0) {
			
			setTransfer = 0;
		}
		
		if(sideSelectID == 1) {
			
			upTransfer = setTransfer;
			setChanged();
		}
		
		if(sideSelectID == 2) {
			
			downTransfer = setTransfer;
			setChanged();
		}
		
		if(sideSelectID == 3) {
			
			northTransfer = setTransfer;
			setChanged();
		}
		
		if(sideSelectID == 4) {
			
			southTransfer = setTransfer;
			setChanged();
		}
		
		if(sideSelectID == 5) {
			
			eastTransfer = setTransfer;
			setChanged();
		}
		
		if(sideSelectID == 6) {
			
			westTransfer = setTransfer;
			setChanged();
		}
	}
	
	public void setSidePriority(int sideSelectID, int setPriority) {
		//TODO
		if(setPriority > 1000) {
			
			setPriority = 1000;
		}
		
		if(setPriority < 0) {
			
			setPriority = 0;
		}
		
		if(sideSelectID == 1) {
			
			upPriority = setPriority;
			setChanged();
		}
		
		if(sideSelectID == 2) {
			
			downPriority = setPriority;
			setChanged();
		}
		
		if(sideSelectID == 3) {
			
			northPriority = setPriority;
			setChanged();
		}
		
		if(sideSelectID == 4) {
			
			southPriority = setPriority;
			setChanged();
		}
		
		if(sideSelectID == 5) {
			
			eastPriority = setPriority;
			setChanged();
		}
		
		if(sideSelectID == 6) {
			
			westPriority = setPriority;
			setChanged();
		}
	}
	
	public void setRedstoneMode(int redstoneMode) {
		//TODO
		if(redstoneMode == 0) {
			
			redstoneHigh = false;
			redstoneLow = false;
		}
		
		if(redstoneMode == 1) {
			
			redstoneHigh = true;
			redstoneLow = false;
		}
		
		if(redstoneMode == 2) {
			
			redstoneHigh = false;
			redstoneLow = true;
		}
	}
	
	public void setComparatorMode(int comparatorMode) {
		//TODO
		
		if(comparatorMode == 0) {
			
			comparatorHigh = false;
			comparatorLow = false;
		}
		
		if(comparatorMode == 1) {
			
			comparatorHigh = true;
			comparatorLow = false;
		}
		
		if(comparatorMode == 2) {
			
			comparatorHigh = false;
			comparatorLow = true;
		}
	}
	
	public int networkEnergyStored() {
		//TODO
		return 0;
	}
	
	public int networkEnergyCapacity() {
		//TODO
		return 0;
	}
	
	public boolean isUpEnabled() {
		
		return upEnabled;
	}
	
	public boolean isDownEnabled() {
		
		return downEnabled;
	}
	
	public boolean isNorthEnabled() {
		
		return northEnabled;
	}
	
	public boolean isSouthEnabled() {
		
		return southEnabled;
	}
	
	public boolean isEastEnabled() {
		
		return eastEnabled;
	}
	
	public boolean isWestEnabled() {
		
		return westEnabled;
	}
	
	public boolean getRedstoneHigh() {
		
		return redstoneHigh;
	}
	
	public boolean getRedstoneLow() {
		
		return redstoneLow;
	}
	
	public boolean getComparatorHigh() {
		
		return comparatorHigh;
	}
	
	public boolean getComparatorLow() {
		
		return comparatorLow;
	}
	
	public int getUpTransfer() {
		
		return upTransfer;
	}
	
	public int getDownTransfer() {
		
		return downTransfer;
	}
	
	public int getNorthTransfer() {
		
		return northTransfer;
	}
	
	public int getSouthTransfer() {
		
		return southTransfer;
	}
	
	public int getEastTransfer() {
		
		return eastTransfer;
	}
	
	public int getWestTransfer() {
		
		return westTransfer;
	}
	
	public int getUpPriority() {
		
		return upPriority;
	}
	
	public int getDownPriority() {
		
		return downPriority;
	}
	
	public int getNorthPriority() {
		
		return northPriority;
	}
	
	public int getSouthPriority() {
		
		return southPriority;
	}
	
	public int getEastPriority() {
		
		return eastPriority;
	}
	
	public int getWestPriority() {
		
		return westPriority;
	}
	
	@Override
	public void onLoad() {
		
		if(configured == false) {
			
			this.upEnabled = true;
			this.downEnabled = true;
			this.northEnabled = true;
			this.southEnabled = true;
			this.eastEnabled = true;
			this.westEnabled = true;
		}
		//LazyOptional<T1EnergyCubeNetworkList> t1CubeNetworkCap = level.getCapability(CapabilityT1EnergyCubeNetwork.T1_NETWORK_LIST);
		
		//if(t1CubeNetworkCap.isPresent()) {
			//T1EnergyCubeNetworkList t1CubeNetwork = t1CubeNetworkCap.orElseThrow(IllegalStateException::new);
			//t1CubeNetwork.addConnection(this.worldPosition);
		//}
		//super.onLoad();
	}

	private void updateEnergyStorage() {
		
		if(energy.isPresent()) {
				
			energyStorage.updateEnergyStorageNoUpgrades(capacity);
		}
	}
    
	private void configureList() {
		
		//TODO remove System.out testing comments
		
		TileEntity tileUp = level.getBlockEntity(worldPosition.relative(Direction.UP));
		TileEntity tileDown = level.getBlockEntity(worldPosition.relative(Direction.DOWN));
		TileEntity tileNorth = level.getBlockEntity(worldPosition.relative(Direction.NORTH));
		TileEntity tileSouth = level.getBlockEntity(worldPosition.relative(Direction.SOUTH));
		TileEntity tileEast = level.getBlockEntity(worldPosition.relative(Direction.EAST));
		TileEntity tileWest = level.getBlockEntity(worldPosition.relative(Direction.WEST));
                
		if(!(connections.containsKey(Direction.UP)) && tileUp != null && tileUp.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).isPresent()) {
			
			if(!(tileUp instanceof TileEntityT1EnergyExtractor)) {
			
				connections.put(Direction.UP, tileUp);
				//System.out.println("Connection UP added to list!");
			}
		}
        
		if(!(connections.containsKey(Direction.DOWN)) && tileDown != null && tileDown.getCapability(CapabilityEnergy.ENERGY, Direction.UP).isPresent()) {
	
			if(!(tileDown instanceof TileEntityT1EnergyExtractor)) {
	
				connections.put(Direction.DOWN, tileDown);
				//System.out.println("Connection DOWN added to list!");
			}
		}
        
		if(!(connections.containsKey(Direction.NORTH)) && tileNorth != null && tileNorth.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH).isPresent()) {
	
			if(!(tileNorth instanceof TileEntityT1EnergyExtractor)) {
	
				connections.put(Direction.NORTH, tileNorth);
				//System.out.println("Connection NORTH added to list!");
			}
		}
        
		if(!(connections.containsKey(Direction.SOUTH)) && tileSouth != null && tileSouth.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).isPresent()) {
	
			if(!(tileSouth instanceof TileEntityT1EnergyExtractor)) {
	
				connections.put(Direction.SOUTH, tileSouth);
				//System.out.println("Connection SOUTH added to list!");
			}
		}
        
		if(!(connections.containsKey(Direction.EAST)) && tileEast != null && tileEast.getCapability(CapabilityEnergy.ENERGY, Direction.WEST).isPresent()) {
	
			if(!(tileEast instanceof TileEntityT1EnergyExtractor)) {
	
				connections.put(Direction.EAST, tileEast);
				//System.out.println("Connection EAST added to list!");
			}
		}
        
		if(!(connections.containsKey(Direction.WEST)) && tileWest != null && tileWest.getCapability(CapabilityEnergy.ENERGY, Direction.EAST).isPresent()) {
	
			if(!(tileWest instanceof TileEntityT1EnergyExtractor)) {
	
				connections.put(Direction.WEST, tileWest);
				//System.out.println("Connection WEST added to list!");
			}
		}
		
		if(connections.containsKey(Direction.UP) && tileUp != connections.get(Direction.UP)) {
			
			TileEntity tile = connections.get(Direction.UP);
			connections.remove(Direction.UP, tile);
			//System.out.println("Connection UP removed from list");
		}
		
		if(connections.containsKey(Direction.DOWN) && tileDown != connections.get(Direction.DOWN)) {
			
			TileEntity tile = connections.get(Direction.DOWN);
			connections.remove(Direction.DOWN, tile);
			//System.out.println("Connection DOWN removed from list");
		}
		
		if(connections.containsKey(Direction.NORTH) && tileNorth != connections.get(Direction.NORTH)) {
			
			TileEntity tile = connections.get(Direction.NORTH);
			connections.remove(Direction.NORTH, tile);
			//System.out.println("Connection NORTH removed from list");
		}
		
		if(connections.containsKey(Direction.SOUTH) && tileSouth != connections.get(Direction.SOUTH)) {
			
			TileEntity tile = connections.get(Direction.SOUTH);
			connections.remove(Direction.SOUTH, tile);
			//System.out.println("Connection SOUTH removed from list");
		}
		
		if(connections.containsKey(Direction.EAST) && tileEast != connections.get(Direction.EAST)) {
			
			TileEntity tile = connections.get(Direction.EAST);
			connections.remove(Direction.EAST, tile);
			//System.out.println("Connection EAST removed from list");
		}
		
		if(connections.containsKey(Direction.WEST) && tileWest != connections.get(Direction.WEST)) {
			
			TileEntity tile = connections.get(Direction.WEST);
			connections.remove(Direction.WEST, tile);
			//System.out.println("Connection WEST removed from list");
		}
    }
    
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        assert level != null;
        return new ContainerT1EnergyExtractor(this, fields, id, playerInventory);
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
    	
    	if(!this.remove) {
    	
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

	@Override
	public ITextComponent getDisplayName() {
		
		return new TranslationTextComponent("container.mechanicraft.t1_energy_extractor");
		
	}
}