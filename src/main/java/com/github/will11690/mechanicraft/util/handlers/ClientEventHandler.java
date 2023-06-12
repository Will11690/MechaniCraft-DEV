package com.github.will11690.mechanicraft.util.handlers;

import com.github.will11690.mechanicraft.blocks.fluidtank.advfluidtank.AdvFluidTankTER;
import com.github.will11690.mechanicraft.blocks.fluidtank.basicfluidtank.BasicFluidTankTER;
import com.github.will11690.mechanicraft.blocks.fluidtank.elitefluidtank.EliteFluidTankTER;
import com.github.will11690.mechanicraft.blocks.fluidtank.superiorfluidtank.SuperiorFluidTankTER;
import com.github.will11690.mechanicraft.blocks.fluidtank.supremefluidtank.SupremeFluidTankTER;
import com.github.will11690.mechanicraft.blocks.fluidtank.ultimatefluidtank.UltimateFluidTankTER;
import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.init.ModFluids;
import com.github.will11690.mechanicraft.util.Reference;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientEventHandler {
	
    private ClientEventHandler() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
    	
    	ContainerTypeHandler.registerScreens(event);
    	
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_BASIC_FLUID_TANK.get(), BasicFluidTankTER::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_ADVANCED_FLUID_TANK.get(), AdvFluidTankTER::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_ELITE_FLUID_TANK.get(), EliteFluidTankTER::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_SUPERIOR_FLUID_TANK.get(), SuperiorFluidTankTER::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_SUPREME_FLUID_TANK.get(), SupremeFluidTankTER::new);
    	ClientRegistry.bindTileEntityRenderer(TileEntityHandler.TILE_ENTITY_ULTIMATE_FLUID_TANK.get(), UltimateFluidTankTER::new);
    	
    	event.enqueueWork(() -> {

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_FLUID.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_FLUID.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.GOLD_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.GOLD_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.GOLD_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.IRON_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.IRON_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.IRON_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.TIN_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.TIN_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.TIN_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.COPPER_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.COPPER_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.COPPER_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.SILVER_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SILVER_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SILVER_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.LEAD_ORE_SLURRY_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LEAD_ORE_SLURRY_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LEAD_ORE_SLURRY_FLOWING.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_SLAG_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_SLAG_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_SLAG_FLOWING.get(), RenderType.translucent());
            
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.MINING_CHUTE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.STRUCTURE_PIPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LASER_CORE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.T1_ENERGY_CHUTE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.T1_ITEM_CHUTE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.T1_FLUID_CHUTE.get(), RenderType.cutout());
            
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BASIC_FLUID_TANK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ADVANCED_FLUID_TANK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELITE_FLUID_TANK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUPERIOR_FLUID_TANK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUPREME_FLUID_TANK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ULTIMATE_FLUID_TANK.get(), RenderType.cutout());
        });
    }
}