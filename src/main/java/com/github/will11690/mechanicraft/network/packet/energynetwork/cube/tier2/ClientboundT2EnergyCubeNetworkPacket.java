package com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier2;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier2.CapabilityT2EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier2.T2EnergyCubeNetworkList;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ClientboundT2EnergyCubeNetworkPacket {

	private CompoundNBT syncTag;

	public ClientboundT2EnergyCubeNetworkPacket() {

	}

	public ClientboundT2EnergyCubeNetworkPacket(final INBT syncTag) {
		final CompoundNBT compound = new CompoundNBT();
		compound.put("syncTag", syncTag);
		this.syncTag = compound;
	}
	
	public static ClientboundT2EnergyCubeNetworkPacket decode(PacketBuffer buffer) {
        return new ClientboundT2EnergyCubeNetworkPacket(buffer.readAnySizeNbt());
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeNbt(syncTag);
    }
    
    private static void processMessage(final ClientboundT2EnergyCubeNetworkPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {

    	ctxSupplier.get().enqueueWork(() -> {
    		
			if((message.syncTag != null) && message.syncTag.contains("syncTag")) {
				
				Minecraft.getInstance().submitAsync(() -> {
					
					if (Minecraft.getInstance() == null) {
						
						return;
					}
					
					if (Minecraft.getInstance().level == null) {
						
						return;
					}

					final T2EnergyCubeNetworkList list = Minecraft.getInstance().level.getCapability(CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST).orElse(null);
					
					if(list == null) {

						return;
					}
					
				CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST.getStorage().readNBT(CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST, list, null, message.syncTag.get("syncTag"));
				});
			}
		});
    	ctxSupplier.get().setPacketHandled(true);
    }

	public static void onMessageReceived(ClientboundT2EnergyCubeNetworkPacket message, final Supplier<NetworkEvent.Context> ctxSupplier) {
		
		ctxSupplier.get().setPacketHandled(true);

    	if (ctxSupplier.get().getDirection().getReceptionSide() != LogicalSide.CLIENT) {
    		
    		return;
    	}
    	
    	ctxSupplier.get().enqueueWork(() -> processMessage(message, ctxSupplier));
	}
}