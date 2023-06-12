package com.github.will11690.mechanicraft.items.armor;

import com.github.will11690.mechanicraft.init.ModConfigs;
import com.github.will11690.mechanicraft.init.ModItems;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EMArmorBase extends ArmorItem {

    public EMArmorBase(ArmorMaterial materialIn, ArmorItem.Type type, Item.Properties builder) {
    	
        super(materialIn, type, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
    	
        if(ModConfigs.armorEffectsBool == true && player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.EMERONIUM_HELMET.get() &&
           player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.EMERONIUM_CHESTPLATE.get() &&
           player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.EMERONIUM_LEGGINGS.get() &&
           player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.EMERONIUM_BOOTS.get()) {
        	
        	if(ModConfigs.emeroniumArmorHeroEffectBool == true) {
        	
        		player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 300, 0, false, false, false));
        		
        	} else {
        		
        		if(ModConfigs.emeroniumArmorHeroEffectBool == true) {
        			
        			player.removeEffect(MobEffects.HERO_OF_THE_VILLAGE);
        		}
        	}
        }
    }
}