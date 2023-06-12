package com.github.will11690.mechanicraft.blocks.machines.tier6.t6energycube;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;
import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier6.CapabilityT6EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier6.T6EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.handlers.NonExtractableStackHandler;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class TileEntityT6EnergyCube extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	ItemStackHandler upgradeSlotHandler = createUpgrade();
    private ItemStackHandler upgradeSlotHandlerWrapper = createUpgradeWrapper(upgradeSlotHandler);
    private ItemStackHandler chargeSlotsHandler = createCharge();
    private MechaniCraftEnergyStorage energyStorage = createEnergy();
    
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    private final LazyOptional<IItemHandler> chargeSlots  = LazyOptional.of(() -> chargeSlotsHandler);
    private final LazyOptional<IItemHandler> upgradeSlotWrapper  = LazyOptional.of(() -> upgradeSlotHandlerWrapper);
    
    private final LazyOptional<IItemHandler> allSlots  = LazyOptional.of(() -> new CombinedInvWrapper(chargeSlotsHandler, upgradeSlotHandlerWrapper));
	
	private final LazyOptional<IItemHandler> dropSlots  = LazyOptional.of(() -> new CombinedInvWrapper(chargeSlotsHandler));
	boolean breakBlock = false;
    
    private static int capacity = ModConfigs.t6EnergyCubeCapacityInt;
    private static int transfer = ModConfigs.t6EnergyCubeTransferInt;
    
    private MechaniCraftEnergyStorage createEnergy() {
    	
        return new MechaniCraftEnergyStorage(capacity, transfer) {
        	
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
    
    private ItemStackHandler createCharge() {
    	
    	return new ItemStackHandler(12) {
    		
    		@Override
            protected void onContentsChanged(int slot) {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
            }
    		
    		@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

				if(stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {

					return true;

				}

				return false;
				
			}
		};
    }

	private ItemStackHandler createUpgrade() {

    	return new ItemStackHandler(3) {
    		
    		@Override
            protected void onContentsChanged(int slot) {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
            }
    		
    		@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

    			Item item = stack.getItem();
				
				if(item.equals(ModItems.CAPACITY_UPGRADE.get()) || item.equals(ModItems.TRANSFER_UPGRADE.get())) {
					
					return true;
					
				}

				return false;
				
			}
    		
    		@Override
    		@Nonnull
    		public ItemStack extractItem(int slot, int amount, boolean simulate) {
    			
    			if(stacks.get(slot).getItem().equals(ModItems.CAPACITY_UPGRADE.get())) {
    				
    				if(energyStorage.canExtractFromSlot(energyStorage.getEnergyStored()) != true) {
    				
    					return ItemStack.EMPTY;
    				}
    			}
    			 	
    			return super.extractItem(slot, amount, simulate);
    		 }
		};
		
    }
	
	private ItemStackHandler createUpgradeWrapper(ItemStackHandler upgradeSlotHandler) {

    	return new NonExtractableStackHandler(this.upgradeSlotHandler) {
    		
    		@Override
            protected void onContentsChanged(int slot) {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
            }
    		
    		@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
    			
    			Item item = stack.getItem();
				
				if(item.equals(ModItems.CAPACITY_UPGRADE.get()) || item.equals(ModItems.TRANSFER_UPGRADE.get())) {

					return true;
					
				}

				return false;
				
			}
    		
    		@Override
    		@Nonnull
    		public ItemStack extractItem(int slot, int amount, boolean simulate) {
    			
    			if(stacks.get(slot).getItem().equals(ModItems.CAPACITY_UPGRADE.get())) {
    				
    				if(energyStorage.canExtractFromSlot(energyStorage.getEnergyStored()) != true) {
    				
    					return ItemStack.EMPTY;
    				}
    			}
    			 	
    			return super.extractItem(slot, amount, simulate);
    		 }
		};
    }

    public TileEntityT6EnergyCube() {
    	
        super(TileEntityHandler.TILE_ENTITY_T6_ENERGY_CUBE.get());
        
    }

	@Override
    public void tick() {
    	
        if (this.level == null || this.level.isClientSide) {
        	
            return;
            
        }
        
        this.setUpgradeModifiers();
        	
        ItemStack chargeStack1 = chargeSlotsHandler.getStackInSlot(0);
        ItemStack chargeStack2 = chargeSlotsHandler.getStackInSlot(1);
        ItemStack chargeStack3 = chargeSlotsHandler.getStackInSlot(2);
        ItemStack chargeStack4 = chargeSlotsHandler.getStackInSlot(3);
        ItemStack chargeStack5 = chargeSlotsHandler.getStackInSlot(4);
        ItemStack chargeStack6 = chargeSlotsHandler.getStackInSlot(5);
        ItemStack chargeStack7 = chargeSlotsHandler.getStackInSlot(6);
        ItemStack chargeStack8 = chargeSlotsHandler.getStackInSlot(7);
        ItemStack chargeStack9 = chargeSlotsHandler.getStackInSlot(8);
        ItemStack chargeStack10 = chargeSlotsHandler.getStackInSlot(9);
        ItemStack chargeStack11 = chargeSlotsHandler.getStackInSlot(10);
        ItemStack chargeStack12 = chargeSlotsHandler.getStackInSlot(11);
        
        if(energyStorage.getEnergyStored() < energyStorage.getCapacity()) {
        		
        	if(!chargeStack7.isEmpty()) {
        			
        		receivePowerItem(chargeStack7);
        			
        	}
        	
        	if(!chargeStack8.isEmpty()) {
    			
        		receivePowerItem(chargeStack8);
    			
        	}
    		
        	if(!chargeStack9.isEmpty()) {
    			
        		receivePowerItem(chargeStack9);
    			
        	}
    	
        	if(!chargeStack10.isEmpty()) {
			
        		receivePowerItem(chargeStack10);
			
        	}
		
        	if(!chargeStack11.isEmpty()) {
			
        		receivePowerItem(chargeStack11);
			
        	}
	
        	if(!chargeStack12.isEmpty()) {
		
        		receivePowerItem(chargeStack12);
		
        	}
        }
        	
        if(hasEnergy()) {
        		
        	if(!chargeStack1.isEmpty()) {
        			
        		sendPowerItem(chargeStack1);
        			
        	}
    		
        	if(!chargeStack2.isEmpty()) {
    			
        		sendPowerItem(chargeStack2);
    			
        	}
		
        	if(!chargeStack3.isEmpty()) {
			
        		sendPowerItem(chargeStack3);
			
        	}
	
        	if(!chargeStack4.isEmpty()) {
		
        		sendPowerItem(chargeStack4);
		
        	}

        	if(!chargeStack5.isEmpty()) {
	
        		sendPowerItem(chargeStack5);
	
        	}

        	if(!chargeStack6.isEmpty()) {
	
        		sendPowerItem(chargeStack6);
	
        	}
        		
        	sendPower();
        		
        }
        
    }
	
	@Override
	public void onLoad() {
		
		LazyOptional<T6EnergyCubeNetworkList> t6CubeNetworkCap = level.getCapability(CapabilityT6EnergyCubeNetwork.T6_NETWORK_LIST);
		
		if(t6CubeNetworkCap.isPresent()) {
			T6EnergyCubeNetworkList t6CubeNetwork = t6CubeNetworkCap.orElseThrow(IllegalStateException::new);
			t6CubeNetwork.addConnection(this.worldPosition);
		}
		super.onLoad();
	}
    
    private void setUpgradeModifiers() {
    	
    	if(energy.isPresent()) {
    		
    		energyStorage.setUpgrade1Stack(upgradeSlotHandler.getStackInSlot(0));
    		energyStorage.setUpgrade2Stack(upgradeSlotHandler.getStackInSlot(1));
    		energyStorage.setUpgrade3Stack(upgradeSlotHandler.getStackInSlot(2));
    		energyStorage.threeUpgradeModifier(capacity, transfer, transfer, upgradeSlotHandler.getStackInSlot(0), upgradeSlotHandler.getStackInSlot(1), upgradeSlotHandler.getStackInSlot(2));
    		
    	}
    }

	public boolean canExtractCapacity() {
		
		if(energy.isPresent()) {
			
			return energyStorage.canExtractFromSlot(energyStorage.getEnergyStored());
			
		} else 
			return false;
	}
	
	private void receivePowerItem(ItemStack stack) {
		
		AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        
		if(energy.get() < energyStorage.getCapacity()) {
                
			if(stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
                	
				boolean doContinue = stack.getCapability(CapabilityEnergy.ENERGY).map(handler -> {
                    	
					if(handler.canReceive()) {
                                	
						int extracted = handler.extractEnergy(handler.getEnergyStored(), false);
                        energy.addAndGet(extracted);
                        energyStorage.addEnergy(extracted);
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
    
    private boolean hasEnergy() {
    	
    	if(energyStorage.getEnergyStored() > 0) {
    		
    		return true;
    		
    	} else {
    		
    		return false;
    		
    	}
    	
    }
    
	private void sendPower() {
		
        AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        
        if(energy.get() > 0) {
        	
            for(Direction direction : Direction.values()) {
            	
                TileEntity te = level.getBlockEntity(worldPosition.relative(direction));
                
                if(te != null && !(te instanceof TileEntityT6EnergyCube)) {
                	
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
	
	private void sendPowerItem(ItemStack stack) {
		
		AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        
		if(energy.get() > 0) {
                
			if(stack.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
                	
				boolean doContinue = stack.getCapability(CapabilityEnergy.ENERGY).map(handler -> {
                    	
					if(handler.canReceive()) {
                                	
						int received = handler.receiveEnergy(energy.get(), false);
						energy.addAndGet(-received);
						energyStorage.consumeEnergy(received);
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
    
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        assert level != null;
        return new ContainerT6EnergyCube(this, id, playerInventory, new CombinedInvWrapper(chargeSlotsHandler, upgradeSlotHandler));
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
    	
        super.load(state, tags);
        this.energyStorage.deserializeNBT(tags.getCompound("energy"));
		this.chargeSlotsHandler.deserializeNBT(tags.getCompound("chargeSlots"));
		this.upgradeSlotHandler.deserializeNBT(tags.getCompound("upgradeSlot"));
        
    }

    @Override
    public CompoundNBT save(CompoundNBT tags) {
    	
        super.save(tags);
        tags.put("energy", energyStorage.serializeNBT());
		tags.put("chargeSlots", chargeSlotsHandler.serializeNBT());
		tags.put("upgradeSlot", upgradeSlotHandler.serializeNBT());
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

	boolean blockBeingBroken(boolean onRemoved) {
		
		return breakBlock = onRemoved;
	}

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
    	
    	if(!this.remove && side != null) {
    	
    		if (cap == CapabilityEnergy.ENERGY) {
    		
            	return energy.cast();
            
        	}
    	
    		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
        	
        		return allSlots.cast();
            
        	}
    		
    	} else if(breakBlock == true && side == null) {

 			if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
 				
 				return dropSlots.cast();
 			}
			
		} else if(!this.remove && side == null && cap == CapabilityEnergy.ENERGY) {
			
			return energy.cast();
		}
        
		return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
    	
        energy.invalidate();
		chargeSlots.invalidate();
		upgradeSlotWrapper.invalidate();
		allSlots.invalidate();
		dropSlots.invalidate();
        super.setRemoved();
        
    }

	@Override
	public ITextComponent getDisplayName() {
		
		return new TranslationTextComponent("container.mechanicraft.t6_energy_cube");
		
	}
}