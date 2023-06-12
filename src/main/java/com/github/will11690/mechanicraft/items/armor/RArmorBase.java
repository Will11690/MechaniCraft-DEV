package com.github.will11690.mechanicraft.items.armor;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RArmorBase extends ArmorItem {

    public RArmorBase(ArmorMaterial materialIn, ArmorItem.Type type, Item.Properties builder) {
    	
        super(materialIn, type, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    	
        if(ModConfigs.armorEffectsBool == true && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.RUBONIUM_HELMET.get() &&
           player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.RUBONIUM_CHESTPLATE.get() &&
           player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.RUBONIUM_LEGGINGS.get() &&
           player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.RUBONIUM_BOOTS.get()) {
        	
        	if(ModConfigs.ruboniumArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() < 30.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", 10.0D, AttributeModifier.Operation.ADDITION));
        	}
        	
        } else {
        	
        	if(ModConfigs.ruboniumArmorHealthEffectBool == true && player.getAttribute(Attributes.MAX_HEALTH).getValue() > 20.0D) {
        		
        		player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(new AttributeModifier("MaxHealth", -20.0D, AttributeModifier.Operation.ADDITION));
        	}
        }
    }
}