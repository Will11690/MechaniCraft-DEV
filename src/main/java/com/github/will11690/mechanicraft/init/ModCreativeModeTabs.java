package com.github.will11690.mechanicraft.init;

import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.init.ModItems;
import com.github.will11690.mechanicraft.util.Reference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {

	public static CreativeModeTab MOD_ITEM_GROUP;
	public static CreativeModeTab MOD_BLOCK_GROUP;
	public static CreativeModeTab MOD_TOOL_GROUP;
	public static CreativeModeTab MOD_ARMOR_GROUP;
	public static CreativeModeTab MOD_MACHINES_GROUP;

	@SubscribeEvent
	public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
		MOD_ITEM_GROUP = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "mechanicraft_items_tab"),
				builder -> builder.icon(() -> new ItemStack(ModItems.GOLD_INFUSED_IRON_GEAR.get()))
						.title(Component.translatable("creativemodetab.mechanicraft_items_tab")));

		MOD_BLOCK_GROUP = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "mechanicraft_blocks_tab"),
				builder -> builder.icon(() -> new ItemStack(ModBlocks.GOLD_INFUSED_IRON_BLOCK.get()))
						.title(Component.translatable("creativemodetab.mechanicraft_blocks_tab")));

		MOD_TOOL_GROUP = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "mechanicraft_tools_tab"),
				builder -> builder.icon(() -> new ItemStack(ModItems.ENDONIUM_CRYSTAL_PICKAXE.get()))
						.title(Component.translatable("creativemodetab.mechanicraft_tools_tab")));

		MOD_ARMOR_GROUP = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "mechanicraft_armor_tab"),
				builder -> builder.icon(() -> new ItemStack(ModItems.ENDONIUM_CRYSTAL_CHESTPLATE.get()))
						.title(Component.translatable("creativemodetab.mechanicraft_armor_tab")));

		MOD_MACHINES_GROUP = event.registerCreativeModeTab(new ResourceLocation(Reference.MOD_ID, "mechanicraft_machiness_tab"),
				builder -> builder.icon(() -> new ItemStack(ModBlocks.T1_METALLIC_INFUSER.get()))
						.title(Component.translatable("creativemodetab.mechanicraft_machiness_tab")));
	}
}