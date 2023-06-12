package com.github.will11690.mechanicraft.items.armor;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SArmorBase extends ArmorItem {

    public SArmorBase(ArmorMaterial materialIn, ArmorItem.Type type, Item.Properties builder) {
    	
        super(materialIn, type, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    	
        if(ModConfigs.armorEffectsBool == true && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SAPHONIUM_HELMET.get() &&
           player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SAPHONIUM_CHESTPLATE.get() &&
           player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SAPHONIUM_LEGGINGS.get() &&
           player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SAPHONIUM_BOOTS.get()) {

        	if(ModConfigs.saphoniumArmorWaterEffectBool == true) {
        		
        		player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0, false, false, false));
        	}
        	
        } else {

        	if(ModConfigs.saphoniumArmorWaterEffectBool == true) {
        		
        		player.removeEffect(MobEffects.WATER_BREATHING);
        	}
        }
    }
}