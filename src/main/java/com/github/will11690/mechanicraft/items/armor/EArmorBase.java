package com.github.will11690.mechanicraft.items.armor;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;

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

public class EArmorBase extends ArmorItem {

    public EArmorBase(ArmorMaterial materialIn, ArmorItem.Type type, Item.Properties builder) {
    	
        super(materialIn, type, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    	
        if(ModConfigs.armorEffectsBool == true && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.ENDONIUM_HELMET.get() &&
           player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.ENDONIUM_CHESTPLATE.get() &&
           player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.ENDONIUM_LEGGINGS.get() &&
           player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.ENDONIUM_BOOTS.get()) {
        	
        	if(ModConfigs.endoniumArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() < 30.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", 10.0D, AttributeModifier.Operation.ADDITION));
        	}
        	
        	if(ModConfigs.endoniumArmorSpeedEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 2, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumArmorHeroEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 300, 0, false, false, false));
        	}
        	
        	if(ModConfigs.endoniumArmorFireEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, false, false, false));
        	}
        	
			if(ModConfigs.endoniumArmorWaterEffectBool == true) {
				
				player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0, false, false, false));
        	}
			
			if(ModConfigs.endoniumArmorVisionEffectBool == true) {
				
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false, false));
        	}
			
        } else {
        	
        	if(ModConfigs.endoniumArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() > 20.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", -10.0D, AttributeModifier.Operation.ADDITION));
        	}
        	
        	if(ModConfigs.endoniumArmorSpeedEffectBool == true) {
        		
        		player.removeEffect(MobEffects.MOVEMENT_SPEED);
        	}
        	
        	if(ModConfigs.endoniumArmorHeroEffectBool == true) {
        		
        		player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE);
        	}
        	
        	if(ModConfigs.endoniumArmorFireEffectBool == true) {
        		
        		player.removeEffect(MobEffects.FIRE_RESISTANCE);
        	}
        	
        	if(ModConfigs.endoniumArmorWaterEffectBool == true) {
        		
        		player.removeEffect(MobEffects.WATER_BREATHING);
        	}
        	
        	if(ModConfigs.endoniumArmorVisionEffectBool == true) {
        		
        		player.removeEffect(MobEffects.NIGHT_VISION);
        	}
        }
    }
}