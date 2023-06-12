package com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier6;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier6.CapabilityT6EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier6.T6EnergyChuteNetworkList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundT6EnergyChuteNetworkPacket {

	private CompoundNBT syncTag;

	public ClientboundT6EnergyChuteNetworkPacket() {

	}

	public ClientboundT6EnergyChuteNetworkPacket(final INBT syncTag) {
		final CompoundNBT compound = new CompoundNBT();
		compound.put("syncTag", syncTag);
		this.syncTag = compound;
	}
	
	public static ClientboundT6EnergyChuteNetworkPacket decode(PacketBuffer buffer) {
        return new ClientboundT6EnergyChuteNetworkPacket(buffer.readAnySizeNbt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(syncTag);
    }
    
    private static void processMessage(final ClientboundT6EnergyChuteNetworkPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
			if((message.syncTag != null) && message.syncTag.contains("syncTag")) {
				
				Minecraft.getInstance().submitAsync(() -> {
					
					if (Minecraft.getInstance() == null) {
						
						return;
					}
					
					if (Minecraft.getInstance().level == null) {
						
						return;
					}
					final T6EnergyChuteNetworkList list = Minecraft.getInstance().level.getCapability(CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST).orElse(null);
					
					if(list == null) {

						return;
					}
					
				CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST.getStorage().readNBT(CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST, list, null, message.syncTag.get("syncTag"));
				});
			}
		});
    	ctxSupplier.get().setPacketHandled(true);
    }

	public static void onMessageReceived(ClientboundT6EnergyChuteNetworkPacket message, final Supplier<NetworkEvent.Context> ctxSupplier) {
		
		ctxSupplier.get().setPacketHandled(true);

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
	}
}