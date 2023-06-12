package com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier3;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier3.CapabilityT3EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier3.T3EnergyCubeNetworkList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundT3EnergyCubeNetworkPacket {

	private CompoundNBT syncTag;

	public ClientboundT3EnergyCubeNetworkPacket() {

	}

	public ClientboundT3EnergyCubeNetworkPacket(final INBT syncTag) {
		final CompoundNBT compound = new CompoundNBT();
		compound.put("syncTag", syncTag);
		this.syncTag = compound;
	}
	
	public static ClientboundT3EnergyCubeNetworkPacket decode(PacketBuffer buffer) {
        return new ClientboundT3EnergyCubeNetworkPacket(buffer.readAnySizeNbt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(syncTag);
    }
    
    private static void processMessage(final ClientboundT3EnergyCubeNetworkPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
			if((message.syncTag != null) && message.syncTag.contains("syncTag")) {
				
				Minecraft.getInstance().submitAsync(() -> {
					
					if (Minecraft.getInstance() == null) {
						
						return;
					}
					
					if (Minecraft.getInstance().level == null) {
						
						return;
					}

					final T3EnergyCubeNetworkList list = Minecraft.getInstance().level.getCapability(CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST).orElse(null);
					
					if(list == null) {

						return;
					}
					
				CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST.getStorage().readNBT(CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST, list, null, message.syncTag.get("syncTag"));
				});
			}
		});
    	ctxSupplier.get().setPacketHandled(true);
    }

	public static void onMessageReceived(ClientboundT3EnergyCubeNetworkPacket message, final Supplier<NetworkEvent.Context> ctxSupplier) {
		
		ctxSupplier.get().setPacketHandled(true);

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
	}
}