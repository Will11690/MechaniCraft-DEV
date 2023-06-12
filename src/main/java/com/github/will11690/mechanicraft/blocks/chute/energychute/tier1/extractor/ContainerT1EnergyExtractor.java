package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.network.PacketHandler;
import com.github.will11690.mechanicraft.network.packet.energy.client.ClientboundEnergyPacket;
import com.github.will11690.mechanicraft.util.handlers.ContainerTypeHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerT1EnergyExtractor extends Container {
	
	private final IItemHandler playerInventory;
	private PlayerEntity player;
    private IIntArray fields;
    private TileEntityT1EnergyExtractor tile;
    
    private BlockPos blockPos;
    
    private int energyStored;
    private int energyCapacity;
    
    private int upTransfer;
    private int downTransfer;
    private int northTransfer;
    private int southTransfer;
    private int eastTransfer;
    private int westTransfer;
    
    private int upPriority;
    private int downPriority;
    private int northPriority;
    private int southPriority;
    private int eastPriority;
    private int westPriority;
    
    //private boolean redstoneHigh;
    //private boolean redstoneLow;
    
    //private boolean comparatorHigh;
    //private boolean comparatorLow;

    public ContainerT1EnergyExtractor(int id, PlayerInventory playerInventory, PacketBuffer exData) {
    	
        this((TileEntityT1EnergyExtractor) playerInventory.player.level.getBlockEntity(exData.readBlockPos()), new IntArray(6), id, playerInventory);
    }

    public ContainerT1EnergyExtractor(@Nullable TileEntityT1EnergyExtractor tile, IIntArray fields, int id, PlayerInventory playerInventory) {
        super(ContainerTypeHandler.CONTAINER_T1_ENERGY_EXTRACTOR.get(), id);

        this.tile = tile;
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.fields = fields;
        
        this.energyStored = 0;
        this.energyCapacity = 0;
        
        this.upTransfer = 0;
        this.downTransfer = 0;
        this.northTransfer = 0;
        this.southTransfer = 0;
        this.eastTransfer = 0;
        this.westTransfer = 0;
        
        this.upPriority = 0;
        this.downPriority = 0;
        this.northPriority = 0;
        this.southPriority = 0;
        this.eastPriority = 0;
        this.westPriority = 0;
        
        layoutPlayerInventorySlots(8, 86);
        
        this.addDataSlots(fields);
    }
    
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        
    	for (int i = 0; i < amount; i++) {
        	
            addSlot(new SlotItemHandler(handler, index, x, y-2));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        
    	for (int j = 0; j < verAmount; j++) {
        	
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
    	
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
    
    private IEnergyStorage getEnergy() {
    	
    	return tile.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    }
    
    @Override
    public void broadcastChanges() {
    	super.broadcastChanges();
    	if((player instanceof ServerPlayerEntity) && !(player instanceof FakePlayer)) {
    		//TODO Handle update packets from gui, from client to server
    		PacketTarget target = PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player);
    		ClientboundEnergyPacket messageEnergy = new ClientboundEnergyPacket(this.energyStored, this.energyCapacity, tile.getBlockPos(), player.getUUID());
			
			int newEnergyStored = getEnergy().getEnergyStored();
			int newEnergyCapacity = getEnergy().getMaxEnergyStored();
			
			if(getEnergy() != null) {
				
				PacketHandler.INSTANCE_ENERGY.send(target, messageEnergy);
				this.energyStored = newEnergyStored;
				this.energyCapacity = newEnergyCapacity;
			}
    	}
    }
	
	public int setEnergyStored(int energyStored) {
		
       return this.energyStored = energyStored;
    }

    public int setEnergyCapacity(int energyCapacity) {
    	
        return this.energyCapacity = energyCapacity;
    }
	
	public int getEnergyStored() {
		
       return this.energyStored;
    }

    public int getEnergyCapacity() {
    	
        return this.energyCapacity;
    }

    public BlockPos getBlockPos() {
    	
    	blockPos = new BlockPos(this.fields.get(0), this.fields.get(1), this.fields.get(2));
    	
    	return blockPos;
    }
    
    public int getPriorityUp() {
    	
    	return upPriority;
    }
    
    public int getPriorityDown() {
    	
    	return downPriority;
    }
    
    public int getPriorityNorth() {
    	
    	return northPriority;
    }
    
    public int getPrioritySouth() {
    	
    	return southPriority;
    }
    
    public int getPriorityEast() {
    	
    	return eastPriority;
    }
    
    public int getPriorityWest() {
    	
    	return westPriority;
    }
    
    public int getTransferUp() {
    	
    	return upTransfer;
    }
    
    public int getTransferDown() {
    	
    	return downTransfer;
    }
    
    public int getTransferNorth() {
    	
    	return northTransfer;
    }
    
    public int getTransferSouth() {
    	
    	return southTransfer;
    }
    
    public int getTransferEast() {
    	
    	return eastTransfer;
    }
    
    public int getTransferWest() {
    	
    	return westTransfer;
    }
    
    public int getCapacity() {
    	//TODO
    	return 0;
    }

	public int getExtractorMode(int sideSelect) {
		
		if(sideSelect == 1) {
			
			if(tile.isUpEnabled() == true) {
				
				return 0;
				
			} else if(tile.isUpEnabled() == false) {
				
				return 1;
			}
		}
			
		if(sideSelect == 2) {
			
			if(tile.isDownEnabled() == true) {
				
				return 0;
				
			} else if(tile.isDownEnabled() == false) {
				
				return 1;
			}
		}
		
		if(sideSelect == 3) {
		
			if(tile.isNorthEnabled() == true) {
			
				return 0;
			
			} else if(tile.isNorthEnabled() == false) {
			
				return 1;
			}
		}
		
		if(sideSelect == 4) {
			
			if(tile.isSouthEnabled() == true) {
				
				return 0;
				
			} else if(tile.isSouthEnabled() == false) {
				
				return 1;
			}
		}
			
		if(sideSelect == 5) {
			
			if(tile.isEastEnabled() == true) {
				
				return 0;
				
			} else if(tile.isEastEnabled() == false) {
				
				return 1;
			}
		}
		
		if(sideSelect == 6) {
		
			if(tile.isWestEnabled() == true) {
			
				return 0;
			
			} else if(tile.isWestEnabled() == false) {
			
				return 1;
			}
		}
		
		return 0;
	}

	public int getTransferLimit() {
		
		return TileEntityT1EnergyExtractor.transfer;
	}
    
	@Override
	public boolean stillValid(PlayerEntity player) {
		
		BlockPos pos = this.tile.getBlockPos();
        return this.tile != null && !this.tile.isRemoved() && player.distanceToSqr(new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5D, 0.5D, 0.5D)) <= 64D;
	}
}