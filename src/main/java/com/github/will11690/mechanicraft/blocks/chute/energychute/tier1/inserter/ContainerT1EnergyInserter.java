package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.inserter;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.blocks.machines.common.slots.SlotEnergyItem;
import com.github.will11690.mechanicraft.blocks.machines.common.slots.SlotStorageUpgrade;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1energycube.TileEntityT1EnergyCube;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.network.PacketHandler;
import com.github.will11690.mechanicraft.network.packet.energy.client.ClientboundEnergyPacket;
import com.github.will11690.mechanicraft.util.handlers.ContainerTypeHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerT1EnergyInserter extends Container {
	
	private final IItemHandler playerInventory;
	private PlayerEntity player;
    private TileEntityT1EnergyInserter tile;
    private int energyStored;
    private int energyCapacity;

    public ContainerT1EnergyInserter(int id, PlayerInventory playerInventory, PacketBuffer exData) {
    	
        this((TileEntityT1EnergyInserter) playerInventory.player.level.getBlockEntity(exData.readBlockPos()), id, playerInventory);
    }

    public ContainerT1EnergyInserter(@Nullable TileEntityT1EnergyInserter tile, int id, PlayerInventory playerInventory) {
        super(ContainerTypeHandler.CONTAINER_T1_ENERGY_INSERTER.get(), id);

        this.tile = tile;
        this.player = playerInventory.player;
        this.playerInventory = new InvWrapper(playerInventory);
        
        this.energyStored = 0;
        this.energyCapacity = 0;
        
        layoutPlayerInventorySlots(8, 86);
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

	@Override
	public boolean stillValid(PlayerEntity player) {
		
		BlockPos pos = this.tile.getBlockPos();
        return this.tile != null && !this.tile.isRemoved() && player.distanceToSqr(new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5D, 0.5D, 0.5D)) <= 64D;
	}
}