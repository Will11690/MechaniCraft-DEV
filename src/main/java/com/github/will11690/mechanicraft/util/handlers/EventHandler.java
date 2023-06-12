package com.github.will11690.mechanicraft.util.handlers;

import java.util.List;

import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.TileEntityQuarry;
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
import com.github.will11690.mechanicraft.util.render.NewLaserRenderer;
import com.github.will11690.mechanicraft.world.gen.MechanicraftOreGenerator;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import org.joml.Vector3d;

//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void doFlying(TickEvent.PlayerTickEvent event) {

        Player player = event.player;
        CompoundNBT tag = player.getPersistentData();
        boolean wasFlying = tag.getBoolean("wasFlying");

        if(!player.isCreative() && !player.isSpectator()) {

            if(player.hasEffect(RegistryHandler.FLIGHT_EFFECT.get()) && !player.hasEffect(MobEffects.LEVITATION)) {

                if(!player.getAbilities().mayfly){

                    player.getAbilities().mayfly = true;
                    tag.putBoolean("wasFlying", true);
                    player.onUpdateAbilities();
                }

                if(player.isOnGround()){

                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }

            } else if(wasFlying && !player.hasEffect(RegistryHandler.FLIGHT_EFFECT.get()) || player.hasEffect(MobEffects.LEVITATION)) {

                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                tag.putBoolean("wasFlying", false);
                player.onUpdateAbilities();
            }
        }
    }
    
    @SubscribeEvent
    public static void worldTick(TickEvent.LevelTickEvent event) {
		
		if(event.side == LogicalSide.SERVER && event.phase == Phase.START) {
			
			//ENERGY CHUTE NETWORK CAPS
			if(event.level.getCapability(CapabilityT1EnergyChuteNetwork.T1_NETWORK_LIST).isPresent()) {
    		
    			T1EnergyChuteNetworkList t1ChuteNetwork = event.level.getCapability(CapabilityT1EnergyChuteNetwork.T1_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t1ChuteNetwork.tick();
			}
    	
			if(event.level.getCapability(CapabilityT2EnergyChuteNetwork.T2_NETWORK_LIST).isPresent()) {
    		
    			T2EnergyChuteNetworkList t2ChuteNetwork = event.level.getCapability(CapabilityT2EnergyChuteNetwork.T2_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t2ChuteNetwork.tick();
			}
    	
			if(event.level.getCapability(CapabilityT3EnergyChuteNetwork.T3_NETWORK_LIST).isPresent()) {
    		
    			T3EnergyChuteNetworkList t3ChuteNetwork = event.level.getCapability(CapabilityT3EnergyChuteNetwork.T3_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t3ChuteNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT4EnergyChuteNetwork.T4_NETWORK_LIST).isPresent()) {
    		
    			T4EnergyChuteNetworkList t4ChuteNetwork = event.level.getCapability(CapabilityT4EnergyChuteNetwork.T4_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t4ChuteNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST).isPresent()) {
    		
    			T5EnergyChuteNetworkList t5ChuteNetwork = event.level.getCapability(CapabilityT5EnergyChuteNetwork.T5_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t5ChuteNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST).isPresent()) {
    		
    			T6EnergyChuteNetworkList t6ChuteNetwork = event.level.getCapability(CapabilityT6EnergyChuteNetwork.T6_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t6ChuteNetwork.tick();
    		}
    		
    		//ENERGY CUBE NETWORK CAPS
			if(event.level.getCapability(CapabilityT1EnergyCubeNetwork.T1_NETWORK_LIST).isPresent()) {
    		
    			T1EnergyCubeNetworkList t1CubeNetwork = event.level.getCapability(CapabilityT1EnergyCubeNetwork.T1_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t1CubeNetwork.tick();
			}
    	
			if(event.level.getCapability(CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST).isPresent()) {
    		
    			T2EnergyCubeNetworkList t2CubeNetwork = event.level.getCapability(CapabilityT2EnergyCubeNetwork.T2_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t2CubeNetwork.tick();
			}
    	
			if(event.level.getCapability(CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST).isPresent()) {
    		
    			T3EnergyCubeNetworkList t3CubeNetwork = event.level.getCapability(CapabilityT3EnergyCubeNetwork.T3_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t3CubeNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST).isPresent()) {
    		
    			T4EnergyCubeNetworkList t4CubeNetwork = event.level.getCapability(CapabilityT4EnergyCubeNetwork.T4_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t4CubeNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT5EnergyCubeNetwork.T5_NETWORK_LIST).isPresent()) {
    		
    			T5EnergyCubeNetworkList t5CubeNetwork = event.level.getCapability(CapabilityT5EnergyCubeNetwork.T5_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t5CubeNetwork.tick();
    		}
    	
    		if(event.level.getCapability(CapabilityT6EnergyCubeNetwork.T6_NETWORK_LIST).isPresent()) {
    		
    			T6EnergyCubeNetworkList t6CubeNetwork = event.level.getCapability(CapabilityT6EnergyCubeNetwork.T6_NETWORK_LIST).orElseThrow(IllegalStateException::new);
    			t6CubeNetwork.tick();
    		}
    	}
    }

    @SubscribeEvent
    public static void addNBTData(PlayerEvent.PlayerLoggedInEvent event) {

        Player player = event.getEntity();
        CompoundTag tag = player.getPersistentData();
        INBT modeTag = tag.get("wasFlying");

        if(modeTag == null) {

            tag.putBoolean("wasFlying", false);
        }
    }

    @SubscribeEvent
    public static void fallDamageFlight(PlayerFlyableFallEvent event) {

        double distance = event.getDistance();
        Player player = event.getEntity();

        if (distance >= 3 && !player.isCreative() && player.hasEffect(RegistryHandler.FLIGHT_EFFECT.get())) {

            player.hurt(DamageSource.class., 0);
        }
    }
    
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
    	
    	MechanicraftOreGenerator.generateOres(event);
    }
    
    public static final class Client {
    	
        private Client() {}
        //TODO
        @SubscribeEvent
        static void renderWorldLastEvent(RenderLevelLastEvent event) {
            List<AbstractClientPlayer> players = Minecraft.getInstance().level.players();
            ClientLevel level = Minecraft.getInstance().level;
            
            if(!level.isClientSide)
            	return;
            
                for (BlockEntity blockEntity : level.blockEntityList) {
                    if (blockEntity instanceof TileEntityQuarry) {
                    	TileEntityQuarry quarry = (TileEntityQuarry) blockEntity;
                    	for (Player player : players) {
                    		Vector3d bePos = new Vector3d(quarry.getBlockPos().getX(), quarry.getBlockPos().getY(), quarry.getBlockPos().getZ());
                            if (player.distanceToSqr(bePos) > 500) {
                                continue;
                            }
                            
                            PoseStack matrixStackIn = event.getPoseStack();
                            MultiBufferSource bufferIn = Minecraft.getInstance().renderBuffers().bufferSource();
                            
                            NewLaserRenderer.renderLaser(event, quarry, matrixStackIn, bufferIn, false);
                    }
                }
        	}
        }
    }
}