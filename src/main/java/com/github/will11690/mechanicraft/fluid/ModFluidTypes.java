package com.github.will11690.mechanicraft.fluid;

import com.github.will11690.mechanicraft.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    public static final ResourceLocation GOLD_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation GOLD_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation GOLD_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation IRON_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation IRON_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation IRON_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation COPPER_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation COPPER_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation COPPER_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation SILVER_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation SILVER_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation SILVER_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation TIN_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation TIN_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation TIN_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation LEAD_ORE_SLURRY_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation LEAD_ORE_SLURRY_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation LEAD_ORE_SLURRY_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final ResourceLocation LIQUID_SLAG_FLUID_STILL = new ResourceLocation("block/fluid_still");
    public static final ResourceLocation LIQUID_SLAG_FLUID_FLOWING = new ResourceLocation("block/fluid_flowing");
    public static final ResourceLocation LIQUID_SLAG_FLUID_OVERLAY = new ResourceLocation(Reference.MOD_ID, "misc/fluid_overlay");
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Reference.MOD_ID);

    public static final RegistryObject<FluidType> GOLD_ORE_SLURRY_FLUID = register("gold_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> IRON_ORE_SLURRY_FLUID = register("iron_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> COPPER_ORE_SLURRY_FLUID = register("copper_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> SILVER_ORE_SLURRY_FLUID = register("silver_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> TIN_ORE_SLURRY_FLUID = register("tin_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> LEAD_ORE_SLURRY_FLUID = register("lead_ore_slurry_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));
    public static final RegistryObject<FluidType> LIQUID_SLAG_FLUID = register("liquid_slag_fluid",
            FluidType.Properties.create().canConvertToSource(false).canSwim(true).canExtinguish(false).canHydrate(false).canDrown(true).supportsBoating(false).canPushEntity(true)
                    .sound(SoundAction.get("swim"), SoundEvents.BUCKET_FILL_LAVA).temperature(1000).lightLevel(2).density(20).viscosity(10));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        //TODO look into seeing if this is the correct way to register fluids, probably not but it may work for now
        if(name.equals("gold_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(GOLD_ORE_SLURRY_FLUID_STILL, GOLD_ORE_SLURRY_FLUID_FLOWING, GOLD_ORE_SLURRY_FLUID_OVERLAY,
                    0xCCe6e600/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(230f / 255f/*RED*/, 230f / 255f/*GREEN*/, 1f / 255f/*BLUE*/), properties));
        }

        if(name.equals("iron_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(IRON_ORE_SLURRY_FLUID_STILL, IRON_ORE_SLURRY_FLUID_FLOWING, IRON_ORE_SLURRY_FLUID_OVERLAY,
                    0xCCbfbfbf/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(191f / 255f/*RED*/, 191f / 255f/*GREEN*/, 191f / 255f/*BLUE*/), properties));
        }

        if(name.equals("copper_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(COPPER_ORE_SLURRY_FLUID_STILL, COPPER_ORE_SLURRY_FLUID_FLOWING, COPPER_ORE_SLURRY_FLUID_OVERLAY,
                    0xCCcc6600/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(218f / 255f/*RED*/, 239f / 255f/*GREEN*/, 255f / 255f/*BLUE*/), properties));
        }

        if(name.equals("silver_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(SILVER_ORE_SLURRY_FLUID_STILL, SILVER_ORE_SLURRY_FLUID_FLOWING, SILVER_ORE_SLURRY_FLUID_OVERLAY,
                    0xCCdaefff/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(224f / 255f/*RED*/, 56f / 255f/*GREEN*/, 208f / 255f/*BLUE*/), properties));
        }

        if(name.equals("tin_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(TIN_ORE_SLURRY_FLUID_STILL, TIN_ORE_SLURRY_FLUID_FLOWING, TIN_ORE_SLURRY_FLUID_OVERLAY,
                    0xCCd9d9d9/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(217f / 255f/*RED*/, 217f / 255f/*GREEN*/, 217f / 255f/*BLUE*/), properties));
        }

        if(name.equals("lead_ore_slurry_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(LEAD_ORE_SLURRY_FLUID_STILL, LEAD_ORE_SLURRY_FLUID_FLOWING, LEAD_ORE_SLURRY_FLUID_OVERLAY,
                    0xCC7676a2/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(118f / 255f/*RED*/, 118f / 255f/*GREEN*/, 162f / 255f/*BLUE*/), properties));
        }

        if(name.equals("liquid_slag_fluid")) {

            return FLUID_TYPES.register(name, () -> new BaseFluidTypes(LIQUID_SLAG_FLUID_STILL, LIQUID_SLAG_FLUID_FLOWING, LIQUID_SLAG_FLUID_OVERLAY,
                    0xE6737373/*tint color: Hex Dec 0x, 1st 2 alpha, 2nd 2 RED, 3rd 2 GREEN, 4th 2 BLUE*/,
                    new Vector3f(115f / 255f/*RED*/, 115f / 255f/*GREEN*/, 115f / 255f/*BLUE*/), properties));
        }

        return null;
    }

    public static void registerFluids(IEventBus eventBus) {

        FLUID_TYPES.register(eventBus);
    }
}
