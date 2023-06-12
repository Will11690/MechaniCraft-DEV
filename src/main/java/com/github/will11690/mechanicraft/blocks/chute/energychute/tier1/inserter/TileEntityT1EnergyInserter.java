package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.inserter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor.TileEntityT1EnergyExtractor;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityT1EnergyInserter extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
    private MechaniCraftEnergyStorage energyStorage = createEnergy();
    
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    
    private Map<Direction, TileEntity> connections = new HashMap<>();
    
	//TODO SET THESE TO CONCRETE VALUES UNTIL CONFIGS ARE ADDED
    private static int capacity = ModConfigs.t1EnergyCubeCapacityInt;
    private static int extract = ModConfigs.t1EnergyCubeTransferInt;
    
    private MechaniCraftEnergyStorage createEnergy() {
    	
        return new MechaniCraftEnergyStorage(capacity, 0, extract) {
        	
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
    
    public TileEntityT1EnergyInserter() {
    	
        super(TileEntityHandler.TILE_ENTITY_T1_ENERGY_INSERTER.get());
    }
    
	@Override
    public void tick() {
    	
        if (this.level == null || this.level.isClientSide) {
        	
            return;
        }
        
        if(energyStorage.getEnergyStored() == 0 /*|| get network that neigbor is connected to and check if energy is empty*/) {
        	
        	return;
        }
    	
        this.updateEnergyStorage();
        this.configureList();
        //TODO get configurations from gui to determine where to send power to
        
        if(connections.isEmpty()) {
        	
        	return;
        }
        
        if(connections.containsKey(Direction.UP)) {
        	
        	TileEntity tile = connections.get(Direction.UP);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN);
        	
        	if(tileCap.isPresent()) {
        		
        		IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
        	
        		if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
        		
        			int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
        			tileEnergyStorage.receiveEnergy(energyToExtract, false);
        			energyStorage.extractEnergy(energyToExtract, false);
        		}
        	}
        }
        	
        if(connections.containsKey(Direction.DOWN)) {
        	
        	TileEntity tile = connections.get(Direction.DOWN);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.UP);
        	
        	if(tileCap.isPresent()) {
        		
            	IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
            	
            	if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
            		
            		int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
            		tileEnergyStorage.receiveEnergy(energyToExtract, false);
            		energyStorage.extractEnergy(energyToExtract, false);
            	}
            }
        }
        	
        if(connections.containsKey(Direction.NORTH)) {
        	
        	TileEntity tile = connections.get(Direction.NORTH);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH);
        	
        	if(tileCap.isPresent()) {
        		
            	IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
            	
            	if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
            		
            		int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
            		tileEnergyStorage.receiveEnergy(energyToExtract, false);
            		energyStorage.extractEnergy(energyToExtract, false);
            	}
            }
        }
        	
        if(connections.containsKey(Direction.SOUTH)) {
        	
        	TileEntity tile = connections.get(Direction.SOUTH);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH);
        	
        	if(tileCap.isPresent()) {
        		
            	IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
            	
            	if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
            		
            		int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
            		tileEnergyStorage.receiveEnergy(energyToExtract, false);
            		energyStorage.extractEnergy(energyToExtract, false);
            	}
            }
        }
        	
        if(connections.containsKey(Direction.EAST)) {
        	
        	TileEntity tile = connections.get(Direction.EAST);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.WEST);
        	
        	if(tileCap.isPresent()) {
        		
            	IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
            	
            	if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
            		
            		int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
            		tileEnergyStorage.receiveEnergy(energyToExtract, false);
            		energyStorage.extractEnergy(energyToExtract, false);
            	}
            }
        }
        	
        if(connections.containsKey(Direction.WEST)) {
        	
        	TileEntity tile = connections.get(Direction.WEST);
        	LazyOptional<IEnergyStorage> tileCap = tile.getCapability(CapabilityEnergy.ENERGY, Direction.EAST);
        	
        	if(tileCap.isPresent()) {
        		
            	IEnergyStorage tileEnergyStorage = tileCap.orElseThrow(IllegalStateException::new);
            	
            	if(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored() > 0 && energyStorage.getEnergyStored() > 0) {
            		
            		int energyToExtract = tileEnergyStorage.receiveEnergy(Math.min(tileEnergyStorage.getMaxEnergyStored() - tileEnergyStorage.getEnergyStored(), (int)(energyStorage.getMaxExtract() / connections.size())), true);
            		tileEnergyStorage.receiveEnergy(energyToExtract, false);
            		energyStorage.extractEnergy(energyToExtract, false);
            	}
            }
        }
    }
	
	private void configureList() {
		
		TileEntity tileUp = level.getBlockEntity(worldPosition.relative(Direction.UP));
		TileEntity tileDown = level.getBlockEntity(worldPosition.relative(Direction.DOWN));
		TileEntity tileNorth = level.getBlockEntity(worldPosition.relative(Direction.NORTH));
		TileEntity tileSouth = level.getBlockEntity(worldPosition.relative(Direction.SOUTH));
		TileEntity tileEast = level.getBlockEntity(worldPosition.relative(Direction.EAST));
		TileEntity tileWest = level.getBlockEntity(worldPosition.relative(Direction.WEST));
                
		if(tileUp != null && tileUp.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).isPresent()) {
			
			if(!(tileUp instanceof TileEntityT1EnergyInserter)) {
			
				connections.put(Direction.UP, tileUp);
			}
		}
        
		if(tileDown != null && tileDown.getCapability(CapabilityEnergy.ENERGY, Direction.UP).isPresent()) {
	
			if(!(tileDown instanceof TileEntityT1EnergyInserter)) {
	
				connections.put(Direction.DOWN, tileDown);
			}
		}
        
		if(tileNorth != null && tileNorth.getCapability(CapabilityEnergy.ENERGY, Direction.SOUTH).isPresent()) {
	
			if(!(tileNorth instanceof TileEntityT1EnergyInserter)) {
	
				connections.put(Direction.NORTH, tileNorth);
			}
		}
        
		if(tileSouth != null && tileSouth.getCapability(CapabilityEnergy.ENERGY, Direction.NORTH).isPresent()) {
	
			if(!(tileSouth instanceof TileEntityT1EnergyInserter)) {
	
				connections.put(Direction.SOUTH, tileSouth);
			}
		}
        
		if(tileEast != null && tileEast.getCapability(CapabilityEnergy.ENERGY, Direction.WEST).isPresent()) {
	
			if(!(tileEast instanceof TileEntityT1EnergyInserter)) {
	
				connections.put(Direction.EAST, tileEast);
			}
		}
        
		if(tileWest != null && tileWest.getCapability(CapabilityEnergy.ENERGY, Direction.EAST).isPresent()) {
	
			if(!(tileWest instanceof TileEntityT1EnergyInserter)) {
	
				connections.put(Direction.WEST, tileWest);
			}
		}
		
		if(connections.containsKey(Direction.UP) && tileUp != connections.get(Direction.UP)) {
			
			TileEntity tile = connections.get(Direction.UP);
			connections.remove(Direction.UP, tile);
		}
		
		if(connections.containsKey(Direction.DOWN) && tileUp != connections.get(Direction.DOWN)) {
			
			TileEntity tile = connections.get(Direction.DOWN);
			connections.remove(Direction.DOWN, tile);
		}
		
		if(connections.containsKey(Direction.NORTH) && tileUp != connections.get(Direction.NORTH)) {
			
			TileEntity tile = connections.get(Direction.NORTH);
			connections.remove(Direction.NORTH, tile);
		}
		
		if(connections.containsKey(Direction.SOUTH) && tileUp != connections.get(Direction.SOUTH)) {
			
			TileEntity tile = connections.get(Direction.SOUTH);
			connections.remove(Direction.SOUTH, tile);
		}
		
		if(connections.containsKey(Direction.EAST) && tileUp != connections.get(Direction.EAST)) {
			
			TileEntity tile = connections.get(Direction.EAST);
			connections.remove(Direction.EAST, tile);
		}
		
		if(connections.containsKey(Direction.WEST) && tileUp != connections.get(Direction.WEST)) {
			
			TileEntity tile = connections.get(Direction.WEST);
			connections.remove(Direction.WEST, tile);
		}
    }
	
	//@Override
	//public void onLoad() {
		
		//LazyOptional<T1EnergyCubeNetworkList> t1CubeNetworkCap = level.getCapability(CapabilityT1EnergyCubeNetwork.T1_NETWORK_LIST);
		
		//if(t1CubeNetworkCap.isPresent()) {
			//T1EnergyCubeNetworkList t1CubeNetwork = t1CubeNetworkCap.orElseThrow(IllegalStateException::new);
			//t1CubeNetwork.addConnection(this.worldPosition);
		//}
		//super.onLoad();
	//}

	private void updateEnergyStorage() {
		
		if(energy.isPresent()) {
				
			energyStorage.updateEnergyStorageNoUpgrades(capacity, 0, extract);
		}
	}
    
	private void sendPower() {
		
        AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        
        if(energy.get() > 0) {
        	
            for(Direction direction : Direction.values()) {
            	
                TileEntity te = level.getBlockEntity(worldPosition.relative(direction));
                
                if(te != null && !(te instanceof TileEntityT1EnergyInserter)) {
                	
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                    	
                        if(handler.canReceive()) {
                                	
                            int received = handler.receiveEnergy(Math.min(energy.get(), energyStorage.getMaxExtract()), false);
                            energy.addAndGet(-received);
                            energyStorage.extractEnergy(received, false);
                            setChanged();
                                    
                            return energy.get() > 0;
                                    
                            } else {
                                	
                                return true;
                                
                            }
                                
                        }).orElse(true);
                    
                    if(!doContinue) {
                    	
                        return;
                    }
                }
            }
        }
    }
    
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        assert level != null;
        return new ContainerT1EnergyInserter(this, id, playerInventory);
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
		
		return new TranslationTextComponent("container.mechanicraft.t1_energy_inserter");
		
	}
}