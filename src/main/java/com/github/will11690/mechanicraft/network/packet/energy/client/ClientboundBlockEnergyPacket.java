package com.github.will11690.mechanicraft.network.packet.energy.client;

import java.util.UUID;
import java.util.function.Supplier;

import com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor.ContainerT1EnergyExtractor;
import com.github.will11690.mechanicraft.network.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundBlockEnergyPacket {

    private static boolean validMessage;

    int energyStored;
    int energyCapacity;
    static BlockPos blockPos;
	UUID playerUUID;

    public ClientboundBlockEnergyPacket(int energyStored, int energyCapacity,  BlockPos blockPos, UUID playerUUID) {
    	validMessage = true;
        this.energyStored = energyStored;
        this.energyCapacity = energyCapacity;
        ClientboundBlockEnergyPacket.blockPos = blockPos;
        this.playerUUID = playerUUID;
    }
    
    public static ClientboundBlockEnergyPacket decode(PacketBuffer buffer) {
        return new ClientboundBlockEnergyPacket(buffer.readInt(), buffer.readInt(), buffer.readBlockPos(), buffer.readUUID());
    }

    public boolean isMessageValid() {
        return validMessage;
    }

    public void encode(PacketBuffer buffer) {
        if (!validMessage)
            return;
        buffer.writeInt(energyStored);
        buffer.writeInt(energyCapacity);
        buffer.writeBlockPos(blockPos);
        buffer.writeUUID(playerUUID);
    }

    public boolean isValid() {
        return validMessage;
    }
    
    public static void onMessageReceived(final ClientboundBlockEnergyPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().setPacketHandled(true);
    	
    	if (!message.isValid()) {
    		
    		return;
    	}

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
    }

    private static void processMessage(ClientboundBlockEnergyPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
    		ClientPlayerEntity player = Minecraft.getInstance().player;
            Container container = player.containerMenu;
            
            if(container instanceof ContainerT1EnergyExtractor) {
    			
            	ContainerT1EnergyExtractor energyContainer = (ContainerT1EnergyExtractor)container;
    			energyContainer.setEnergyStored(message.energyStored);
    			energyContainer.setEnergyCapacity(message.energyCapacity);
    		}
    	});
    	ctxSupplier.get().setPacketHandled(true);
    }

    public static boolean isProtocolAccepted(String protocolVersion) {
    	return PacketHandler.PROTOCOL_VERSION.equals(protocolVersion);
    }
}