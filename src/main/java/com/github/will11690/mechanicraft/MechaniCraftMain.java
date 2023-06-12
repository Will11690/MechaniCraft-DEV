package com.github.will11690.mechanicraft;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModCreativeModeTabs;
import com.github.will11690.mechanicraft.init.ModFeatures;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.util.Reference;
import com.github.will11690.mechanicraft.util.capabilities.factory.UpgradeGeneratorHandlerFactory;
import com.github.will11690.mechanicraft.util.capabilities.factory.UpgradeMachineHandlerFactory;
import com.github.will11690.mechanicraft.util.capabilities.interfaces.IUpgradeGeneratorHandler;
import com.github.will11690.mechanicraft.util.capabilities.interfaces.IUpgradeMachineHandler;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier1.CapabilityT1EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier2.CapabilityT2EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier3.CapabilityT3EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier4.CapabilityT4EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier5.CapabilityT5EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.chute.tier6.CapabilityT6EnergyChuteNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier1.CapabilityT1EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier2.CapabilityT2EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier3.CapabilityT3EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier4.CapabilityT4EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier5.CapabilityT5EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.network.energy.cube.tier6.CapabilityT6EnergyCubeNetwork;
import com.github.will11690.mechanicraft.util.capabilities.storage.UpgradeGeneratorHandlerStorage;
import com.github.will11690.mechanicraft.util.capabilities.storage.UpgradeMachineHandlerStorage;
import com.github.will11690.mechanicraft.util.handlers.EventHandler;
import com.github.will11690.mechanicraft.util.handlers.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class MechaniCraftMain {

	//TODO CLEAN ALL FORMATTING(Chutes, ModConfigs done)
    //TODO Utility package created for possible ideas in block package, once implementation is decided move to correct packages(IF ADDED AT ALL)

	//TODO TEST CODE FOR FLUIDS IS IN, USE IT TO UPDATE OLD FLUIDS(MAY NEED TO DO SOME RE TEXTURING AND ADD BUCKET TEXTURES)

    public static final Logger MECHANICRAFT_LOGGER = LogManager.getLogger();

	//TODO UPDATE TO 1.19.4
	//TODO Try to figure out IntelliJ

    //TODO Bugs
    //---- Advanced Coal generator consuming 2 fuel to start
    
  	//TODO Energy Pipes(Pipes done now just need to set up extractor and inserter then disable chute block interaction(SHOULD HELP REDUCE MORE LAG CUZ LESS BLOCK STATES))
    //---- GUIS ARE MADE, NEED TO FINISH BLOCK MODELS / TEXTURES
    //---- Make Extractor and inserter with GUI for configuration
    //---- configs will be react to redsone signal/comparator output mode
    //---- Extractors will get input and output based on the network capacity of extracting block(If no network capacity then will base it off that block)
    //---- extraction/insertion limits
    //---- priority per side
    
    //TODO store player health before applying health bonus from armor and set max health back to that once removed
    //TODO bug above may not be the issue, issue is probably if the player has more health than the checked amounts then effect wont be applied
    //TODO fix bug where Fluid Handler Item with fluid matching output tanks can't remove more(add FluidStack matching to tankToInteractWith)
    
    //TODO May replace gem meshes with obsidium
    //TODO Maybe add Line Miner to configs, currently it is 100FE * Hardness of block to mine
    
  	//TODO FEATURES FOR VERSION 1.1.0
    
    //TODO Side config and auto eject for machines
    //TODO Create Fluid Handler for itemstacks of tanks(Capacities and init capabilities already added to TankItem)
    //TODO Create Energy Handler for itemstacks of energy cubes
    //TODO un-hardcode the requirement for all slots in press to be filled for more versatility(most likely add a boolean check with recipes for slots used)
    //TODO localized text for configs
    
  	//TODO Fluid Pipes(Add Functionality)
  	//TODO Item Pipes(Add Functionality)
    
    //TODO Finish tier basic - 6 machines(new machines, all have empty packages)
    
    //TODO Void Ore Miner
    //---- Dimension focus for miner
    //---- Filters for miner
    //---- Implementation will be adding all forge/ores to list then generate random number from 1 to max list size and that will be output
    //---- Will need to see bedrock to work(maybe add all blocks beneath it to list and if any are not air or bedrock set canRun to false)
    //---- Filter will override list to only search for ores in filter
    //---- Dimension focus will override list with only ores in that dimension tag(forge/netherores or forge/endores)
    //---- Output chance will again be a random number generator with speed upgrades shrinking max number by certain amount
    //---- Can find random number generator in Crusher or Sieve recipes
    
    //TODO Quarry
    //---- The Core functions are done and work
    //---- Need to create a laser that points to the blocks/fluids being mined/removed(Laser WIP, is broken but a laser IS drawn)
    //---- Make structure resizeable
    //---- Blockstates for isMiningComple = true and false
    //---- Blockstates for miningStalled
    //---- Make a chunkloading upgrade
    //---- Create GUI for resizing structure, displaying current mining position, mining status, and power
    //---- Make Quarry use power correctly
    
  	//TODO Add heat to liquid slag(and posion, nausea, and hunger when touched), not really possible currently in forge so maybe just add it to lava tag
  	//TODO Add slag reprocessor that turns slag liquid into slag item
  	//TODO create slag(item) recycler
  	//TODO Add matter fabricator type machine
  	//TODO Add UU-Matter type item
  	//TODO Rework basic infuser front texture
    //TODO Creative Upgrade(implement it, item already in)

  	//POSSIBLE FEATURES

  	//TODO Make machines face away from you when shift key is pressed(if(GuiScreen.isShiftPressed())(player.isSneaking maybe??)
  	//TODO Gas Pipe(Maybe eventually not currently planned)(potential Mekanism Support)
  	//TODO Gaseous fluid tank(Maybe eventually not currently planned)(potential Mekanism Support)

    public MechaniCraftMain() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Configs!");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.MECHANICRAFT_SPEC, "mechanicraft-common.toml");
        MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Configs Registered!");
    	
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Baking Configs!");
        ModConfigs.bakeConfig();
        MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Configs Baked!");
    	
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Loading Registry Handler!");
    	RegistryHandler.register(modEventBus);
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registry Handler Loaded!");
    	
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Adding Listener To Event Bus!");
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::setupClient);
        MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Listener Added!");

		modEventBus.addListener(this::addToCreativeTabs);
        
        MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Registering Event Bus!");
        MinecraftForge.EVENT_BUS.register(this);
        MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Event Bus Registered!");
        
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Injecting Capabilities!");
    	
    	//ENERGY CHUTE NETWORKS CAPS
    	CapabilityT1EnergyChuteNetwork.register();
    	CapabilityT2EnergyChuteNetwork.register();
    	CapabilityT3EnergyChuteNetwork.register();
    	CapabilityT4EnergyChuteNetwork.register();
    	CapabilityT5EnergyChuteNetwork.register();
    	CapabilityT6EnergyChuteNetwork.register();
    	
    	//ENERGY CUBE NETWORKS CAPS
    	CapabilityT1EnergyCubeNetwork.register();
    	CapabilityT2EnergyCubeNetwork.register();
    	CapabilityT3EnergyCubeNetwork.register();
    	CapabilityT4EnergyCubeNetwork.register();
    	CapabilityT5EnergyCubeNetwork.register();
    	CapabilityT6EnergyCubeNetwork.register();
    	
    	//MACHINE UPGRADE CAPS
    	CapabilityManager.INSTANCE.register(IUpgradeMachineHandler.class, new UpgradeMachineHandlerStorage(), UpgradeMachineHandlerFactory::new);
    	CapabilityManager.INSTANCE.register(IUpgradeGeneratorHandler.class, new UpgradeGeneratorHandlerStorage(), UpgradeGeneratorHandlerFactory::new);
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Capabilities Injected!");
    	
    	MinecraftForge.EVENT_BUS.register(EventHandler.class);
    	
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "Regeistering World Gen Features!");
    	ModFeatures.registerEvent(event);
    	MECHANICRAFT_LOGGER.info(Reference.MOD_ID + "-" + Reference.VERSION + ": " + "World Gen Features Registered!");

    }
	//TODO
	private void addToCreativeTabs(CreativeModeTabEvent.BuildContents event) {
		if(event.getTab() == ModCreativeModeTabs.MOD_ITEM_GROUP) {
			event.accept(ModItems.COPPER_INGOT.get());
			event.accept(ModItems.TIN_INGOT.get());
			event.accept(ModItems.BRONZE_INGOT.get());
			event.accept(ModItems.SILVER_INGOT.get());
			event.accept(ModItems.EMERONIUM_INGOT.get());
			event.accept(ModItems.ENDONIUM_INGOT.get());
			event.accept(ModItems.GOLD_INFUSED_IRON_INGOT.get());
			event.accept(ModItems.LEAD_INGOT.get());
			event.accept(ModItems.STEEL_INGOT.get());
			event.accept(ModItems.ENDER_INGOT.get());
			event.accept(ModItems.OBSIDIUM_INGOT.get());
			event.accept(ModItems.RUBONIUM_INGOT.get());
			event.accept(ModItems.SAPHONIUM_INGOT.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL.get());
			event.accept(ModItems.DIAMONIUM_CRYSTAL.get());
			event.accept(ModItems.RUBY.get());
			event.accept(ModItems.SAPPHIRE.get());
			event.accept(ModItems.BRONZE_NUGGET.get());
			event.accept(ModItems.COPPER_NUGGET.get());
			event.accept(ModItems.TIN_NUGGET.get());
			event.accept(ModItems.STEEL_NUGGET.get());
			event.accept(ModItems.ENDER_NUGGET.get());
			event.accept(ModItems.SILVER_NUGGET.get());
			event.accept(ModItems.GOLD_INFUSED_IRON_NUGGET.get());
			event.accept(ModItems.DIAMONIUM_CRYSTAL_NUGGET.get());
			event.accept(ModItems.DIAMOND_NUGGET.get());
			event.accept(ModItems.RUBY_NUGGET.get());
			event.accept(ModItems.SAPPHIRE_NUGGET.get());
			event.accept(ModItems.EMERALD_NUGGET.get());
			event.accept(ModItems.OBSIDIUM_NUGGET.get());
			event.accept(ModItems.RUBONIUM_NUGGET.get());
			event.accept(ModItems.SAPHONIUM_NUGGET.get());
			event.accept(ModItems.ENDONIUM_NUGGET.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_NUGGET.get());
			event.accept(ModItems.EMERONIUM_NUGGET.get());
			event.accept(ModItems.LEAD_NUGGET.get());
			event.accept(ModItems.TIN_DUST.get());
			event.accept(ModItems.ENDER_DUST.get());
			event.accept(ModItems.STEEL_DUST.get());
			event.accept(ModItems.COPPER_DUST.get());
			event.accept(ModItems.BRONZE_DUST.get());
			event.accept(ModItems.IRON_DUST.get());
			event.accept(ModItems.GOLD_DUST.get());
			event.accept(ModItems.DIAMOND_DUST.get());
			event.accept(ModItems.EMERALD_DUST.get());
			event.accept(ModItems.SILVER_DUST.get());
			event.accept(ModItems.OBSIDIAN_DUST.get());
			event.accept(ModItems.RUBY_DUST.get());
			event.accept(ModItems.SAPPHIRE_DUST.get());
			event.accept(ModItems.RUBONIUM_DUST.get());
			event.accept(ModItems.EMERONIUM_DUST.get());
			event.accept(ModItems.SAPHONIUM_DUST.get());
			event.accept(ModItems.ENDONIUM_DUST.get());
			event.accept(ModItems.OBSIDIUM_DUST.get());
			event.accept(ModItems.DIAMONIUM_CRYSTAL_DUST.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_DUST.get());
			event.accept(ModItems.GOLD_INFUSED_IRON_DUST.get());
			event.accept(ModItems.LEAD_DUST.get());
			event.accept(ModItems.TIN_DIRTY_CHUNKS.get());
			event.accept(ModItems.COPPER_DIRTY_CHUNKS.get());
			event.accept(ModItems.SILVER_DIRTY_CHUNKS.get());
			event.accept(ModItems.IRON_DIRTY_CHUNKS.get());
			event.accept(ModItems.GOLD_DIRTY_CHUNKS.get());
			event.accept(ModItems.LEAD_DIRTY_CHUNKS.get());
			event.accept(ModItems.TIN_REFINED_CHUNKS.get());
			event.accept(ModItems.COPPER_REFINED_CHUNKS.get());
			event.accept(ModItems.SILVER_REFINED_CHUNKS.get());
			event.accept(ModItems.IRON_REFINED_CHUNKS.get());
			event.accept(ModItems.GOLD_REFINED_CHUNKS.get());
			event.accept(ModItems.LEAD_REFINED_CHUNKS.get());
			event.accept(ModItems.TIN_PURE_CHUNKS.get());
			event.accept(ModItems.COPPER_PURE_CHUNKS.get());
			event.accept(ModItems.SILVER_PURE_CHUNKS.get());
			event.accept(ModItems.GOLD_PURE_CHUNKS.get());
			event.accept(ModItems.IRON_PURE_CHUNKS.get());
			event.accept(ModItems.LEAD_PURE_CHUNKS.get());
			event.accept(ModItems.GOLD_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.IRON_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.COPPER_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.SILVER_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.TIN_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.LEAD_ORE_SLURRY_BUCKET.get());
			event.accept(ModItems.LIQUID_SLAG_BUCKET.get());
			event.accept(ModItems.DIAMONIUM_GEAR.get());
			event.accept(ModItems.EMERONIUM_GEAR.get());
			event.accept(ModItems.ENDONIUM_GEAR.get());
			event.accept(ModItems.GOLD_INFUSED_IRON_GEAR.get());
			event.accept(ModItems.OBSIDIUM_GEAR.get());
			event.accept(ModItems.RUBONIUM_GEAR.get());
			event.accept(ModItems.SAPHONIUM_GEAR.get());
			event.accept(ModItems.WOODEN_GEAR.get());
			event.accept(ModItems.STONE_GEAR.get());
			event.accept(ModItems.IRON_GEAR.get());
			event.accept(ModItems.STRING_MESH.get());
			event.accept(ModItems.REINFORCED_STRING_MESH.get());
			event.accept(ModItems.IRON_MESH.get());
			event.accept(ModItems.REINFORCED_IRON_MESH.get());
			event.accept(ModItems.STEEL_MESH.get());
			event.accept(ModItems.REINFORCED_STEEL_MESH.get());
			event.accept(ModItems.DIAMOND_MESH.get());
			event.accept(ModItems.REINFORCED_DIAMOND_MESH.get());
			event.accept(ModItems.GEM_MESH.get());
			event.accept(ModItems.REINFORCED_GEM_MESH.get());
			event.accept(ModItems.ENDONIUM_MESH.get());
			event.accept(ModItems.REINFORCED_ENDONIUM_MESH.get());
		}

		if(event.getTab() == ModCreativeModeTabs.MOD_TOOL_GROUP) {
			event.accept(ModItems.EMERONIUM_SWORD.get());
			event.accept(ModItems.EMERONIUM_PICKAXE.get());
			event.accept(ModItems.EMERONIUM_SHOVEL.get());
			event.accept(ModItems.EMERONIUM_AXE.get());
			event.accept(ModItems.EMERONIUM_HOE.get());
			event.accept(ModItems.RUBONIUM_SWORD.get());
			event.accept(ModItems.RUBONIUM_PICKAXE.get());
			event.accept(ModItems.RUBONIUM_SHOVEL.get());
			event.accept(ModItems.RUBONIUM_AXE.get());
			event.accept(ModItems.RUBONIUM_HOE.get());
			event.accept(ModItems.SAPHONIUM_SWORD.get());
			event.accept(ModItems.SAPHONIUM_PICKAXE.get());
			event.accept(ModItems.SAPHONIUM_SHOVEL.get());
			event.accept(ModItems.SAPHONIUM_AXE.get());
			event.accept(ModItems.SAPHONIUM_HOE.get());
			event.accept(ModItems.OBSIDIUM_SWORD.get());
			event.accept(ModItems.OBSIDIUM_PICKAXE.get());
			event.accept(ModItems.OBSIDIUM_SHOVEL.get());
			event.accept(ModItems.OBSIDIUM_AXE.get());
			event.accept(ModItems.OBSIDIUM_HOE.get());
			event.accept(ModItems.ENDONIUM_SWORD.get());
			event.accept(ModItems.ENDONIUM_PICKAXE.get());
			event.accept(ModItems.ENDONIUM_SHOVEL.get());
			event.accept(ModItems.ENDONIUM_AXE.get());
			event.accept(ModItems.ENDONIUM_HOE.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_SWORD.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_PICKAXE.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_SHOVEL.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_AXE.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_HOE.get());
			event.accept(ModItems.GLASS_SWORD.get());
			event.accept(ModItems.GLASS_PICKAXE.get());
			event.accept(ModItems.GLASS_SHOVEL.get());
			event.accept(ModItems.GLASS_AXE.get());
			event.accept(ModItems.GLASS_HOE.get());
		}

		if(event.getTab() == ModCreativeModeTabs.MOD_ARMOR_GROUP) {
			event.accept(ModItems.EMERONIUM_BOOTS.get());
			event.accept(ModItems.EMERONIUM_CHESTPLATE.get());
			event.accept(ModItems.EMERONIUM_LEGGINGS.get());
			event.accept(ModItems.EMERONIUM_HELMET.get());
			event.accept(ModItems.ENDONIUM_BOOTS.get());
			event.accept(ModItems.ENDONIUM_CHESTPLATE.get());
			event.accept(ModItems.ENDONIUM_LEGGINGS.get());
			event.accept(ModItems.ENDONIUM_HELMET.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_BOOTS.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_CHESTPLATE.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_LEGGINGS.get());
			event.accept(ModItems.ENDONIUM_CRYSTAL_HELMET.get());
			event.accept(ModItems.SAPHONIUM_BOOTS.get());
			event.accept(ModItems.SAPHONIUM_CHESTPLATE.get());
			event.accept(ModItems.SAPHONIUM_LEGGINGS.get());
			event.accept(ModItems.SAPHONIUM_HELMET.get());
			event.accept(ModItems.RUBONIUM_BOOTS.get());
			event.accept(ModItems.RUBONIUM_CHESTPLATE.get());
			event.accept(ModItems.RUBONIUM_LEGGINGS.get());
			event.accept(ModItems.RUBONIUM_HELMET.get());
			event.accept(ModItems.OBSIDIUM_BOOTS.get());
			event.accept(ModItems.OBSIDIUM_CHESTPLATE.get());
			event.accept(ModItems.OBSIDIUM_LEGGINGS.get());
			event.accept(ModItems.OBSIDIUM_HELMET.get());
			event.accept(ModItems.GLASS_BOOTS.get());
			event.accept(ModItems.GLASS_CHESTPLATE.get());
			event.accept(ModItems.GLASS_LEGGINGS.get());
			event.accept(ModItems.GLASS_HELMET.get());
		}

		if(event.getTab() == ModCreativeModeTabs.MOD_BLOCK_GROUP) {
			event.accept(ModBlocks.BLOCK.get());
		}

		if(event.getTab() == ModCreativeModeTabs.MOD_MACHINES_GROUP) {
			event.accept(ModItems.CAPACITY_UPGRADE.get());
			event.accept(ModItems.EFFICIENCY_UPGRADE.get());
			event.accept(ModItems.SPEED_UPGRADE.get());
			event.accept(ModItems.TRANSFER_UPGRADE.get());
			event.accept(ModItems.CREATIVE_UPGRADE.get());
			event.accept(ModItems.GEAR_PRESS_DIE.get());
			event.accept(ModItems.PLATE_PRESS_DIE.get());
			event.accept(ModItems.ROD_PRESS_DIE.get());
			event.accept(ModBlocks.BLOCK.get());
		}
	}

    private void setupClient(final FMLClientSetupEvent event) {
    	
    	MinecraftForge.EVENT_BUS.register(EventHandler.Client.class);
    }
}