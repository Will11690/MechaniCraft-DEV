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

public class ServerboundExtractorTransferPacket {

	//TODO Split commented out parts into seperate packets
    private static boolean validMessage;

    static int sideSelectID;
    static int setTransfer;
    static BlockPos blockPos;
	static UUID playerUUID;

    public ServerboundExtractorTransferPacket(int sideSelectID, int setTransfer,  BlockPos blockPos, UUID playerUUID) {
    	
    	validMessage = true;
        ServerboundExtractorTransferPacket.sideSelectID = sideSelectID;
        ServerboundExtractorTransferPacket.setTransfer = setTransfer;
        ServerboundExtractorTransferPacket.blockPos = blockPos;
        ServerboundExtractorTransferPacket.playerUUID = playerUUID;
    }
    
    public static ServerboundExtractorTransferPacket decode(PacketBuffer buffer) {
    	
        return new ServerboundExtractorTransferPacket(buffer.readInt(), buffer.readInt(), buffer.readBlockPos(), buffer.readUUID());
    }

    public boolean isMessageValid() {
    	
        return validMessage;
    }

    public void encode(PacketBuffer buffer) {
    	
        if (!validMessage)
            return;
        
        buffer.writeInt(sideSelectID);
        buffer.writeInt(setTransfer);
        buffer.writeBlockPos(blockPos);
        buffer.writeUUID(playerUUID);
    }

    public boolean isValid() {
    	
        return validMessage;
    }
    
    public static void onMessageReceived(final ServerboundExtractorTransferPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().setPacketHandled(true);
    	
    	if (!message.isValid()) {
    		
    		return;
    	}

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.SERVER) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
    }

    private static void processMessage(ServerboundExtractorTransferPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
            
    		PlayerEntity player = ctxSupplier.get().getSender();
    		UUID playerID = player.getUUID();
    		
    		World world = player.getCommandSenderWorld();
    		TileEntity tile = world.getBlockEntity(blockPos);
    		
    		if(tile instanceof TileEntityT1EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT1EnergyExtractor tileT1Extractor = (TileEntityT1EnergyExtractor)tile;
    			
    			tileT1Extractor.setSideTransfer(sideSelectID, setTransfer);
    		}
    		
    		/*if(tile instanceof TileEntityT2EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT2EnergyExtractor tileT2Extractor = (TileEntityT2EnergyExtractor)tile;
    			
    			tileT2Extractor.setSideEnabled(sideSelectID, extractorToggle);
    			tileT2Extractor.setSideTransfer(sideSelectID, setTransfer);
    			tileT2Extractor.setSidePriority(sideSelectID, setPriority);
    			tileT2Extractor.setRedstoneMode(redstoneMode);
    			tileT2Extractor.setComparatorMode(comparatorMode);
    		}
    		
    		if(tile instanceof TileEntityT3EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT3EnergyExtractor tileT3Extractor = (TileEntityT3EnergyExtractor)tile;
    			
    			tileT3Extractor.setSideEnabled(sideSelectID, extractorToggle);
    			tileT3Extractor.setSideTransfer(sideSelectID, setTransfer);
    			tileT3Extractor.setSidePriority(sideSelectID, setPriority);
    			tileT3Extractor.setRedstoneMode(redstoneMode);
    			tileT3Extractor.setComparatorMode(comparatorMode);
    		}
    		
    		if(tile instanceof TileEntityT4EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT4EnergyExtractor tileT4Extractor = (TileEntityT4EnergyExtractor)tile;
    			
    			tileT4Extractor.setSideEnabled(sideSelectID, extractorToggle);
    			tileT4Extractor.setSideTransfer(sideSelectID, setTransfer);
    			tileT4Extractor.setSidePriority(sideSelectID, setPriority);
    			tileT4Extractor.setRedstoneMode(redstoneMode);
    			tileT4Extractor.setComparatorMode(comparatorMode);
    		}
    		
    		if(tile instanceof TileEntityT5EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT5EnergyExtractor tileT5Extractor = (TileEntityT5EnergyExtractor)tile;
    			
    			tileT5Extractor.setSideEnabled(sideSelectID, extractorToggle);
    			tileT5Extractor.setSideTransfer(sideSelectID, setTransfer);
    			tileT5Extractor.setSidePriority(sideSelectID, setPriority);
    			tileT5Extractor.setRedstoneMode(redstoneMode);
    			tileT5Extractor.setComparatorMode(comparatorMode);
    		}
    		
    		if(tile instanceof TileEntityT6EnergyExtractor && playerUUID == playerID) {
    			
    			TileEntityT6EnergyExtractor tileT6Extractor = (TileEntityT6EnergyExtractor)tile;
    			
    			tileT6Extractor.setSideEnabled(sideSelectID, extractorToggle);
    			tileT6Extractor.setSideTransfer(sideSelectID, setTransfer);
    			tileT6Extractor.setSidePriority(sideSelectID, setPriority);
    			tileT6Extractor.setRedstoneMode(redstoneMode);
    			tileT6Extractor.setComparatorMode(comparatorMode);
    		}*/
    	});
    	ctxSupplier.get().setPacketHandled(true);
    }

    public static boolean isProtocolAccepted(String protocolVersion) {
    	
    	return PacketHandler.PROTOCOL_VERSION.equals(protocolVersion);
    }
}