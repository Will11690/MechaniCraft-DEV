package com.github.will11690.mechanicraft.init;

import java.util.function.Supplier;

import com.github.will11690.mechanicraft.fluid.ModFluidTypes;
import com.github.will11690.mechanicraft.util.Reference;
import com.github.will11690.mechanicraft.util.handlers.RegistryHandler;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final RegistryObject<FlowingFluid> SOURCE_GOLD_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("gold_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.GOLD_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_GOLD_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_gold_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.GOLD_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_IRON_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("iron_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.IRON_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_IRON_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_iron_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.IRON_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_COPPER_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("copper_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.COPPER_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_COPPER_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_copper_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.COPPER_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_SILVER_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("silver_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.SILVER_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_SILVER_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_silver_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.SILVER_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_TIN_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("tin_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.TIN_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_TIN_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_tin_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.TIN_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_LEAD_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("lead_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.LEAD_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_LEAD_ORE_SLURRY_FLUID = RegistryHandler.FLUIDS.register("flowing_lead_ore_slurry_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.LEAD_ORE_SLURRY_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_LIQUID_SLAG_FLUID = RegistryHandler.FLUIDS.register("liquid_slag_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.LIQUID_SLAG_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_LIQUID_SLAG_FLUID = RegistryHandler.FLUIDS.register("flowing_liquid_slag_fluid", () -> new ForgeFlowingFluid.Flowing(ModFluids.LIQUID_SLAG_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties GOLD_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.GOLD_ORE_SLURRY_FLUID, SOURCE_GOLD_ORE_SLURRY_FLUID, FLOWING_GOLD_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.GOLD_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties IRON_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.IRON_ORE_SLURRY_FLUID, SOURCE_IRON_ORE_SLURRY_FLUID, FLOWING_IRON_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.IRON_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties COPPER_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.COPPER_ORE_SLURRY_FLUID, SOURCE_COPPER_ORE_SLURRY_FLUID, FLOWING_COPPER_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.COPPER_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties SILVER_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.SILVER_ORE_SLURRY_FLUID, SOURCE_SILVER_ORE_SLURRY_FLUID, FLOWING_SILVER_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.SILVER_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties TIN_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.TIN_ORE_SLURRY_FLUID, SOURCE_TIN_ORE_SLURRY_FLUID, FLOWING_TIN_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.TIN_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties LEAD_ORE_SLURRY_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.LEAD_ORE_SLURRY_FLUID, SOURCE_LEAD_ORE_SLURRY_FLUID, FLOWING_LEAD_ORE_SLURRY_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(6).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.LEAD_ORE_SLURRY_BUCKET);

    public static final ForgeFlowingFluid.Properties LIQUID_SLAG_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.LIQUID_SLAG_FLUID, SOURCE_LIQUID_SLAG_FLUID, FLOWING_LIQUID_SLAG_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(8).tickRate(5).explosionResistance(Float.MAX_VALUE)
            .block(ModBlocks.TEST_FLUID_BLOCK).bucket(ModItems.LIQUID_SLAG_BUCKET);

    public static void registerFluids(IEventBus eventBus) {

        RegistryHandler.FLUIDS.register(eventBus);
    }
    /*public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    //GOLD SLURRY
    public static final RegistryObject<FlowingFluid> GOLD_ORE_SLURRY_FLUID = registerFluid("gold_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.GOLD_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> GOLD_ORE_SLURRY_FLOWING = registerFluid("gold_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.GOLD_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties GOLD_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> GOLD_ORE_SLURRY_FLUID.get(), () -> GOLD_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCCe6e600)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.GOLD_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.GOLD_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> GOLD_ORE_SLURRY_BLOCK = registerFluidBlock("gold_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.GOLD_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //IRON SLURRY
    public static final RegistryObject<FlowingFluid> IRON_ORE_SLURRY_FLUID = registerFluid("iron_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.IRON_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> IRON_ORE_SLURRY_FLOWING = registerFluid("iron_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.IRON_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties IRON_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> IRON_ORE_SLURRY_FLUID.get(), () -> IRON_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCCbfbfbf)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.IRON_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.IRON_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> IRON_ORE_SLURRY_BLOCK = registerFluidBlock("iron_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.IRON_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //COPPER SLURRY
    public static final RegistryObject<FlowingFluid> COPPER_ORE_SLURRY_FLUID = registerFluid("copper_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.COPPER_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> COPPER_ORE_SLURRY_FLOWING = registerFluid("copper_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.COPPER_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties COPPER_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> COPPER_ORE_SLURRY_FLUID.get(), () -> COPPER_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCCcc6600)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.COPPER_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.COPPER_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> COPPER_ORE_SLURRY_BLOCK = registerFluidBlock("copper_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.COPPER_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //SILVER SLURRY
    public static final RegistryObject<FlowingFluid> SILVER_ORE_SLURRY_FLUID = registerFluid("silver_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.SILVER_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> SILVER_ORE_SLURRY_FLOWING = registerFluid("silver_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.SILVER_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties SILVER_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> SILVER_ORE_SLURRY_FLUID.get(), () -> SILVER_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCCd1d1e0)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.SILVER_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.SILVER_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> SILVER_ORE_SLURRY_BLOCK = registerFluidBlock("silver_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.SILVER_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //TIN SLURRY
    public static final RegistryObject<FlowingFluid> TIN_ORE_SLURRY_FLUID = registerFluid("tin_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.TIN_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> TIN_ORE_SLURRY_FLOWING = registerFluid("tin_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.TIN_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties TIN_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> TIN_ORE_SLURRY_FLUID.get(), () -> TIN_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCCd9d9d9)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.TIN_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.TIN_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> TIN_ORE_SLURRY_BLOCK = registerFluidBlock("tin_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.TIN_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //LEAD SLURRY
    public static final RegistryObject<FlowingFluid> LEAD_ORE_SLURRY_FLUID = registerFluid("lead_ore_slurry_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.LEAD_ORE_SLURRY_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> LEAD_ORE_SLURRY_FLOWING = registerFluid("lead_ore_slurry_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.LEAD_ORE_SLURRY_PROPERTIES));

    public static final ForgeFlowingFluid.Properties LEAD_ORE_SLURRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> LEAD_ORE_SLURRY_FLUID.get(), () -> LEAD_ORE_SLURRY_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(20).luminosity(0).viscosity(10).sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY).overlay(WATER_OVERLAY_RL).color(0xCC7676a2)).slopeFindDistance(1).levelDecreasePerBlock(2)
    .block(() -> ModFluids.LEAD_ORE_SLURRY_BLOCK.get()).bucket(() -> ModItems.LEAD_ORE_SLURRY_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> LEAD_ORE_SLURRY_BLOCK = registerFluidBlock("lead_ore_slurry",
    () -> new FlowingFluidBlock(() -> ModFluids.LEAD_ORE_SLURRY_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    //LIQUID SLAG
    public static final RegistryObject<FlowingFluid> LIQUID_SLAG_FLUID = registerFluid("liquid_slag_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.LIQUID_SLAG_PROPERTIES));
    
    public static final RegistryObject<FlowingFluid> LIQUID_SLAG_FLOWING = registerFluid("liquid_slag_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.LIQUID_SLAG_PROPERTIES));

    public static final ForgeFlowingFluid.Properties LIQUID_SLAG_PROPERTIES = new ForgeFlowingFluid.Properties(() -> LIQUID_SLAG_FLUID.get(), () -> LIQUID_SLAG_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
    .density(30).luminosity(0).viscosity(20).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA).overlay(WATER_OVERLAY_RL).color(0xE6737373)).slopeFindDistance(1).levelDecreasePerBlock(1)
    .block(() -> ModFluids.LIQUID_SLAG_BLOCK.get()).bucket(() -> ModItems.LIQUID_SLAG_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> LIQUID_SLAG_BLOCK = registerFluidBlock("liquid_slag",
    () -> new FlowingFluidBlock(() -> ModFluids.LIQUID_SLAG_FLUID.get(), Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    public static void register() {}
    
    private static <T extends FlowingFluid> RegistryObject<T> registerFluid(String name, Supplier<T> fluid) {
        return RegistryHandler.FLUIDS.register(name, fluid);
    }

    private static <T extends FlowingFluidBlock> RegistryObject<T> registerFluidBlock(String name, Supplier<T> block) {
        return RegistryHandler.BLOCKS.register(name, block);
    }*/
}