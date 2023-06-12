package com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier4;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier4.CapabilityT4EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier4.T4EnergyCubeNetworkList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundT4EnergyCubeNetworkPacket {

	private CompoundNBT syncTag;

	public ClientboundT4EnergyCubeNetworkPacket() {

	}

	public ClientboundT4EnergyCubeNetworkPacket(final INBT syncTag) {
		final CompoundNBT compound = new CompoundNBT();
		compound.put("syncTag", syncTag);
		this.syncTag = compound;
	}
	
	public static ClientboundT4EnergyCubeNetworkPacket decode(PacketBuffer buffer) {
        return new ClientboundT4EnergyCubeNetworkPacket(buffer.readAnySizeNbt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(syncTag);
    }
    
    private static void processMessage(final ClientboundT4EnergyCubeNetworkPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
			if((message.syncTag != null) && message.syncTag.contains("syncTag")) {
				
				Minecraft.getInstance().submitAsync(() -> {
					
					if (Minecraft.getInstance() == null) {
						
						return;
					}
					
					if (Minecraft.getInstance().level == null) {
						
						return;
					}

					final T4EnergyCubeNetworkList list = Minecraft.getInstance().level.getCapability(CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST).orElse(null);
					
					if(list == null) {

						return;
					}
					
				CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST.getStorage().readNBT(CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST, list, null, message.syncTag.get("syncTag"));
				});
			}
		});
    	ctxSupplier.get().setPacketHandled(true);
    }

	public static void onMessageReceived(ClientboundT4EnergyCubeNetworkPacket message, final Supplier<NetworkEvent.Context> ctxSupplier) {
		
		ctxSupplier.get().setPacketHandled(true);

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
	}
}