package com.github.will11690.mechanicraft.network.packet.extractor.server;

import java.util.UUID;
import java.util.function.Supplier;

import com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor.TileEntityT1EnergyExtractor;
import com.github.will11690.mechanicraft.network.PacketHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ServerboundExtractorRedstoneConfigurePacket {

    private static boolean validMessage;

    static int redstoneMode;
    static BlockPos blockPos;
	static UUID playerUUID;

    public ServerboundExtractorRedstoneConfigurePacket(int redstoneMode,  BlockPos blockPos, UUID playerUUID) {
    	
    	validMessage = true;
    	ServerboundExtractorRedstoneConfigurePacket.redstoneMode = redstoneMode;
        ServerboundExtractorRedstoneConfigurePacket.blockPos = blockPos;
        ServerboundExtractorRedstoneConfigurePacket.playerUUID = playerUUID;
    }
    
    public static ServerboundExtractorRedstoneConfigurePacket decode(PacketBuffer buffer) {
    	
        return new ServerboundExtractorRedstoneConfigurePacket(buffer.readInt(), buffer.readBlockPos(), buffer.readUUID());
    }

    public boolean isMessageValid() {
    	
        return validMessage;
    }

    public void encode(PacketBuffer buffer) {
    	
        if (!validMessage)
            return;
        
        buffer.writeInt(redstoneMode);
        buffer.writeBlockPos(blockPos);
        buffer.writeUUID(playerUUID);
    }

    public boolean isValid() {
    	
        return validMessage;
    }
    
    public static void onMessageReceived(final ServerboundExtractorRedstoneConfigurePacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().setPacketHandled(true);
    	
    	if (!message.isValid()) {
    		
    		return;
    	}

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.SERVER) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
    }

    private static void processMessage(ServerboundExtractorRedstoneConfigurePacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
            
    		PlayerEntity player = ctxSupplier.get().getSender();
    		UUID playerID = player.getUUID();
    		
    		World world = player.getCommandSenderWorld();
    		TileEntity tile = world.getBlockEntity(blockPos);
    		
    		if(tile instanceof TileEntityT1EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT1EnergyExtractor tileT1Extractor = (TileEntityT1EnergyExtractor)tile;
    			
    			tileT1Extractor.setRedstoneMode(redstoneMode);
    		}
    		
    		/*if(tile instanceof TileEntityT2EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT2EnergyExtractor tileT2Extractor = (TileEntityT2EnergyExtractor)tile;
    			
    			tileT2Extractor.setRedstoneMode(redstoneMode);
    		}
    		
    		if(tile instanceof TileEntityT3EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT3EnergyExtractor tileT3Extractor = (TileEntityT3EnergyExtractor)tile;
    			
    			tileT3Extractor.setRedstoneMode(redstoneMode);
    		}
    		
    		if(tile instanceof TileEntityT4EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT4EnergyExtractor tileT4Extractor = (TileEntityT4EnergyExtractor)tile;
    			
    			tileT4Extractor.setRedstoneMode(redstoneMode);
    		}
    		
    		if(tile instanceof TileEntityT5EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT5EnergyExtractor tileT5Extractor = (TileEntityT5EnergyExtractor)tile;
    			
    			tileT5Extractor.setRedstoneMode(redstoneMode);
    		}
    		
    		if(tile instanceof TileEntityT6EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT6EnergyExtractor tileT6Extractor = (TileEntityT6EnergyExtractor)tile;
    			
    			tileT6Extractor.setRedstoneMode(redstoneMode);
    		}*/
    	});
    	ctxSupplier.get().setPacketHandled(true);
    }

    public static boolean isProtocolAccepted(String protocolVersion) {
    	
    	return PacketHandler.PROTOCOL_VERSION.equals(protocolVersion);
    }
}