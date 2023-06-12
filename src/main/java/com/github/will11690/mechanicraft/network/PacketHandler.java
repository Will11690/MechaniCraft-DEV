package com.github.will11690.mechanicraft.network;

import com.github.will11690.mechanicraft.network.packet.burntime.client.ClientboundBurnTimePacket;
import com.github.will11690.mechanicraft.network.packet.burntime.server.ServerboundBurnTimePacket;
import com.github.will11690.mechanicraft.network.packet.energy.client.ClientboundEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.energy.server.ServerboundEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier1.ClientboundT1EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier2.ClientboundT2EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier3.ClientboundT3EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier4.ClientboundT4EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier5.ClientboundT5EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.chute.tier6.ClientboundT6EnergyChuteNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier1.ClientboundT1EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier2.ClientboundT2EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier3.ClientboundT3EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier4.ClientboundT4EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier5.ClientboundT5EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.energynetwork.cube.tier6.ClientboundT6EnergyCubeNetworkPacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorComparatorConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorPriorityPacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorRedstoneConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorSideConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorTransferPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.energy.ClientboundWasherEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.energy.ServerboundWasherEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.input.ClientboundWasherInputPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.input.ServerboundWasherInputPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.output.ClientboundWasherOutputPacket;
import com.github.will11690.mechanicraft.network.packet.orewasher.output.ServerboundWasherOutputPacket;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.energy.ClientboundSlurryEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.energy.ServerboundSlurryEnergyPacket;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.input1.ClientboundSlurryInput1Packet;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.input1.ServerboundSlurryInput1Packet;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.input2.ClientboundSlurryInput2Packet;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.input2.ServerboundSlurryInput2Packet;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.output.ClientboundSlurryOutputPacket;
import com.github.will11690.mechanicraft.network.packet.slurryprocesor.output.ServerboundSlurryOutputPacket;
import com.github.will11690.mechanicraft.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketHandler {
	
    public static final String PROTOCOL_VERSION = "1";
    
    //MACHINES
	public static final SimpleChannel INSTANCE_ENERGY = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    
	public static final SimpleChannel INSTANCE_BURN_TIME = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "burn_time"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    
	public static final SimpleChannel INSTANCE_WASHER_ENERGY = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "washer_energy"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel INSTANCE_WASHER_INPUT = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "washer_input"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel INSTANCE_WASHER_OUTPUT = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "washer_output"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    
	public static final SimpleChannel INSTANCE_SLURRY_ENERGY = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "slurry_energy"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel INSTANCE_SLURRY_INPUT1 = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "slurry_input1"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel INSTANCE_SLURRY_INPUT2 = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "slurry_input2"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	public static final SimpleChannel INSTANCE_SLURRY_OUTPUT = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "slurry_output"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	//CHUTES
	public static final SimpleChannel T1_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t1_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T2_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t2_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T3_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t3_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T4_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t4_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T5_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t5_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T6_ENERGY_NETWORK_CHUTE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t6_energy_network_chute"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	//CUBES
	public static final SimpleChannel T1_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t1_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T2_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t2_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T3_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t3_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T4_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t4_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T5_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t5_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel T6_ENERGY_NETWORK_CUBE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "t6_energy_network_cube"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	//EXTRACTORS
	public static final SimpleChannel ENERGY_EXTRACTOR_REDSTONE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_redstone"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel ENERGY_EXTRACTOR_COMPARATOR = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_comparator"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static final SimpleChannel ENERGY_EXTRACTOR_SIDE_TOGGLE = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_side_toggle"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	//TODO
	public static final SimpleChannel ENERGY_EXTRACTOR_CAPACITY = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_capacity"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	//TODO
	public static final SimpleChannel ENERGY_EXTRACTOR_TRANSFER = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_transfer"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	//TODO
	public static final SimpleChannel ENERGY_EXTRACTOR_PRIORITY = NetworkRegistry.newSimpleChannel(
	new ResourceLocation(Reference.MOD_ID, "energy_extractor_priority"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	
	//public static final SimpleChannel T1_ENERGY_EXTRACTOR = NetworkRegistry.newSimpleChannel(
	//new ResourceLocation(Reference.MOD_ID, "t1_energy_extractor"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    private PacketHandler() {}

    public static void init() {
    	
        int index = 0;
        //WASHER PACKETS
        INSTANCE_WASHER_ENERGY.messageBuilder(ServerboundWasherEnergyPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundWasherEnergyPacket::encode).decoder(ServerboundWasherEnergyPacket::decode)
        .consumer(ServerboundWasherEnergyPacket::onMessageReceived).add();
        
        INSTANCE_WASHER_ENERGY.messageBuilder(ClientboundWasherEnergyPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundWasherEnergyPacket::encode).decoder(ClientboundWasherEnergyPacket::decode)
        .consumer(ClientboundWasherEnergyPacket::onMessageReceived).add();
        
        INSTANCE_WASHER_INPUT.messageBuilder(ServerboundWasherInputPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundWasherInputPacket::encode).decoder(ServerboundWasherInputPacket::decode)
        .consumer(ServerboundWasherInputPacket::onMessageReceived).add();

        INSTANCE_WASHER_INPUT.messageBuilder(ClientboundWasherInputPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundWasherInputPacket::encode).decoder(ClientboundWasherInputPacket::decode)
        .consumer(ClientboundWasherInputPacket::onMessageReceived).add();

        INSTANCE_WASHER_OUTPUT.messageBuilder(ServerboundWasherOutputPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundWasherOutputPacket::encode).decoder(ServerboundWasherOutputPacket::decode)
        .consumer(ServerboundWasherOutputPacket::onMessageReceived).add();

        INSTANCE_WASHER_OUTPUT.messageBuilder(ClientboundWasherOutputPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundWasherOutputPacket::encode).decoder(ClientboundWasherOutputPacket::decode)
        .consumer(ClientboundWasherOutputPacket::onMessageReceived).add();
        
        //SLURRY PROCESSOR PACKETS
        INSTANCE_SLURRY_ENERGY.messageBuilder(ServerboundSlurryEnergyPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundSlurryEnergyPacket::encode).decoder(ServerboundSlurryEnergyPacket::decode)
        .consumer(ServerboundSlurryEnergyPacket::onMessageReceived).add();
        
        INSTANCE_SLURRY_ENERGY.messageBuilder(ClientboundSlurryEnergyPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundSlurryEnergyPacket::encode).decoder(ClientboundSlurryEnergyPacket::decode)
        .consumer(ClientboundSlurryEnergyPacket::onMessageReceived).add();
        
        INSTANCE_SLURRY_INPUT1.messageBuilder(ServerboundSlurryInput1Packet.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundSlurryInput1Packet::encode).decoder(ServerboundSlurryInput1Packet::decode)
        .consumer(ServerboundSlurryInput1Packet::onMessageReceived).add();

        INSTANCE_SLURRY_INPUT1.messageBuilder(ClientboundSlurryInput1Packet.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundSlurryInput1Packet::encode).decoder(ClientboundSlurryInput1Packet::decode)
        .consumer(ClientboundSlurryInput1Packet::onMessageReceived).add();
        
        INSTANCE_SLURRY_INPUT2.messageBuilder(ServerboundSlurryInput2Packet.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundSlurryInput2Packet::encode).decoder(ServerboundSlurryInput2Packet::decode)
        .consumer(ServerboundSlurryInput2Packet::onMessageReceived).add();

        INSTANCE_SLURRY_INPUT2.messageBuilder(ClientboundSlurryInput2Packet.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundSlurryInput2Packet::encode).decoder(ClientboundSlurryInput2Packet::decode)
        .consumer(ClientboundSlurryInput2Packet::onMessageReceived).add();

        INSTANCE_SLURRY_OUTPUT.messageBuilder(ServerboundSlurryOutputPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundSlurryOutputPacket::encode).decoder(ServerboundSlurryOutputPacket::decode)
        .consumer(ServerboundSlurryOutputPacket::onMessageReceived).add();

        INSTANCE_SLURRY_OUTPUT.messageBuilder(ClientboundSlurryOutputPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundSlurryOutputPacket::encode).decoder(ClientboundSlurryOutputPacket::decode)
        .consumer(ClientboundSlurryOutputPacket::onMessageReceived).add();
        
        //BURN TIME PACKETS
        INSTANCE_ENERGY.messageBuilder(ServerboundEnergyPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundEnergyPacket::encode).decoder(ServerboundEnergyPacket::decode)
        .consumer(ServerboundEnergyPacket::onMessageReceived).add();
        
        INSTANCE_ENERGY.messageBuilder(ClientboundEnergyPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundEnergyPacket::encode).decoder(ClientboundEnergyPacket::decode)
        .consumer(ClientboundEnergyPacket::onMessageReceived).add();
        
        //ENERGY PACKETS
        INSTANCE_BURN_TIME.messageBuilder(ServerboundBurnTimePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundBurnTimePacket::encode).decoder(ServerboundBurnTimePacket::decode)
        .consumer(ServerboundBurnTimePacket::onMessageReceived).add();
        
        INSTANCE_BURN_TIME.messageBuilder(ClientboundBurnTimePacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundBurnTimePacket::encode).decoder(ClientboundBurnTimePacket::decode)
        .consumer(ClientboundBurnTimePacket::onMessageReceived).add();
        
        //CHUTE ENERGY NETWORK PACKETS
        T1_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT1EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT1EnergyChuteNetworkPacket::encode).decoder(ClientboundT1EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT1EnergyChuteNetworkPacket::onMessageReceived).add();
        
        T2_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT2EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT2EnergyChuteNetworkPacket::encode).decoder(ClientboundT2EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT2EnergyChuteNetworkPacket::onMessageReceived).add();
        
        T3_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT3EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT3EnergyChuteNetworkPacket::encode).decoder(ClientboundT3EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT3EnergyChuteNetworkPacket::onMessageReceived).add();
        
        T4_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT4EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT4EnergyChuteNetworkPacket::encode).decoder(ClientboundT4EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT4EnergyChuteNetworkPacket::onMessageReceived).add();
        
        T5_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT5EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT5EnergyChuteNetworkPacket::encode).decoder(ClientboundT5EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT5EnergyChuteNetworkPacket::onMessageReceived).add();
        
        T6_ENERGY_NETWORK_CHUTE.messageBuilder(ClientboundT6EnergyChuteNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT6EnergyChuteNetworkPacket::encode).decoder(ClientboundT6EnergyChuteNetworkPacket::decode)
        .consumer(ClientboundT6EnergyChuteNetworkPacket::onMessageReceived).add();
        
        //CUBE ENERGY NETWORK PACKETS
        T1_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT1EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT1EnergyCubeNetworkPacket::encode).decoder(ClientboundT1EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT1EnergyCubeNetworkPacket::onMessageReceived).add();
        
        T2_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT2EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT2EnergyCubeNetworkPacket::encode).decoder(ClientboundT2EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT2EnergyCubeNetworkPacket::onMessageReceived).add();
        
        T3_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT3EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT3EnergyCubeNetworkPacket::encode).decoder(ClientboundT3EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT3EnergyCubeNetworkPacket::onMessageReceived).add();
        
        T4_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT4EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT4EnergyCubeNetworkPacket::encode).decoder(ClientboundT4EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT4EnergyCubeNetworkPacket::onMessageReceived).add();
        
        T5_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT5EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT5EnergyCubeNetworkPacket::encode).decoder(ClientboundT5EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT5EnergyCubeNetworkPacket::onMessageReceived).add();
        
        T6_ENERGY_NETWORK_CUBE.messageBuilder(ClientboundT6EnergyCubeNetworkPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(ClientboundT6EnergyCubeNetworkPacket::encode).decoder(ClientboundT6EnergyCubeNetworkPacket::decode)
        .consumer(ClientboundT6EnergyCubeNetworkPacket::onMessageReceived).add();
        
        //EXTRACTOR PACKETS
        ENERGY_EXTRACTOR_PRIORITY.messageBuilder(ServerboundExtractorPriorityPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundExtractorPriorityPacket::encode).decoder(ServerboundExtractorPriorityPacket::decode)
        .consumer(ServerboundExtractorPriorityPacket::onMessageReceived).add();
        
        ENERGY_EXTRACTOR_TRANSFER.messageBuilder(ServerboundExtractorTransferPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundExtractorTransferPacket::encode).decoder(ServerboundExtractorTransferPacket::decode)
        .consumer(ServerboundExtractorTransferPacket::onMessageReceived).add();

        ENERGY_EXTRACTOR_SIDE_TOGGLE.messageBuilder(ServerboundExtractorSideConfigurePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundExtractorSideConfigurePacket::encode).decoder(ServerboundExtractorSideConfigurePacket::decode)
        .consumer(ServerboundExtractorSideConfigurePacket::onMessageReceived).add();

        ENERGY_EXTRACTOR_REDSTONE.messageBuilder(ServerboundExtractorRedstoneConfigurePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundExtractorRedstoneConfigurePacket::encode).decoder(ServerboundExtractorRedstoneConfigurePacket::decode)
        .consumer(ServerboundExtractorRedstoneConfigurePacket::onMessageReceived).add();

        ENERGY_EXTRACTOR_COMPARATOR.messageBuilder(ServerboundExtractorComparatorConfigurePacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ServerboundExtractorComparatorConfigurePacket::encode).decoder(ServerboundExtractorComparatorConfigurePacket::decode)
        .consumer(ServerboundExtractorComparatorConfigurePacket::onMessageReceived).add();
    }
    
    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        init();
    }
}