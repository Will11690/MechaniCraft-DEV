package com.github.will11690.mechanicraft.init;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.MechaniCraftMain;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.T1EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor.T1EnergyExtractor;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier2.T2EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier3.T3EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier4.T4EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier5.T5EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.energychute.tier6.T6EnergyChute;
import com.github.will11690.mechanicraft.blocks.chute.fluidchute.tier1.T1FluidChute;
import com.github.will11690.mechanicraft.blocks.chute.itemchute.tier1.T1ItemChute;
import com.github.will11690.mechanicraft.blocks.fluidtank.advfluidtank.AdvFluidTank;
import com.github.will11690.mechanicraft.blocks.fluidtank.basicfluidtank.BasicFluidTank;
import com.github.will11690.mechanicraft.blocks.fluidtank.elitefluidtank.EliteFluidTank;
import com.github.will11690.mechanicraft.blocks.fluidtank.superiorfluidtank.SuperiorFluidTank;
import com.github.will11690.mechanicraft.blocks.fluidtank.supremefluidtank.SupremeFluidTank;
import com.github.will11690.mechanicraft.blocks.fluidtank.ultimatefluidtank.UltimateFluidTank;
import com.github.will11690.mechanicraft.blocks.machines.adv.advcgen.AdvancedCoalGenerator;
import com.github.will11690.mechanicraft.blocks.machines.adv.advfurnace.AdvancedFurnace;
import com.github.will11690.mechanicraft.blocks.machines.basic.basiccgen.BasicCoalGenerator;
import com.github.will11690.mechanicraft.blocks.machines.basic.basicfurnace.BasicFurnace;
import com.github.will11690.mechanicraft.blocks.machines.basic.basicinfuser.BasicMetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1crusher.T1Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1energycube.T1EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1infuser.T1MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1orewasher.T1OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1poweredsieve.T1PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1press.T1Press;
import com.github.will11690.mechanicraft.blocks.machines.tier1.t1slurryprocessor.T1SlurryProcessor;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2crusher.T2Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2energycube.T2EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2infuser.T2MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2orewasher.T2OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2poweredsieve.T2PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2press.T2Press;
import com.github.will11690.mechanicraft.blocks.machines.tier2.t2slurryprocessor.T2SlurryProcessor;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3crusher.T3Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3energycube.T3EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3infuser.T3MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3orewasher.T3OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3poweredsieve.T3PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3press.T3Press;
import com.github.will11690.mechanicraft.blocks.machines.tier3.t3slurryprocessor.T3SlurryProcessor;
import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.Quarry;
import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.structure.LaserCore;
import com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.structure.StructurePipe;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4crusher.T4Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4energycube.T4EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4infuser.T4MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4orewasher.T4OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4poweredsieve.T4PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4press.T4Press;
import com.github.will11690.mechanicraft.blocks.machines.tier4.t4slurryprocessor.T4SlurryProcessor;
import com.github.will11690.mechanicraft.blocks.machines.tier5.lineminer.LineMiner;
import com.github.will11690.mechanicraft.blocks.machines.tier5.lineminer.miningchute.MiningChute;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5crusher.T5Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5energycube.T5EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5infuser.T5MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5orewasher.T5OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5poweredsieve.T5PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5press.T5Press;
import com.github.will11690.mechanicraft.blocks.machines.tier5.t5slurryprocessor.T5SlurryProcessor;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6crusher.T6Crusher;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6energycube.T6EnergyCube;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6infuser.T6MetallicInfuser;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6orewasher.T6OreWasher;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6poweredsieve.T6PoweredSieve;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6press.T6Press;
import com.github.will11690.mechanicraft.blocks.machines.tier6.t6slurryprocessor.T6SlurryProcessor;
import com.github.will11690.mechanicraft.items.machines.MachineItem;
import com.github.will11690.mechanicraft.items.tanks.TankItem;
import com.github.will11690.mechanicraft.util.Reference;
import com.github.will11690.mechanicraft.util.handlers.RegistryHandler;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlocks {

	//ORES
    public static final RegistryObject<Block> COPPER_ORE = registerBlock("copper_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(2, 3.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> ENDER_ORE = registerBlock("ender_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(3, 3.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(1, 3.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> SILVER_ORE = registerBlock("silver_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(2, 3.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(2, 3.0F).harvestLevel(1).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> RUBY_ORE = registerBlock("ruby_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(3, 3.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));
    
    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore", () ->
		new Block(Block.Properties.of(Material.STONE)
		.strength(3, 3.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
		.requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //BLOCKS
    public static final RegistryObject<Block> ENDER_BLOCK = registerBlock("ender_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> COPPER_BLOCK = registerBlock("copper_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> EMERONIUM_BLOCK = registerBlock("emeronium_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> ENDONIUM_BLOCK = registerBlock("endonium_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> ENDONIUM_CRYSTAL_BLOCK = registerBlock("endonium_crystal_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> GOLD_INFUSED_IRON_BLOCK = registerBlock("gold_infused_iron_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> OBSIDIUM_BLOCK = registerBlock("obsidium_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> RUBONIUM_BLOCK = registerBlock("rubonium_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> RUBY_BLOCK = registerBlock("ruby_block", () ->
    	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> SAPHONIUM_BLOCK = registerBlock("saphonium_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block", () ->
		new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //MACHINE PARTS
    
    //CRAFTING
    public static final RegistryObject<Block> MACHINE_BLOCK = registerBlock("machine_block", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T1_GEAR_BOX = registerBlock("t1_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T2_GEAR_BOX = registerBlock("t2_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T3_GEAR_BOX = registerBlock("t3_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T4_GEAR_BOX = registerBlock("t4_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T5_GEAR_BOX = registerBlock("t5_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    public static final RegistryObject<Block> T6_GEAR_BOX = registerBlock("t6_gear_box", () ->
	new Block(Block.Properties.of(Material.METAL).strength(2, 10).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //CHUTES
    
    //STRUCTURE
	public static final RegistryObject<StructurePipe> STRUCTURE_PIPE = registerBlock("structure_pipe", () ->
	new StructurePipe(Block.Properties.of(Material.GLASS).strength(Float.MAX_VALUE, Float.MAX_VALUE).sound(SoundType.GLASS).noOcclusion()));
	
	public static final RegistryObject<LaserCore> LASER_CORE = registerBlock("laser_core", () ->
	new LaserCore(Block.Properties.of(Material.GLASS).strength(Float.MAX_VALUE, Float.MAX_VALUE).sound(SoundType.GLASS).noOcclusion()));
	
	//MINING
	public static final RegistryObject<MiningChute> MINING_CHUTE = registerBlock("mining_chute", () ->
	new MiningChute(Block.Properties.of(Material.GLASS).strength(Float.MAX_VALUE, Float.MAX_VALUE).sound(SoundType.GLASS).noOcclusion()));
    
    //ENERGY
    public static final RegistryObject<T1EnergyExtractor> T1_ENERGY_EXTRACTOR = registerBlock("t1_energy_extractor", () ->
	new T1EnergyExtractor(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T1EnergyChute> T1_ENERGY_CHUTE = registerBlock("t1_energy_chute", () ->
	new T1EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T2EnergyChute> T2_ENERGY_CHUTE = registerBlock("t2_energy_chute", () ->
	new T2EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T3EnergyChute> T3_ENERGY_CHUTE = registerBlock("t3_energy_chute", () ->
	new T3EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T4EnergyChute> T4_ENERGY_CHUTE = registerBlock("t4_energy_chute", () ->
	new T4EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T5EnergyChute> T5_ENERGY_CHUTE = registerBlock("t5_energy_chute", () ->
	new T5EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    public static final RegistryObject<T6EnergyChute> T6_ENERGY_CHUTE = registerBlock("t6_energy_chute", () ->
	new T6EnergyChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    //ITEM
    public static final RegistryObject<T1ItemChute> T1_ITEM_CHUTE = registerBlock("t1_item_chute",
			() -> new T1ItemChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    //FLUID

	public static final RegistryObject<LiquidBlock> TEST_FLUID_BLOCK = registerBlock("test_fluid_block",
			() -> new LiquidBlock(ModFluids.SOURCE_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<T1FluidChute> T1_FLUID_CHUTE = registerBlock("t1_fluid_chute", () ->
	new T1FluidChute(Block.Properties.of(Material.GLASS).strength(1, 10).sound(SoundType.METAL).noOcclusion()));
    
    //FLUID TANKS
    
    //BASIC
    public static final RegistryObject<BasicFluidTank> BASIC_FLUID_TANK = registerBlock("basic_fluid_tank", () ->
	new BasicFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //ADVANCED
    public static final RegistryObject<AdvFluidTank> ADVANCED_FLUID_TANK = registerBlock("advanced_fluid_tank", () ->
	new AdvFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //ELITE
    public static final RegistryObject<EliteFluidTank> ELITE_FLUID_TANK = registerBlock("elite_fluid_tank", () ->
	new EliteFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //SUPERIOR
    public static final RegistryObject<SuperiorFluidTank> SUPERIOR_FLUID_TANK = registerBlock("superior_fluid_tank", () ->
	new SuperiorFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //SUPREME
    public static final RegistryObject<SupremeFluidTank> SUPREME_FLUID_TANK = registerBlock("supreme_fluid_tank", () ->
	new SupremeFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //ULTIMATE
    public static final RegistryObject<UltimateFluidTank> ULTIMATE_FLUID_TANK = registerBlock("ultimate_fluid_tank", () ->
	new UltimateFluidTank(Block.Properties.of(Material.GLASS).strength(1, 20).sound(SoundType.GLASS).noOcclusion()));
    
    //MACHINES
    
    //BASIC
	public static final RegistryObject<BasicMetallicInfuser> BASIC_METALLIC_INFUSER = registerBlock("basic_metallic_infuser", () ->
	new BasicMetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<BasicCoalGenerator> BASIC_COAL_GENERATOR = registerBlock("basic_coal_generator", () ->
	new BasicCoalGenerator(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<BasicFurnace> BASIC_FURNACE = registerBlock("basic_furnace", () ->
	new BasicFurnace(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
    //ADVANCED
	public static final RegistryObject<AdvancedCoalGenerator> ADVANCED_COAL_GENERATOR = registerBlock("advanced_coal_generator", () ->
	new AdvancedCoalGenerator(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<AdvancedFurnace> ADVANCED_FURNACE = registerBlock("advanced_furnace", () ->
	new AdvancedFurnace(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
    //T1 Machines
	public static final RegistryObject<T1EnergyCube> T1_ENERGY_CUBE = registerBlock("t1_energy_cube", () ->
	new T1EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1Crusher> T1_CRUSHER = registerBlock("t1_crusher", () ->
	new T1Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1Press> T1_PRESS = registerBlock("t1_press", () ->
	new T1Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1MetallicInfuser> T1_METALLIC_INFUSER = registerBlock("t1_metallic_infuser", () ->
	new T1MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1PoweredSieve> T1_POWERED_SIEVE = registerBlock("t1_powered_sieve", () ->
	new T1PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1OreWasher> T1_ORE_WASHER = registerBlock("t1_ore_washer", () ->
	new T1OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T1SlurryProcessor> T1_SLURRY_PROCESSOR = registerBlock("t1_slurry_processor", () ->
	new T1SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));//PAUSE
	
    //T2 Machines
	public static final RegistryObject<T2EnergyCube> T2_ENERGY_CUBE = registerBlock("t2_energy_cube", () ->
	new T2EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2Crusher> T2_CRUSHER = registerBlock("t2_crusher", () ->
	new T2Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2Press> T2_PRESS = registerBlock("t2_press", () ->
	new T2Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2MetallicInfuser> T2_METALLIC_INFUSER = registerBlock("t2_metallic_infuser", () ->
	new T2MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2PoweredSieve> T2_POWERED_SIEVE = registerBlock("t2_powered_sieve", () ->
	new T2PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2OreWasher> T2_ORE_WASHER = registerBlock("t2_ore_washer", () ->
	new T2OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T2SlurryProcessor> T2_SLURRY_PROCESSOR = registerBlock("t2_slurry_processor", () ->
	new T2SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //T3 Machines
	public static final RegistryObject<T3EnergyCube> T3_ENERGY_CUBE = registerBlock("t3_energy_cube", () ->
	new T3EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3Crusher> T3_CRUSHER = registerBlock("t3_crusher", () ->
	new T3Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3Press> T3_PRESS = registerBlock("t3_press", () ->
	new T3Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3MetallicInfuser> T3_METALLIC_INFUSER = registerBlock("t3_metallic_infuser", () ->
	new T3MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3PoweredSieve> T3_POWERED_SIEVE = registerBlock("t3_powered_sieve", () ->
	new T3PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3OreWasher> T3_ORE_WASHER = registerBlock("t3_ore_washer", () ->
	new T3OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T3SlurryProcessor> T3_SLURRY_PROCESSOR = registerBlock("t3_slurry_processor", () ->
	new T3SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //T4 Machines
	public static final RegistryObject<T4EnergyCube> T4_ENERGY_CUBE = registerBlock("t4_energy_cube", () ->
	new T4EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4Crusher> T4_CRUSHER = registerBlock("t4_crusher", () ->
	new T4Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4Press> T4_PRESS = registerBlock("t4_press", () ->
	new T4Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4MetallicInfuser> T4_METALLIC_INFUSER = registerBlock("t4_metallic_infuser", () ->
	new T4MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4PoweredSieve> T4_POWERED_SIEVE = registerBlock("t4_powered_sieve", () ->
	new T4PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4OreWasher> T4_ORE_WASHER = registerBlock("t4_ore_washer", () ->
	new T4OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T4SlurryProcessor> T4_SLURRY_PROCESSOR = registerBlock("t4_slurry_processor", () ->
	new T4SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<Quarry> QUARRY = registerBlock("quarry", () ->
	new Quarry(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //T5 Machines
	public static final RegistryObject<LineMiner> LINE_MINER = registerBlock("line_miner", () ->
	new LineMiner(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).noOcclusion().harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5EnergyCube> T5_ENERGY_CUBE = registerBlock("t5_energy_cube", () ->
	new T5EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5Crusher> T5_CRUSHER = registerBlock("t5_crusher", () ->
	new T5Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5Press> T5_PRESS = registerBlock("t5_press", () ->
	new T5Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5MetallicInfuser> T5_METALLIC_INFUSER = registerBlock("t5_metallic_infuser", () ->
	new T5MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5PoweredSieve> T5_POWERED_SIEVE = registerBlock("t5_powered_sieve", () ->
	new T5PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5OreWasher> T5_ORE_WASHER = registerBlock("t5_ore_washer", () ->
	new T5OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T5SlurryProcessor> T5_SLURRY_PROCESSOR = registerBlock("t5_slurry_processor", () ->
	new T5SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
    
    //T6 Machines
	public static final RegistryObject<T6EnergyCube> T6_ENERGY_CUBE = registerBlock("t6_energy_cube", () ->
	new T6EnergyCube(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6Crusher> T6_CRUSHER = registerBlock("t6_crusher", () ->
	new T6Crusher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6Press> T6_PRESS = registerBlock("t6_press", () ->
	new T6Press(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6MetallicInfuser> T6_METALLIC_INFUSER = registerBlock("t6_metallic_infuser", () ->
	new T6MetallicInfuser(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6PoweredSieve> T6_POWERED_SIEVE = registerBlock("t6_powered_sieve", () ->
	new T6PoweredSieve(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6OreWasher> T6_ORE_WASHER = registerBlock("t6_ore_washer", () ->
	new T6OreWasher(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<T6SlurryProcessor> T6_SLURRY_PROCESSOR = registerBlock("t6_slurry_processor", () ->
	new T6SlurryProcessor(Block.Properties.of(Material.METAL).strength(2, 20).sound(SoundType.METAL).harvestLevel(1).harvestTool(ToolType.PICKAXE)));

	public static void registerBlocks(IEventBus eventBus) {

		RegistryHandler.BLOCKS.register(eventBus);
	}

	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
		RegistryObject<T> toReturn = RegistryHandler.BLOCKS.register(name, block);
		registerBlockItem(name, toReturn);
		return toReturn;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return RegistryHandler.ITEMS.register(name, () -> new BlockItem(block.get(),
				new Item.Properties()));
	}
}