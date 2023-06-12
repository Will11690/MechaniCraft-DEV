package com.github.will11690.mechanicraft.items.armor;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.util.handlers.RegistryHandler;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ECArmorBase extends ArmorItem {

    public ECArmorBase(ArmorMaterial materialIn, ArmorItem.Type type, Item.Properties builder) {
    	
        super(materialIn, type, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    	
        if(ModConfigs.armorEffectsBool == true && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.ENDONIUM_CRYSTAL_HELMET.get() &&
           player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.ENDONIUM_CRYSTAL_CHESTPLATE.get() &&
           player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.ENDONIUM_CRYSTAL_LEGGINGS.get() &&
           player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.ENDONIUM_CRYSTAL_BOOTS.get()) {
        	
        	if(ModConfigs.endoniumCrystalArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() < 40.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", 20.0D, AttributeModifier.Operation.ADDITION));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorFlightEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(RegistryHandler.FLIGHT_EFFECT.get(), 300, 0, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorSpeedEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorHeroEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 300, 0, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorFireEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorWaterEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorVisionEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false, false));
        	}
        	
        } else {
        	
        	if(ModConfigs.endoniumCrystalArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() > 20.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", -20.0D, AttributeModifier.Operation.ADDITION));
        	}

        	if(ModConfigs.endoniumCrystalArmorFlightEffectBool == true) {
        		
        		player.removeEffect(RegistryHandler.FLIGHT_EFFECT.get());
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorSpeedEffectBool == true) {
        		
        		player.removeEffect(MobEffects.MOVEMENT_SPEED);
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorHeroEffectBool == true) {
        		
        		player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE);
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorFireEffectBool == true) {
        		
        		player.removeEffect(MobEffects.FIRE_RESISTANCE);
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorWaterEffectBool == true) {
        		
        		player.removeEffect(MobEffects.WATER_BREATHING);
        	}
        	
        	if(ModConfigs.endoniumCrystalArmorVisionEffectBool == true) {
        		
        		player.removeEffect(MobEffects.NIGHT_VISION);
        	}
        }
    }
}