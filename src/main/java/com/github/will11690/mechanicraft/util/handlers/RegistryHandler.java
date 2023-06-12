package com.github.will11690.mechanicraft.util.handlers;

import com.github.will11690.mechanicraft.MechaniCraftMain;
import com.github.will11690.mechanicraft.effects.FlightEffect;
import com.github.will11690.mechanicraft.fluid.ModFluidTypes;
import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.init.ModFluids;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.init.ModRecipes;
import com.github.will11690.mechanicraft.util.Reference;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {
	
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Reference.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Reference.MOD_ID);
    public static final RegistryObject<MobEffect> FLIGHT_EFFECT = MOB_EFFECTS.register("flight_effect", FlightEffect::new);

    public static void register(IEventBus eventBus) {
        
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Adding Containers To Event Bus!");
        //CONTAINERS.register(eventBus);
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Added Containers To Event Bus!");
        
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Recipes!");
        //eventBus.addGenericListener(IRecipeSerializer.class, ModRecipes::regiserRecipes);
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Recipes Registered!");
        
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Adding Tile Entities To Event Bus!");
        //BLOCK_ENTITIES.register(eventBus);
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Added Tile Entities To Event Bus!");
        
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Adding Effects To Event Bus!");
        //MOB_EFFECTS.register(eventBus);
        //MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Added Effects To Event Bus!");
        
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Non Tile Blocks!");
        ModBlocks.registerBlocks(eventBus);
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Non Tile Blocks Registered!");
        
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Containers!");
        ContainerTypeHandler.register();
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Containers Registered!");
        
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Items!");
        ModItems.registerItems(eventBus);
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Items Registered!");
        
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Fluids!");
        ModFluids.registerFluids(eventBus);
        ModFluidTypes.registerFluids(eventBus);
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Fluids Registered!");
        
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Tile Entities!");
        TileEntityHandler.register();
        MechaniCraftMain.MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Tile Entities Registered!");
        
    }
}