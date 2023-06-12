package com.github.will11690.mechanicraft.util.handlers;

import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier1.CapabilityT1EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier1.T1EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier2.CapabilityT2EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier2.T2EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier3.CapabilityT3EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier3.T3EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier4.CapabilityT4EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier4.T4EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier5.CapabilityT5EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier5.T5EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier6.CapabilityT6EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier6.T6EnergyChuteNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier1.CapabilityT1EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier1.T1EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier2.CapabilityT2EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier2.T2EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier3.CapabilityT3EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier3.T3EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier4.CapabilityT4EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier4.T4EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier5.CapabilityT5EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier5.T5EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier6.CapabilityT6EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier6.T6EnergyCubeNetworkList;
import com.github.will11690.mechanicraft.util.capabilities.provider.UpgradeGeneratorHandlerProvider;
import com.github.will11690.mechanicraft.util.capabilities.provider.UpgradeMachineHandlerProvider;
import com.github.will11690.mechanicraft.blocks.machines.adv.advcgen.TileEntityAdvancedCoalGenerator;
import com.github.will11690.mechanicraft.blocks.machines.adv.advfurnace.TileEntityAdvancedFurnace;
import com.github.will11690.mechanicraft.util.Reference;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {
	
	public static final ResourceLocation UPGRADE_MACHINE_HANDLER = new ResourceLocation(Reference.MOD_ID, "util/capabilities/factory/upgrademachinehandlerfactory.class");
	public static final ResourceLocation UPGRADE_GENERATOR_HANDLER = new ResourceLocation(Reference.MOD_ID, "util/capabilities/factory/upgradegeneratorhandlerfactory.class");
	public static final ResourceLocation ENERGY_NETWORKS = new ResourceLocation(Reference.MOD_ID, "util/capabilities/network/energy/all");

	@SubscribeEvent
	public static void attachCapabilityGenerator(AttachCapabilitiesEvent<BlockEntity> event) {
		
		if(event.getObject() instanceof TileEntityAdvancedCoalGenerator) {
		
			event.addCapability(UPGRADE_GENERATOR_HANDLER, new UpgradeGeneratorHandlerProvider());
		}
	}
	
	@SubscribeEvent
	public static void attachCapabilityMachine(AttachCapabilitiesEvent<BlockEntity> event) {
	        
		if(event.getObject() instanceof TileEntityAdvancedFurnace) {
			
			event.addCapability(UPGRADE_MACHINE_HANDLER, new UpgradeMachineHandlerProvider());
		}
	}
	
	@SubscribeEvent
	public static void attachCapabilityT1EnergyNetwork(AttachCapabilitiesEvent<Level> event) {
			
		event.addCapability(ENERGY_NETWORKS, new ICapabilityProvider() {

			//ENERGY CHUTE NETWORK CAPS
			private final T1EnergyChuteNetworkList t1EnergyChuteNetworkList = new T1EnergyChuteNetworkList(event.getObject());
			private final T2EnergyChuteNetworkList t2EnergyChuteNetworkList = new T2EnergyChuteNetworkList(event.getObject());
			private final T3EnergyChuteNetworkList t3EnergyChuteNetworkList = new T3EnergyChuteNetworkList(event.getObject());
			private final T4EnergyChuteNetworkList t4EnergyChuteNetworkList = new T4EnergyChuteNetworkList(event.getObject());
			private final T5EnergyChuteNetworkList t5EnergyChuteNetworkList = new T5EnergyChuteNetworkList(event.getObject());
			private final T6EnergyChuteNetworkList t6EnergyChuteNetworkList = new T6EnergyChuteNetworkList(event.getObject());
			
			//ENERGY CUBE NETWORK CAPS
			private final T1EnergyCubeNetworkList t1EnergyCubeNetworkList = new T1EnergyCubeNetworkList(event.getObject());
			private final T2EnergyCubeNetworkList t2EnergyCubeNetworkList = new T2EnergyCubeNetworkList(event.getObject());
			private final T3EnergyCubeNetworkList t3EnergyCubeNetworkList = new T3EnergyCubeNetworkList(event.getObject());
			private final T4EnergyCubeNetworkList t4EnergyCubeNetworkList = new T4EnergyCubeNetworkList(event.getObject());
			private final T5EnergyCubeNetworkList t5EnergyCubeNetworkList = new T5EnergyCubeNetworkList(event.getObject());
			private final T6EnergyCubeNetworkList t6EnergyCubeNetworkList = new T6EnergyCubeNetworkList(event.getObject());

			@Override
			public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
				
				//ENERGY CHUTE NETWORK CAPS
				if(cap == CapabilityT1EnergyChuteNetwork.T1_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t1EnergyChuteNetworkList).cast();
				}
				
				if(cap == CapabilityT2EnergyChuteNetwork.T2_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t2EnergyChuteNetworkList).cast();
				}
				
				if(cap == CapabilityT3EnergyChuteNetwork.T3_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t3EnergyChuteNetworkList).cast();
				}
				
				if(cap == CapabilityT4EnergyChuteNetwork.T4_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t4EnergyChuteNetworkList).cast();
				}
				
				if(cap == CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t5EnergyChuteNetworkList).cast();
				}
				
				if(cap == CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t6EnergyChuteNetworkList).cast();
				}
				
				//ENERGY CUBE NETWORK CAPS
				if(cap == CapabilityT1EnergyCubeNetwork.T1_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t1EnergyCubeNetworkList).cast();
				}
				
				if(cap == CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t2EnergyCubeNetworkList).cast();
				}
				
				if(cap == CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t3EnergyCubeNetworkList).cast();
				}
				
				if(cap == CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t4EnergyCubeNetworkList).cast();
				}
				
				if(cap == CapabilityT5EnergyCubeNetwork.T5_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t5EnergyCubeNetworkList).cast();
				}
				
				if(cap == CapabilityT6EnergyCubeNetwork.T6_NETWORK_LIST) {
					
					return LazyOptional.of(() -> t6EnergyCubeNetworkList).cast();
				}
				
				return LazyOptional.empty();
			}
		});
	}
}
