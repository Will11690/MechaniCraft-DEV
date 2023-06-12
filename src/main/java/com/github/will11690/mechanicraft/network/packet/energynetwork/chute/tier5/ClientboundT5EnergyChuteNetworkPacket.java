package com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier5;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier5.CapabilityT5EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier5.T5EnergyChuteNetworkList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundT5EnergyChuteNetworkPacket {

	private CompoundNBT syncTag;

	public ClientboundT5EnergyChuteNetworkPacket() {

	}

	public ClientboundT5EnergyChuteNetworkPacket(final INBT syncTag) {
		final CompoundNBT compound = new CompoundNBT();
		compound.put("syncTag", syncTag);
		this.syncTag = compound;
	}
	
	public static ClientboundT5EnergyChuteNetworkPacket decode(PacketBuffer buffer) {
        return new ClientboundT5EnergyChuteNetworkPacket(buffer.readAnySizeNbt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(syncTag);
    }
    
    private static void processMessage(final ClientboundT5EnergyChuteNetworkPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
			if((message.syncTag != null) && message.syncTag.contains("syncTag")) {
				
				Minecraft.getInstance().submitAsync(() -> {
					
					if (Minecraft.getInstance() == null) {
						
						return;
					}
					
					if (Minecraft.getInstance().level == null) {
						
						return;
					}

					final T5EnergyChuteNetworkList list = Minecraft.getInstance().level.getCapability(CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST).orElse(null);
					
					if(list == null) {

						return;
					}
					
				CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST.getStorage().readNBT(CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST, list, null, message.syncTag.get("syncTag"));
				});
			}
		});
    	ctxSupplier.get().setPacketHandled(true);
    }

	public static void onMessageReceived(ClientboundT5EnergyChuteNetworkPacket message, final Supplier<NetworkEvent.Context> ctxSupplier) {
		
		ctxSupplier.get().setPacketHandled(true);

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
	}
}