package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor;

import java.util.Arrays;

import com.github.will11690.mechanicraft.network.PacketHandler;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorComparatorConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorPriorityPacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorRedstoneConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorSideConfigurePacket;
import com.github.will11690.mechanicraft.network.packet.extractor.server.ServerboundExtractorTransferPacket;
import com.github.will11690.mechanicraft.util.Reference;
import com.github.will11690.mechanicraft.util.Utils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenT1EnergyExtractor extends ContainerScreen<ContainerT1EnergyExtractor> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID + ":textures/gui/energy_extractor/t1_energy_extractor.png");
	
	public int redstoneMode = 0;
	public int comparatorMode = 0;
	public int extractorMode = 0;
	public int sideSelect = 0;
	
 	//Energy Bar Texture
 	public static final int ENERGY_BAR_FROM_X = 200;
 	public static final int ENERGY_BAR_FROM_Y = 69;
 	public static final int ENERGY_BAR_WIDTH = 18;
 	public static final int ENERGY_BAR_HEIGHT = 69;
 	public static final int ENERGY_BAR_TO_X = 7;
 	public static final int ENERGY_BAR_TO_Y = 74;
 	
 	//Energy Bar INFO
 	public static final int INFO_ENERGY_BAR_WIDTH = 18;
 	public static final int INFO_ENERGY_BAR_HEIGHT = 69;
 	public static final int INFO_ENERGY_BAR_TO_X = 7;
 	public static final int INFO_ENERGY_BAR_TO_Y = 5;
	
 	//Redstone Mode Button Default Texture
 	public static final int REDSTONE_DEFAULT_FROM_X = 176;
 	public static final int REDSTONE_DEFAULT_FROM_Y = 151;
 	public static final int REDSTONE_DEFAULT_WIDTH = 11;
 	public static final int REDSTONE_DEFAULT_HEIGHT = 11;
 	public static final int REDSTONE_DEFAULT_TO_X = 27;
 	public static final int REDSTONE_DEFAULT_TO_Y = 5;
	
 	//Redstone Mode Button Mouse Over Texture
 	public static final int REDSTONE_DEFAULT_HOVER_FROM_X = 188;
 	public static final int REDSTONE_DEFAULT_HOVER_FROM_Y = 151;
 	public static final int REDSTONE_DEFAULT_HOVER_WIDTH = 11;
 	public static final int REDSTONE_DEFAULT_HOVER_HEIGHT = 11;
 	public static final int REDSTONE_DEFAULT_HOVER_TO_X = 27;
 	public static final int REDSTONE_DEFAULT_HOVER_TO_Y = 5;
	
 	//Redstone Mode Button Low Texture
 	public static final int REDSTONE_LOW_FROM_X = 176;
 	public static final int REDSTONE_LOW_FROM_Y = 140;
 	public static final int REDSTONE_LOW_WIDTH = 11;
 	public static final int REDSTONE_LOW_HEIGHT = 11;
 	public static final int REDSTONE_LOW_TO_X = 27;
 	public static final int REDSTONE_LOW_TO_Y = 5;
	
 	//Redstone Mode Button Low Mouse Over Texture
 	public static final int REDSTONE_LOW_HOVER_FROM_X = 188;
 	public static final int REDSTONE_LOW_HOVER_FROM_Y = 140;
 	public static final int REDSTONE_LOW_HOVER_WIDTH = 11;
 	public static final int REDSTONE_LOW_HOVER_HEIGHT = 11;
 	public static final int REDSTONE_LOW_HOVER_TO_X = 27;
 	public static final int REDSTONE_LOW_HOVER_TO_Y = 5;
	
 	//Redstone Mode Button High Texture
 	public static final int REDSTONE_HIGH_FROM_X = 176;
 	public static final int REDSTONE_HIGH_FROM_Y = 129;
 	public static final int REDSTONE_HIGH_WIDTH = 11;
 	public static final int REDSTONE_HIGH_HEIGHT = 11;
 	public static final int REDSTONE_HIGH_TO_X = 27;
 	public static final int REDSTONE_HIGH_TO_Y = 5;
	
 	//Redstone Mode Button High Mouse Over Texture
 	public static final int REDSTONE_HIGH_HOVER_FROM_X = 188;
 	public static final int REDSTONE_HIGH_HOVER_FROM_Y = 129;
 	public static final int REDSTONE_HIGH_HOVER_WIDTH = 11;
 	public static final int REDSTONE_HIGH_HOVER_HEIGHT = 11;
 	public static final int REDSTONE_HIGH_HOVER_TO_X = 27;
 	public static final int REDSTONE_HIGH_HOVER_TO_Y = 5;
 	
 	//Redstone Mode Button INFO
 	public static final int INFO_REDSTONE_MODE_WIDTH = 11;
 	public static final int INFO_REDSTONE_MODE_HEIGHT = 11;
 	public static final int INFO_REDSTONE_MODE_TO_X = 27;
 	public static final int INFO_REDSTONE_MODE_TO_Y = 5;
	
 	//Comparator Mode Button Default Texture
 	public static final int COMPARATOR_DEFAULT_FROM_X = 176;
 	public static final int COMPARATOR_DEFAULT_FROM_Y = 118;
 	public static final int COMPARATOR_DEFAULT_WIDTH = 11;
 	public static final int COMPARATOR_DEFAULT_HEIGHT = 11;
 	public static final int COMPARATOR_DEFAULT_TO_X = 27;
 	public static final int COMPARATOR_DEFAULT_TO_Y = 18;
	
 	//Comparator Mode Button Mouse Over Texture
 	public static final int COMPARATOR_DEFAULT_HOVER_FROM_X = 188;
 	public static final int COMPARATOR_DEFAULT_HOVER_FROM_Y = 118;
 	public static final int COMPARATOR_DEFAULT_HOVER_WIDTH = 11;
 	public static final int COMPARATOR_DEFAULT_HOVER_HEIGHT = 11;
 	public static final int COMPARATOR_DEFAULT_HOVER_TO_X = 27;
 	public static final int COMPARATOR_DEFAULT_HOVER_TO_Y = 18;
	
 	//Comparator Mode Button Low Texture
 	public static final int COMPARATOR_LOW_FROM_X = 176;
 	public static final int COMPARATOR_LOW_FROM_Y = 107;
 	public static final int COMPARATOR_LOW_WIDTH = 11;
 	public static final int COMPARATOR_LOW_HEIGHT = 11;
 	public static final int COMPARATOR_LOW_TO_X = 27;
 	public static final int COMPARATOR_LOW_TO_Y = 18;
	
 	//Comparator Mode Button Low Mouse Over Texture
 	public static final int COMPARATOR_LOW_HOVER_FROM_X = 188;
 	public static final int COMPARATOR_LOW_HOVER_FROM_Y = 107;
 	public static final int COMPARATOR_LOW_HOVER_WIDTH = 11;
 	public static final int COMPARATOR_LOW_HOVER_HEIGHT = 11;
 	public static final int COMPARATOR_LOW_HOVER_TO_X = 27;
 	public static final int COMPARATOR_LOW_HOVER_TO_Y = 18;
	
 	//Comparator Mode Button High Texture
 	public static final int COMPARATOR_HIGH_FROM_X = 176;
 	public static final int COMPARATOR_HIGH_FROM_Y = 96;
 	public static final int COMPARATOR_HIGH_WIDTH = 11;
 	public static final int COMPARATOR_HIGH_HEIGHT = 11;
 	public static final int COMPARATOR_HIGH_TO_X = 27;
 	public static final int COMPARATOR_HIGH_TO_Y = 18;
	
 	//Comparator Mode Button High Mouse Over Texture
 	public static final int COMPARATOR_HIGH_HOVER_FROM_X = 188;
 	public static final int COMPARATOR_HIGH_HOVER_FROM_Y = 96;
 	public static final int COMPARATOR_HIGH_HOVER_WIDTH = 11;
 	public static final int COMPARATOR_HIGH_HOVER_HEIGHT = 11;
 	public static final int COMPARATOR_HIGH_HOVER_TO_X = 27;
 	public static final int COMPARATOR_HIGH_HOVER_TO_Y = 18;
 	
 	//Comparator Mode Button INFO
 	public static final int INFO_COMPARATOR_MODE_WIDTH = 11;
 	public static final int INFO_COMPARATOR_MODE_HEIGHT = 11;
 	public static final int INFO_COMPARATOR_MODE_TO_X = 27;
 	public static final int INFO_COMPARATOR_MODE_TO_Y = 18;
	
 	//Extractor Toggle Button Default Texture
 	public static final int EXTRACTOR_TOGGLE_FROM_X = 176;
 	public static final int EXTRACTOR_TOGGLE_FROM_Y = 72;
 	public static final int EXTRACTOR_TOGGLE_WIDTH = 12;
 	public static final int EXTRACTOR_TOGGLE_HEIGHT = 12;
 	public static final int EXTRACTOR_TOGGLE_TO_X = 82;
 	public static final int EXTRACTOR_TOGGLE_TO_Y = 47;
	
 	//Extractor Toggle Button Default Mouse Over Texture
 	public static final int EXTRACTOR_TOGGLE_HOVER_FROM_X = 188;
 	public static final int EXTRACTOR_TOGGLE_HOVER_FROM_Y = 72;
 	public static final int EXTRACTOR_TOGGLE_HOVER_WIDTH = 12;
 	public static final int EXTRACTOR_TOGGLE_HOVER_HEIGHT = 12;
 	public static final int EXTRACTOR_TOGGLE_HOVER_TO_X = 82;
 	public static final int EXTRACTOR_TOGGLE_HOVER_TO_Y = 47;
	
 	//Extractor Toggle Button Disabled Texture
 	public static final int EXTRACTOR_TOGGLE_OFF_FROM_X = 176;
 	public static final int EXTRACTOR_TOGGLE_OFF_FROM_Y = 84;
 	public static final int EXTRACTOR_TOGGLE_OFF_WIDTH = 12;
 	public static final int EXTRACTOR_TOGGLE_OFF_HEIGHT = 12;
 	public static final int EXTRACTOR_TOGGLE_OFF_TO_X = 82;
 	public static final int EXTRACTOR_TOGGLE_OFF_TO_Y = 47;
	
 	//Extractor Toggle Button Disabled Mouse Over Texture
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_FROM_X = 188;
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_FROM_Y = 84;
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_WIDTH = 12;
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_HEIGHT = 12;
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_TO_X = 82;
 	public static final int EXTRACTOR_TOGGLE_OFF_HOVER_TO_Y = 47;
 	
 	//Extractor Toggle Button INFO
 	public static final int INFO_EXTRACTOR_TOGGLE_WIDTH = 12;
 	public static final int INFO_EXTRACTOR_TOGGLE_HEIGHT = 12;
 	public static final int INFO_EXTRACTOR_TOGGLE_TO_X = 82;
 	public static final int INFO_EXTRACTOR_TOGGLE_TO_Y = 47;
	
 	//Priority Increase Texture
 	public static final int PRIORITY_INCREASE_FROM_X = 176;
 	public static final int PRIORITY_INCREASE_FROM_Y = 162;
 	public static final int PRIORITY_INCREASE_WIDTH = 7;
 	public static final int PRIORITY_INCREASE_HEIGHT = 7;
 	public static final int PRIORITY_INCREASE_TO_X = 45;
 	public static final int PRIORITY_INCREASE_TO_Y = 67;
	
 	//Priority Increase Mouse Over Texture
 	public static final int PRIORITY_INCREASE_HOVER_FROM_X = 188;
 	public static final int PRIORITY_INCREASE_HOVER_FROM_Y = 162;
 	public static final int PRIORITY_INCREASE_HOVER_WIDTH = 7;
 	public static final int PRIORITY_INCREASE_HOVER_HEIGHT = 7;
 	public static final int PRIORITY_INCREASE_HOVER_TO_X = 45;
 	public static final int PRIORITY_INCREASE_HOVER_TO_Y = 67;
 	
 	//Priority Increase Button INFO
 	public static final int INFO_PRIORITY_INCREASE_WIDTH = 7;
 	public static final int INFO_PRIORITY_INCREASE_HEIGHT = 7;
 	public static final int INFO_PRIORITY_INCREASE_TO_X = 45;
 	public static final int INFO_PRIORITY_INCREASE_TO_Y = 67;
	
 	//Priority Decrease Texture
 	public static final int PRIORITY_DECREASE_FROM_X = 176;
 	public static final int PRIORITY_DECREASE_FROM_Y = 169;
 	public static final int PRIORITY_DECREASE_WIDTH = 7;
 	public static final int PRIORITY_DECREASE_HEIGHT = 7;
 	public static final int PRIORITY_DECREASE_TO_X = 28;
 	public static final int PRIORITY_DECREASE_TO_Y = 67;
	
 	//Priority Decrease Mouse Over Texture
 	public static final int PRIORITY_DECREASE_HOVER_FROM_X = 188;
 	public static final int PRIORITY_DECREASE_HOVER_FROM_Y = 169;
 	public static final int PRIORITY_DECREASE_HOVER_WIDTH = 7;
 	public static final int PRIORITY_DECREASE_HOVER_HEIGHT = 7;
 	public static final int PRIORITY_DECREASE_HOVER_TO_X = 28;
 	public static final int PRIORITY_DECREASE_HOVER_TO_Y = 67;
 	
 	//Priority Decrease Button INFO
 	public static final int INFO_PRIORITY_DECREASE_WIDTH = 7;
 	public static final int INFO_PRIORITY_DECREASE_HEIGHT = 7;
 	public static final int INFO_PRIORITY_DECREASE_TO_X = 28;
 	public static final int INFO_PRIORITY_DECREASE_TO_Y = 67;
	
 	//Transfer Increase Texture
 	public static final int TRANSFER_INCREASE_FROM_X = 176;
 	public static final int TRANSFER_INCREASE_FROM_Y = 162;
 	public static final int TRANSFER_INCREASE_WIDTH = 7;
 	public static final int TRANSFER_INCREASE_HEIGHT = 7;
 	public static final int TRANSFER_INCREASE_TO_X = 93;
 	public static final int TRANSFER_INCREASE_TO_Y = 38;
	
 	//Transfer Increase Mouse Over Texture
 	public static final int TRANSFER_INCREASE_HOVER_FROM_X = 188;
 	public static final int TRANSFER_INCREASE_HOVER_FROM_Y = 162;
 	public static final int TRANSFER_INCREASE_HOVER_WIDTH = 7;
 	public static final int TRANSFER_INCREASE_HOVER_HEIGHT = 7;
 	public static final int TRANSFER_INCREASE_HOVER_TO_X = 93;
 	public static final int TRANSFER_INCREASE_HOVER_TO_Y = 38;
 	
 	//Transfer Increase Button INFO
 	public static final int INFO_TRANSFER_INCREASE_WIDTH = 7;
 	public static final int INFO_TRANSFER_INCREASE_HEIGHT = 7;
 	public static final int INFO_TRANSFER_INCREASE_TO_X = 93;
 	public static final int INFO_TRANSFER_INCREASE_TO_Y = 38;
	
 	//Transfer Decrease Texture
 	public static final int TRANSFER_DECREASE_FROM_X = 176;
 	public static final int TRANSFER_DECREASE_FROM_Y = 169;
 	public static final int TRANSFER_DECREASE_WIDTH = 7;
 	public static final int TRANSFER_DECREASE_HEIGHT = 7;
 	public static final int TRANSFER_DECREASE_TO_X = 76;
 	public static final int TRANSFER_DECREASE_TO_Y = 38;
	
 	//Transfer Decrease Mouse Over Texture
 	public static final int TRANSFER_DECREASE_HOVER_FROM_X = 188;
 	public static final int TRANSFER_DECREASE_HOVER_FROM_Y = 169;
 	public static final int TRANSFER_DECREASE_HOVER_WIDTH = 7;
 	public static final int TRANSFER_DECREASE_HOVER_HEIGHT = 7;
 	public static final int TRANSFER_DECREASE_HOVER_TO_X = 76;
 	public static final int TRANSFER_DECREASE_HOVER_TO_Y = 38;
 	
 	//Transfer Decrease Button INFO
 	public static final int INFO_TRANSFER_DECREASE_WIDTH = 7;
 	public static final int INFO_TRANSFER_DECREASE_HEIGHT = 7;
 	public static final int INFO_TRANSFER_DECREASE_TO_X = 76;
 	public static final int INFO_TRANSFER_DECREASE_TO_Y = 38;
	
 	//Up Select Texture
 	public static final int UP_SELECT_FROM_X = 176;
 	public static final int UP_SELECT_FROM_Y = 0;
 	public static final int UP_SELECT_WIDTH = 12;
 	public static final int UP_SELECT_HEIGHT = 12;
 	public static final int UP_SELECT_TO_X = 143;
 	public static final int UP_SELECT_TO_Y = 39;
	
 	//Up Select Mouse Over Texture
 	public static final int UP_SELECT_HOVER_FROM_X = 188;
 	public static final int UP_SELECT_HOVER_FROM_Y = 0;
 	public static final int UP_SELECT_HOVER_WIDTH = 12;
 	public static final int UP_SELECT_HOVER_HEIGHT = 12;
 	public static final int UP_SELECT_HOVER_TO_X = 143;
 	public static final int UP_SELECT_HOVER_TO_Y = 39;
 	
 	//Up Select Button INFO
 	public static final int INFO_UP_SELECT_WIDTH = 12;
 	public static final int INFO_UP_SELECT_HEIGHT = 12;
 	public static final int INFO_UP_SELECT_TO_X = 143;
 	public static final int INFO_UP_SELECT_TO_Y = 39;
	
 	//Down Select Texture
 	public static final int DOWN_SELECT_FROM_X = 176;
 	public static final int DOWN_SELECT_FROM_Y = 48;
 	public static final int DOWN_SELECT_WIDTH = 12;
 	public static final int DOWN_SELECT_HEIGHT = 12;
 	public static final int DOWN_SELECT_TO_X = 143;
 	public static final int DOWN_SELECT_TO_Y = 67;
	
 	//Down Select Mouse Over Texture
 	public static final int DOWN_SELECT_HOVER_FROM_X = 188;
 	public static final int DOWN_SELECT_HOVER_FROM_Y = 48;
 	public static final int DOWN_SELECT_HOVER_WIDTH = 12;
 	public static final int DOWN_SELECT_HOVER_HEIGHT = 12;
 	public static final int DOWN_SELECT_HOVER_TO_X = 143;
 	public static final int DOWN_SELECT_HOVER_TO_Y = 67;
 	
 	//Down Select Button INFO
 	public static final int INFO_DOWN_SELECT_WIDTH = 12;
 	public static final int INFO_DOWN_SELECT_HEIGHT = 12;
 	public static final int INFO_DOWN_SELECT_TO_X = 143;
 	public static final int INFO_DOWN_SELECT_TO_Y = 67;
	
 	//North Select Texture
 	public static final int NORTH_SELECT_FROM_X = 176;
 	public static final int NORTH_SELECT_FROM_Y = 24;
 	public static final int NORTH_SELECT_WIDTH = 12;
 	public static final int NORTH_SELECT_HEIGHT = 12;
 	public static final int NORTH_SELECT_TO_X = 143;
 	public static final int NORTH_SELECT_TO_Y = 53;
	
 	//North Select Mouse Over Texture
 	public static final int NORTH_SELECT_HOVER_FROM_X = 188;
 	public static final int NORTH_SELECT_HOVER_FROM_Y = 24;
 	public static final int NORTH_SELECT_HOVER_WIDTH = 12;
 	public static final int NORTH_SELECT_HOVER_HEIGHT = 12;
 	public static final int NORTH_SELECT_HOVER_TO_X = 143;
 	public static final int NORTH_SELECT_HOVER_TO_Y = 53;
 	
 	//North Select Button INFO
 	public static final int INFO_NORTH_SELECT_WIDTH = 12;
 	public static final int INFO_NORTH_SELECT_HEIGHT = 12;
 	public static final int INFO_NORTH_SELECT_TO_X = 143;
 	public static final int INFO_NORTH_SELECT_TO_Y = 53;
	
 	//South Select Texture
 	public static final int SOUTH_SELECT_FROM_X = 176;
 	public static final int SOUTH_SELECT_FROM_Y = 60;
 	public static final int SOUTH_SELECT_WIDTH = 12;
 	public static final int SOUTH_SELECT_HEIGHT = 12;
 	public static final int SOUTH_SELECT_TO_X = 157;
 	public static final int SOUTH_SELECT_TO_Y = 67;
	
 	//South Select Mouse Over Texture
 	public static final int SOUTH_SELECT_HOVER_FROM_X = 188;
 	public static final int SOUTH_SELECT_HOVER_FROM_Y = 60;
 	public static final int SOUTH_SELECT_HOVER_WIDTH = 12;
 	public static final int SOUTH_SELECT_HOVER_HEIGHT = 12;
 	public static final int SOUTH_SELECT_HOVER_TO_X = 157;
 	public static final int SOUTH_SELECT_HOVER_TO_Y = 67;
 	
 	//South Select Button INFO
 	public static final int INFO_SOUTH_SELECT_WIDTH = 12;
 	public static final int INFO_SOUTH_SELECT_HEIGHT = 12;
 	public static final int INFO_SOUTH_SELECT_TO_X = 157;
 	public static final int INFO_SOUTH_SELECT_TO_Y = 67;
	
 	//East Select Texture
 	public static final int EAST_SELECT_FROM_X = 176;
 	public static final int EAST_SELECT_FROM_Y = 36;
 	public static final int EAST_SELECT_WIDTH = 12;
 	public static final int EAST_SELECT_HEIGHT = 12;
 	public static final int EAST_SELECT_TO_X = 157;
 	public static final int EAST_SELECT_TO_Y = 53;
	
 	//East Select Mouse Over Texture
 	public static final int EAST_SELECT_HOVER_FROM_X = 188;
 	public static final int EAST_SELECT_HOVER_FROM_Y = 36;
 	public static final int EAST_SELECT_HOVER_WIDTH = 12;
 	public static final int EAST_SELECT_HOVER_HEIGHT = 12;
 	public static final int EAST_SELECT_HOVER_TO_X = 157;
 	public static final int EAST_SELECT_HOVER_TO_Y = 53;
 	
 	//East Select Button INFO
 	public static final int INFO_EAST_SELECT_WIDTH = 12;
 	public static final int INFO_EAST_SELECT_HEIGHT = 12;
 	public static final int INFO_EAST_SELECT_TO_X = 157;
 	public static final int INFO_EAST_SELECT_TO_Y = 53;
	
 	//West Select Texture
 	public static final int WEST_SELECT_FROM_X = 176;
 	public static final int WEST_SELECT_FROM_Y = 12;
 	public static final int WEST_SELECT_WIDTH = 12;
 	public static final int WEST_SELECT_HEIGHT = 12;
 	public static final int WEST_SELECT_TO_X = 129;
 	public static final int WEST_SELECT_TO_Y = 53;
	
 	//West Select Mouse Over Texture
 	public static final int WEST_SELECT_HOVER_FROM_X = 188;
 	public static final int WEST_SELECT_HOVER_FROM_Y = 12;
 	public static final int WEST_SELECT_HOVER_WIDTH = 12;
 	public static final int WEST_SELECT_HOVER_HEIGHT = 12;
 	public static final int WEST_SELECT_HOVER_TO_X = 129;
 	public static final int WEST_SELECT_HOVER_TO_Y = 53;
 	
 	//West Select Button INFO
 	public static final int INFO_WEST_SELECT_WIDTH = 12;
 	public static final int INFO_WEST_SELECT_HEIGHT = 12;
 	public static final int INFO_WEST_SELECT_TO_X = 129;
 	public static final int INFO_WEST_SELECT_TO_Y = 53;
 	
 	//Button IDS
 	public static final int REDSTONE_MODE_ID = 0;
 	public static final int COMPARATOR_MODE_ID = 1;
 	public static final int EXTRACTOR_TOGGLE_ID = 2;
 	
 	public static final int PRIORITY_INCREASE_ID = 3;
 	public static final int PRIORITY_DECREASE_ID = 4;

 	public static final int TRANSFER_INCREASE_ID = 5;
 	public static final int TRANSFER_DECREASE_ID = 6;
 	
 	public BlockPos pos;
    
 	//SKIP
    public ScreenT1EnergyExtractor(ContainerT1EnergyExtractor container, PlayerInventory playerInventory, ITextComponent title) {
        
    	super(container, playerInventory, title);
    	pos = container.getBlockPos();
    }
    
    @Override
    protected void renderLabels(MatrixStack stack, int x, int y) {
    	
        this.font.draw(stack, this.title, (float)this.titleLabelX + 32.0F, (float)this.titleLabelY, 4210752);
        this.font.draw(stack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY + 3.0F, 4210752);
        
        if(sideSelect == 0) {
        	//TODO May remove this as it may make interacting with gui confusing
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.selectside"), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.selectside"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 1) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferUp()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPriorityUp()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 2) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferDown()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPriorityDown()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 3) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferNorth()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPriorityNorth()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 4) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferSouth()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPrioritySouth()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 5) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferEast()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPriorityEast()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
        
        if(sideSelect == 6) {
        
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.transfer"), (float)this.titleLabelX + 32.0F, (float)this.titleLabelY + 7F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getTransferWest()), (float)this.titleLabelX + 52.0F, (float)this.titleLabelY + 16F, 4210752);
        	this.font.draw(stack, new TranslationTextComponent("com.github.will11690.mechanicraft.screen.energy_extractor.priority"), (float)this.titleLabelX + 18.0F, (float)this.titleLabelY + 45.0F, 4210752);
        	this.font.draw(stack, String.valueOf(this.menu.getPriorityWest()), (float)this.titleLabelX + 30.0F, (float)this.titleLabelY + 53.0F, 4210752);
        }
     }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
    	
        this.renderBackground(matrixStack);
        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);
        
        if(Screen.hasShiftDown()) {
        	
        	//Draw Energy ToolTip Full Number
        	if (x > (getGuiLeft() + INFO_ENERGY_BAR_TO_X) && x < (getGuiLeft() + INFO_ENERGY_BAR_TO_X) + INFO_ENERGY_BAR_WIDTH && y > (getGuiTop() + INFO_ENERGY_BAR_TO_Y) && y < (getGuiTop() + INFO_ENERGY_BAR_TO_Y) + INFO_ENERGY_BAR_HEIGHT) {
            	
                this.renderTooltip(matrixStack, LanguageMap.getInstance().getVisualOrder(Arrays.asList(
                new TranslationTextComponent("com.github.will11690.mechanicraft.screen.t1_energy_extractor.energy", this.menu.getEnergyStored(), this.menu.getEnergyCapacity()))), x, y);
        	}
        	
        } else {
        	
        	//Draw Energy ToolTip
        	if (x > (getGuiLeft() + INFO_ENERGY_BAR_TO_X) && x < (getGuiLeft() + INFO_ENERGY_BAR_TO_X) + INFO_ENERGY_BAR_WIDTH && y > (getGuiTop() + INFO_ENERGY_BAR_TO_Y) && y < (getGuiTop() + INFO_ENERGY_BAR_TO_Y) + INFO_ENERGY_BAR_HEIGHT) {
        	
        		this.renderTooltip(matrixStack, LanguageMap.getInstance().getVisualOrder(Arrays.asList(
        		new TranslationTextComponent("com.github.will11690.mechanicraft.screen.t1_energy_extractor.energy", Utils.withSuffix(this.menu.getEnergyStored()), Utils.withSuffix(this.menu.getEnergyCapacity())),
        		new TranslationTextComponent("com.github.will11690.mechanicraft.screen.gui_details"))), x, y);
        	}
        }
    }
    
    public void redstoneMode() {
    		
    	redstoneMode = redstoneMode + 1;
    		
    	if(redstoneMode > 2) {
    			
    		redstoneMode = 0;
    	}
    }
    
    public void comparatorMode() {
    		
    	comparatorMode = comparatorMode + 1;
    		
    	if(comparatorMode > 2) {
    			
    		comparatorMode = 0;
    	}
    }
    
    public void extractorMode() {
    		
    	extractorMode = extractorMode + 1;
    		
    	if(extractorMode > 1) {
    			
    		extractorMode = 0;
    	}
    	
    	if(extractorMode != this.menu.getExtractorMode(sideSelect)) {
    		
    		extractorMode = this.menu.getExtractorMode(sideSelect);
    	}
    }
    
    private int getPriority(int sideSelect) {
    	
    	int sidePriority;
    	
    	if(sideSelect == 0) {
    		
    		return 0;
    	}
    	
    	if(sideSelect == 1) {
    		
    		sidePriority = this.menu.getPriorityUp();
    		return sidePriority;
    	}
    	
    	if(sideSelect == 2) {
    		
    		sidePriority = this.menu.getPriorityDown();
    		return sidePriority;
    	}
    	
    	if(sideSelect == 3) {
    		
    		sidePriority = this.menu.getPriorityNorth();
    		return sidePriority;
    	}
    	
    	if(sideSelect == 4) {
    		
    		sidePriority = this.menu.getPrioritySouth();
    		return sidePriority;
    	}
    	
    	if(sideSelect == 5) {
    		
    		sidePriority = this.menu.getPriorityEast();
    		return sidePriority;
    	}
    	
    	if(sideSelect == 6) {
    		
    		sidePriority = this.menu.getPriorityWest();
    		return sidePriority;
    	}
    	
    	else return 0;
    }
    
    private int getTransfer(int sideSelect) {
    	
    	int sideTransfer;
    	
    	if(sideSelect == 0) {
    		
    		return 0;
    	}
    	
    	if(sideSelect == 1) {
    		
    		sideTransfer = this.menu.getTransferUp();
    		return sideTransfer;
    	}
    	
    	if(sideSelect == 2) {
    		
    		sideTransfer = this.menu.getTransferDown();
    		return sideTransfer;
    	}
    	
    	if(sideSelect == 3) {
    		
    		sideTransfer = this.menu.getTransferNorth();
    		return sideTransfer;
    	}
    	
    	if(sideSelect == 4) {
    		
    		sideTransfer = this.menu.getTransferSouth();
    		return sideTransfer;
    	}
    	
    	if(sideSelect == 5) {
    		
    		sideTransfer = this.menu.getTransferEast();
    		return sideTransfer;
    	}
    	
    	if(sideSelect == 6) {
    		
    		sideTransfer = this.menu.getTransferWest();
    		return sideTransfer;
    	}
    	
    	else return 0;
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
    	
        if (minecraft == null) {
        	
            return;
        }
        
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE);

        int posX = (this.width - this.imageWidth) / 2;
        int posY = (this.height - this.imageHeight) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.imageWidth, this.imageHeight);
        
        if(redstoneMode == 0) {
        
        	this.blit(matrixStack, posX + REDSTONE_DEFAULT_TO_X, posY + REDSTONE_DEFAULT_TO_Y, REDSTONE_DEFAULT_FROM_X, REDSTONE_DEFAULT_FROM_Y, REDSTONE_DEFAULT_HEIGHT, REDSTONE_DEFAULT_WIDTH);
        
        	if(x > (getGuiLeft() + REDSTONE_DEFAULT_HOVER_TO_X) && x < (getGuiLeft() + REDSTONE_DEFAULT_HOVER_TO_X) + REDSTONE_DEFAULT_HOVER_WIDTH && y > (getGuiTop() + REDSTONE_DEFAULT_HOVER_TO_Y) && y < (getGuiTop() + REDSTONE_DEFAULT_HOVER_TO_Y) + REDSTONE_DEFAULT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + REDSTONE_DEFAULT_HOVER_TO_X, posY + REDSTONE_DEFAULT_HOVER_TO_Y, REDSTONE_DEFAULT_HOVER_FROM_X, REDSTONE_DEFAULT_HOVER_FROM_Y, REDSTONE_DEFAULT_HOVER_HEIGHT, REDSTONE_DEFAULT_HOVER_WIDTH);
        	}
        }
        
        if(redstoneMode == 1) {
        
        	this.blit(matrixStack, posX + REDSTONE_HIGH_TO_X, posY + REDSTONE_HIGH_TO_Y, REDSTONE_HIGH_FROM_X, REDSTONE_HIGH_FROM_Y, REDSTONE_HIGH_HEIGHT, REDSTONE_HIGH_WIDTH);
        
        	if(x > (getGuiLeft() + REDSTONE_HIGH_HOVER_TO_X) && x < (getGuiLeft() + REDSTONE_HIGH_HOVER_TO_X) + REDSTONE_HIGH_HOVER_WIDTH && y > (getGuiTop() + REDSTONE_HIGH_HOVER_TO_Y) && y < (getGuiTop() + REDSTONE_HIGH_HOVER_TO_Y) + REDSTONE_HIGH_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + REDSTONE_HIGH_HOVER_TO_X, posY + REDSTONE_HIGH_HOVER_TO_Y, REDSTONE_HIGH_HOVER_FROM_X, REDSTONE_HIGH_HOVER_FROM_Y, REDSTONE_HIGH_HOVER_HEIGHT, REDSTONE_HIGH_HOVER_WIDTH);
        	}
        }
        
        if(redstoneMode == 2) {
        
        	this.blit(matrixStack, posX + REDSTONE_LOW_TO_X, posY + REDSTONE_LOW_TO_Y, REDSTONE_LOW_FROM_X, REDSTONE_LOW_FROM_Y, REDSTONE_LOW_HEIGHT, REDSTONE_LOW_WIDTH);
        
        	if(x > (getGuiLeft() + REDSTONE_LOW_HOVER_TO_X) && x < (getGuiLeft() + REDSTONE_LOW_HOVER_TO_X) + REDSTONE_LOW_HOVER_WIDTH && y > (getGuiTop() + REDSTONE_LOW_HOVER_TO_Y) && y < (getGuiTop() + REDSTONE_LOW_HOVER_TO_Y) + REDSTONE_LOW_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + REDSTONE_LOW_HOVER_TO_X, posY + REDSTONE_LOW_HOVER_TO_Y, REDSTONE_LOW_HOVER_FROM_X, REDSTONE_LOW_HOVER_FROM_Y, REDSTONE_LOW_HOVER_HEIGHT, REDSTONE_LOW_HOVER_WIDTH);
        	}
        }
        
        if(comparatorMode == 0) {
        	
        	this.blit(matrixStack, posX + COMPARATOR_DEFAULT_TO_X, posY + COMPARATOR_DEFAULT_TO_Y, COMPARATOR_DEFAULT_FROM_X, COMPARATOR_DEFAULT_FROM_Y, COMPARATOR_DEFAULT_HEIGHT, COMPARATOR_DEFAULT_WIDTH);
       
        	if(x > (getGuiLeft() + COMPARATOR_DEFAULT_HOVER_TO_X) && x < (getGuiLeft() + COMPARATOR_DEFAULT_HOVER_TO_X) + COMPARATOR_DEFAULT_HOVER_WIDTH && y > (getGuiTop() + COMPARATOR_DEFAULT_HOVER_TO_Y) && y < (getGuiTop() + COMPARATOR_DEFAULT_HOVER_TO_Y) + COMPARATOR_DEFAULT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + COMPARATOR_DEFAULT_HOVER_TO_X, posY + COMPARATOR_DEFAULT_HOVER_TO_Y, COMPARATOR_DEFAULT_HOVER_FROM_X, COMPARATOR_DEFAULT_HOVER_FROM_Y, COMPARATOR_DEFAULT_HOVER_HEIGHT, COMPARATOR_DEFAULT_HOVER_WIDTH);
        	}
        }
        
        if(comparatorMode == 1) {
        	
        	this.blit(matrixStack, posX + COMPARATOR_HIGH_TO_X, posY + COMPARATOR_HIGH_TO_Y, COMPARATOR_HIGH_FROM_X, COMPARATOR_HIGH_FROM_Y, COMPARATOR_HIGH_HEIGHT, COMPARATOR_HIGH_WIDTH);
       
        	if(x > (getGuiLeft() + COMPARATOR_HIGH_HOVER_TO_X) && x < (getGuiLeft() + COMPARATOR_HIGH_HOVER_TO_X) + COMPARATOR_HIGH_HOVER_WIDTH && y > (getGuiTop() + COMPARATOR_HIGH_HOVER_TO_Y) && y < (getGuiTop() + COMPARATOR_HIGH_HOVER_TO_Y) + COMPARATOR_HIGH_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + COMPARATOR_HIGH_HOVER_TO_X, posY + COMPARATOR_HIGH_HOVER_TO_Y, COMPARATOR_HIGH_HOVER_FROM_X, COMPARATOR_HIGH_HOVER_FROM_Y, COMPARATOR_HIGH_HOVER_HEIGHT, COMPARATOR_HIGH_HOVER_WIDTH);
        	}
        }
        
        if(comparatorMode == 2) {
        	
        	this.blit(matrixStack, posX + COMPARATOR_LOW_TO_X, posY + COMPARATOR_LOW_TO_Y, COMPARATOR_LOW_FROM_X, COMPARATOR_LOW_FROM_Y, COMPARATOR_LOW_HEIGHT, COMPARATOR_LOW_WIDTH);
       
        	if(x > (getGuiLeft() + COMPARATOR_LOW_HOVER_TO_X) && x < (getGuiLeft() + COMPARATOR_LOW_HOVER_TO_X) + COMPARATOR_LOW_HOVER_WIDTH && y > (getGuiTop() + COMPARATOR_LOW_HOVER_TO_Y) && y < (getGuiTop() + COMPARATOR_LOW_HOVER_TO_Y) + COMPARATOR_LOW_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + COMPARATOR_LOW_HOVER_TO_X, posY + COMPARATOR_LOW_HOVER_TO_Y, COMPARATOR_LOW_HOVER_FROM_X, COMPARATOR_LOW_HOVER_FROM_Y, COMPARATOR_LOW_HOVER_HEIGHT, COMPARATOR_LOW_HOVER_WIDTH);
        	}
        }
        
        if(extractorMode == 0 && sideSelect != 0) {
        	
        this.blit(matrixStack, posX + EXTRACTOR_TOGGLE_TO_X, posY + EXTRACTOR_TOGGLE_TO_Y, EXTRACTOR_TOGGLE_FROM_X, EXTRACTOR_TOGGLE_FROM_Y, EXTRACTOR_TOGGLE_HEIGHT, EXTRACTOR_TOGGLE_WIDTH);
       
        	if(x > (getGuiLeft() + EXTRACTOR_TOGGLE_HOVER_TO_X) && x < (getGuiLeft() + EXTRACTOR_TOGGLE_HOVER_TO_X) + EXTRACTOR_TOGGLE_HOVER_WIDTH && y > (getGuiTop() + EXTRACTOR_TOGGLE_HOVER_TO_Y) && y < (getGuiTop() + EXTRACTOR_TOGGLE_HOVER_TO_Y) + EXTRACTOR_TOGGLE_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + EXTRACTOR_TOGGLE_HOVER_TO_X, posY + EXTRACTOR_TOGGLE_HOVER_TO_Y, EXTRACTOR_TOGGLE_HOVER_FROM_X, EXTRACTOR_TOGGLE_HOVER_FROM_Y, EXTRACTOR_TOGGLE_HOVER_HEIGHT, EXTRACTOR_TOGGLE_HOVER_WIDTH);
        	}
        }
        
        if(extractorMode == 1 && sideSelect != 0) {
        	
        this.blit(matrixStack, posX + EXTRACTOR_TOGGLE_OFF_TO_X, posY + EXTRACTOR_TOGGLE_OFF_TO_Y, EXTRACTOR_TOGGLE_OFF_FROM_X, EXTRACTOR_TOGGLE_OFF_FROM_Y, EXTRACTOR_TOGGLE_OFF_HEIGHT, EXTRACTOR_TOGGLE_OFF_WIDTH);
       
        	if(x > (getGuiLeft() + EXTRACTOR_TOGGLE_OFF_HOVER_TO_X) && x < (getGuiLeft() + EXTRACTOR_TOGGLE_OFF_HOVER_TO_X) + EXTRACTOR_TOGGLE_OFF_HOVER_WIDTH && y > (getGuiTop() + EXTRACTOR_TOGGLE_OFF_HOVER_TO_Y) && y < (getGuiTop() + EXTRACTOR_TOGGLE_OFF_HOVER_TO_Y) + EXTRACTOR_TOGGLE_OFF_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + EXTRACTOR_TOGGLE_OFF_HOVER_TO_X, posY + EXTRACTOR_TOGGLE_OFF_HOVER_TO_Y, EXTRACTOR_TOGGLE_OFF_HOVER_FROM_X, EXTRACTOR_TOGGLE_OFF_HOVER_FROM_Y, EXTRACTOR_TOGGLE_OFF_HOVER_HEIGHT, EXTRACTOR_TOGGLE_OFF_HOVER_WIDTH);
        	}
        }
        
        //SIDE SELECTION SECTION
        
        if(sideSelect != 1) {
        	
        	this.blit(matrixStack, posX + UP_SELECT_TO_X, posY + UP_SELECT_TO_Y, UP_SELECT_FROM_X, UP_SELECT_FROM_Y, UP_SELECT_HEIGHT, UP_SELECT_WIDTH);
       
        	if(x > (getGuiLeft() + UP_SELECT_HOVER_TO_X) && x < (getGuiLeft() + UP_SELECT_HOVER_TO_X) + UP_SELECT_HOVER_WIDTH && y > (getGuiTop() + UP_SELECT_HOVER_TO_Y) && y < (getGuiTop() + UP_SELECT_HOVER_TO_Y) + UP_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + UP_SELECT_HOVER_TO_X, posY + UP_SELECT_HOVER_TO_Y, UP_SELECT_HOVER_FROM_X, UP_SELECT_HOVER_FROM_Y, UP_SELECT_HOVER_HEIGHT, UP_SELECT_HOVER_WIDTH);
        	}
    	}
        
        if(sideSelect != 2) {
        	this.blit(matrixStack, posX + DOWN_SELECT_TO_X, posY + DOWN_SELECT_TO_Y, DOWN_SELECT_FROM_X, DOWN_SELECT_FROM_Y, DOWN_SELECT_HEIGHT, DOWN_SELECT_WIDTH);
        
        	if(x > (getGuiLeft() + DOWN_SELECT_HOVER_TO_X) && x < (getGuiLeft() + DOWN_SELECT_HOVER_TO_X) + DOWN_SELECT_HOVER_WIDTH && y > (getGuiTop() + DOWN_SELECT_HOVER_TO_Y) && y < (getGuiTop() + DOWN_SELECT_HOVER_TO_Y) + DOWN_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + DOWN_SELECT_HOVER_TO_X, posY + DOWN_SELECT_HOVER_TO_Y, DOWN_SELECT_HOVER_FROM_X, DOWN_SELECT_HOVER_FROM_Y, DOWN_SELECT_HOVER_HEIGHT, DOWN_SELECT_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 3) {
        	
        	this.blit(matrixStack, posX + NORTH_SELECT_TO_X, posY + NORTH_SELECT_TO_Y, NORTH_SELECT_FROM_X, NORTH_SELECT_FROM_Y, NORTH_SELECT_HEIGHT, NORTH_SELECT_WIDTH);
        
        	if(x > (getGuiLeft() + NORTH_SELECT_HOVER_TO_X) && x < (getGuiLeft() + NORTH_SELECT_HOVER_TO_X) + NORTH_SELECT_HOVER_WIDTH && y > (getGuiTop() + NORTH_SELECT_HOVER_TO_Y) && y < (getGuiTop() + NORTH_SELECT_HOVER_TO_Y) + NORTH_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + NORTH_SELECT_HOVER_TO_X, posY + NORTH_SELECT_HOVER_TO_Y, NORTH_SELECT_HOVER_FROM_X, NORTH_SELECT_HOVER_FROM_Y, NORTH_SELECT_HOVER_HEIGHT, NORTH_SELECT_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 4) {
        
        	this.blit(matrixStack, posX + SOUTH_SELECT_TO_X, posY + SOUTH_SELECT_TO_Y, SOUTH_SELECT_FROM_X, SOUTH_SELECT_FROM_Y, SOUTH_SELECT_HEIGHT, SOUTH_SELECT_WIDTH);
        
        	if(x > (getGuiLeft() + SOUTH_SELECT_HOVER_TO_X) && x < (getGuiLeft() + SOUTH_SELECT_HOVER_TO_X) + SOUTH_SELECT_HOVER_WIDTH && y > (getGuiTop() + SOUTH_SELECT_HOVER_TO_Y) && y < (getGuiTop() + SOUTH_SELECT_HOVER_TO_Y) + SOUTH_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + SOUTH_SELECT_HOVER_TO_X, posY + SOUTH_SELECT_HOVER_TO_Y, SOUTH_SELECT_HOVER_FROM_X, SOUTH_SELECT_HOVER_FROM_Y, SOUTH_SELECT_HOVER_HEIGHT, SOUTH_SELECT_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 5) {
        	
        	this.blit(matrixStack, posX + EAST_SELECT_TO_X, posY + EAST_SELECT_TO_Y, EAST_SELECT_FROM_X, EAST_SELECT_FROM_Y, EAST_SELECT_HEIGHT, EAST_SELECT_WIDTH);
        
        	if(x > (getGuiLeft() + EAST_SELECT_HOVER_TO_X) && x < (getGuiLeft() + EAST_SELECT_HOVER_TO_X) + EAST_SELECT_HOVER_WIDTH && y > (getGuiTop() + EAST_SELECT_HOVER_TO_Y) && y < (getGuiTop() + EAST_SELECT_HOVER_TO_Y) + EAST_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + EAST_SELECT_HOVER_TO_X, posY + EAST_SELECT_HOVER_TO_Y, EAST_SELECT_HOVER_FROM_X, EAST_SELECT_HOVER_FROM_Y, EAST_SELECT_HOVER_HEIGHT, EAST_SELECT_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 6) {
        
        	this.blit(matrixStack, posX + WEST_SELECT_TO_X, posY + WEST_SELECT_TO_Y, WEST_SELECT_FROM_X, WEST_SELECT_FROM_Y, WEST_SELECT_HEIGHT, WEST_SELECT_WIDTH);
        
        	if(x > (getGuiLeft() + WEST_SELECT_HOVER_TO_X) && x < (getGuiLeft() + WEST_SELECT_HOVER_TO_X) + WEST_SELECT_HOVER_WIDTH && y > (getGuiTop() + WEST_SELECT_HOVER_TO_Y) && y < (getGuiTop() + WEST_SELECT_HOVER_TO_Y) + WEST_SELECT_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + WEST_SELECT_HOVER_TO_X, posY + WEST_SELECT_HOVER_TO_Y, WEST_SELECT_HOVER_FROM_X, WEST_SELECT_HOVER_FROM_Y, WEST_SELECT_HOVER_HEIGHT, WEST_SELECT_HOVER_WIDTH);
        	}
        }
        
        //TRANSFER AND PRIORITY SECTION
        if(sideSelect != 0) {
        	
        	this.blit(matrixStack, posX + PRIORITY_INCREASE_TO_X, posY + PRIORITY_INCREASE_TO_Y, PRIORITY_INCREASE_FROM_X, PRIORITY_INCREASE_FROM_Y, PRIORITY_INCREASE_HEIGHT, PRIORITY_INCREASE_WIDTH);
        
        	if(x > (getGuiLeft() + PRIORITY_INCREASE_HOVER_TO_X) && x < (getGuiLeft() + PRIORITY_INCREASE_HOVER_TO_X) + PRIORITY_INCREASE_HOVER_WIDTH && y > (getGuiTop() + PRIORITY_INCREASE_HOVER_TO_Y) && y < (getGuiTop() + PRIORITY_INCREASE_HOVER_TO_Y) + PRIORITY_INCREASE_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + PRIORITY_INCREASE_HOVER_TO_X, posY + PRIORITY_INCREASE_HOVER_TO_Y, PRIORITY_INCREASE_HOVER_FROM_X, PRIORITY_INCREASE_HOVER_FROM_Y, PRIORITY_INCREASE_HOVER_HEIGHT, PRIORITY_INCREASE_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 0) {
        	
        	this.blit(matrixStack, posX + PRIORITY_DECREASE_TO_X, posY + PRIORITY_DECREASE_TO_Y, PRIORITY_DECREASE_FROM_X, PRIORITY_DECREASE_FROM_Y, PRIORITY_DECREASE_HEIGHT, PRIORITY_DECREASE_WIDTH);
        
        	if(x > (getGuiLeft() + PRIORITY_DECREASE_HOVER_TO_X) && x < (getGuiLeft() + PRIORITY_DECREASE_HOVER_TO_X) + PRIORITY_DECREASE_HOVER_WIDTH && y > (getGuiTop() + PRIORITY_DECREASE_HOVER_TO_Y) && y < (getGuiTop() + PRIORITY_DECREASE_HOVER_TO_Y) + PRIORITY_DECREASE_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + PRIORITY_DECREASE_HOVER_TO_X, posY + PRIORITY_DECREASE_HOVER_TO_Y, PRIORITY_DECREASE_HOVER_FROM_X, PRIORITY_DECREASE_HOVER_FROM_Y, PRIORITY_DECREASE_HOVER_HEIGHT, PRIORITY_DECREASE_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 0) {
        	
        	this.blit(matrixStack, posX + TRANSFER_INCREASE_TO_X, posY + TRANSFER_INCREASE_TO_Y, TRANSFER_INCREASE_FROM_X, TRANSFER_INCREASE_FROM_Y, TRANSFER_INCREASE_HEIGHT, TRANSFER_INCREASE_WIDTH);
        
        	if(x > (getGuiLeft() + TRANSFER_INCREASE_HOVER_TO_X) && x < (getGuiLeft() + TRANSFER_INCREASE_HOVER_TO_X) + TRANSFER_INCREASE_HOVER_WIDTH && y > (getGuiTop() + TRANSFER_INCREASE_HOVER_TO_Y) && y < (getGuiTop() + TRANSFER_INCREASE_HOVER_TO_Y) + TRANSFER_INCREASE_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + TRANSFER_INCREASE_HOVER_TO_X, posY + TRANSFER_INCREASE_HOVER_TO_Y, TRANSFER_INCREASE_HOVER_FROM_X, TRANSFER_INCREASE_HOVER_FROM_Y, TRANSFER_INCREASE_HOVER_HEIGHT, TRANSFER_INCREASE_HOVER_WIDTH);
        	}
        }
        
        if(sideSelect != 0) {
        	
        	this.blit(matrixStack, posX + TRANSFER_DECREASE_TO_X, posY + TRANSFER_DECREASE_TO_Y, TRANSFER_DECREASE_FROM_X, TRANSFER_DECREASE_FROM_Y, TRANSFER_DECREASE_HEIGHT, TRANSFER_DECREASE_WIDTH);
        
        	if(x > (getGuiLeft() + TRANSFER_DECREASE_HOVER_TO_X) && x < (getGuiLeft() + TRANSFER_DECREASE_HOVER_TO_X) + TRANSFER_DECREASE_HOVER_WIDTH && y > (getGuiTop() + TRANSFER_DECREASE_HOVER_TO_Y) && y < (getGuiTop() + TRANSFER_DECREASE_HOVER_TO_Y) + TRANSFER_DECREASE_HOVER_HEIGHT) {
        	
        		this.blit(matrixStack, posX + TRANSFER_DECREASE_HOVER_TO_X, posY + TRANSFER_DECREASE_HOVER_TO_Y, TRANSFER_DECREASE_HOVER_FROM_X, TRANSFER_DECREASE_HOVER_FROM_Y, TRANSFER_DECREASE_HOVER_HEIGHT, TRANSFER_DECREASE_HOVER_WIDTH);
        	}
        }
        
        int maxEnergy = this.menu.getEnergyCapacity(), height = ENERGY_BAR_HEIGHT;
        
        if (this.menu.getEnergyStored() > 0) {
        	
            int remaining = (this.menu.getEnergyStored() * height) / maxEnergy;
            this.blit(matrixStack, posX + ENERGY_BAR_TO_X, posY + ENERGY_BAR_TO_Y - remaining, ENERGY_BAR_FROM_X, /*Pixels from top of gui to bottom of drawn texture*/65 - remaining, ENERGY_BAR_WIDTH, remaining + 1);
        }
    }
    
    @Override
    public boolean mouseClicked(double x, double y, int btn) {
    	
        if(x > (getGuiLeft() + REDSTONE_DEFAULT_HOVER_TO_X) && x < (getGuiLeft() + REDSTONE_DEFAULT_HOVER_TO_X) + REDSTONE_DEFAULT_HOVER_WIDTH && y > (getGuiTop() + REDSTONE_DEFAULT_HOVER_TO_Y) && y < (getGuiTop() + REDSTONE_DEFAULT_HOVER_TO_Y) + REDSTONE_DEFAULT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            redstoneMode();
            ServerboundExtractorRedstoneConfigurePacket updateMessage = new ServerboundExtractorRedstoneConfigurePacket(redstoneMode, pos, this.inventory.player.getUUID());
        	PacketHandler.ENERGY_EXTRACTOR_REDSTONE.sendToServer(updateMessage);
            return true;
        }
        
        if(x > (getGuiLeft() + COMPARATOR_DEFAULT_HOVER_TO_X) && x < (getGuiLeft() + COMPARATOR_DEFAULT_HOVER_TO_X) + COMPARATOR_DEFAULT_HOVER_WIDTH && y > (getGuiTop() + COMPARATOR_DEFAULT_HOVER_TO_Y) && y < (getGuiTop() + COMPARATOR_DEFAULT_HOVER_TO_Y) + COMPARATOR_DEFAULT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            comparatorMode();
            ServerboundExtractorComparatorConfigurePacket updateMessage = new ServerboundExtractorComparatorConfigurePacket(comparatorMode, pos, this.inventory.player.getUUID());
        	PacketHandler.ENERGY_EXTRACTOR_COMPARATOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect >= 0 && x > (getGuiLeft() + EXTRACTOR_TOGGLE_HOVER_TO_X) && x < (getGuiLeft() + EXTRACTOR_TOGGLE_HOVER_TO_X) + EXTRACTOR_TOGGLE_HOVER_WIDTH && y > (getGuiTop() + EXTRACTOR_TOGGLE_HOVER_TO_Y) && y < (getGuiTop() + EXTRACTOR_TOGGLE_HOVER_TO_Y) + EXTRACTOR_TOGGLE_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            extractorMode();
            ServerboundExtractorSideConfigurePacket updateMessage;
            
            if(sideSelect == 0) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 1) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 2) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 3) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 4) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 5) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else if(sideSelect == 6) {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(sideSelect, extractorMode, pos, this.inventory.player.getUUID());
            	
            } else {
            	
            	updateMessage = new ServerboundExtractorSideConfigurePacket(0, 0, pos, this.inventory.player.getUUID());
            }
            
        	PacketHandler.ENERGY_EXTRACTOR_SIDE_TOGGLE.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 1 && x > (getGuiLeft() + UP_SELECT_HOVER_TO_X) && x < (getGuiLeft() + UP_SELECT_HOVER_TO_X) + UP_SELECT_HOVER_WIDTH && y > (getGuiTop() + UP_SELECT_HOVER_TO_Y) && y < (getGuiTop() + UP_SELECT_HOVER_TO_Y) + UP_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 1;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 2 && x > (getGuiLeft() + DOWN_SELECT_HOVER_TO_X) && x < (getGuiLeft() + DOWN_SELECT_HOVER_TO_X) + DOWN_SELECT_HOVER_WIDTH && y > (getGuiTop() + DOWN_SELECT_HOVER_TO_Y) && y < (getGuiTop() + DOWN_SELECT_HOVER_TO_Y) + DOWN_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 2;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 3 && x > (getGuiLeft() + NORTH_SELECT_HOVER_TO_X) && x < (getGuiLeft() + NORTH_SELECT_HOVER_TO_X) + NORTH_SELECT_HOVER_WIDTH && y > (getGuiTop() + NORTH_SELECT_HOVER_TO_Y) && y < (getGuiTop() + NORTH_SELECT_HOVER_TO_Y) + NORTH_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 3;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 4 && x > (getGuiLeft() + SOUTH_SELECT_HOVER_TO_X) && x < (getGuiLeft() + SOUTH_SELECT_HOVER_TO_X) + SOUTH_SELECT_HOVER_WIDTH && y > (getGuiTop() + SOUTH_SELECT_HOVER_TO_Y) && y < (getGuiTop() + SOUTH_SELECT_HOVER_TO_Y) + SOUTH_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 4;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 5 && x > (getGuiLeft() + EAST_SELECT_HOVER_TO_X) && x < (getGuiLeft() + EAST_SELECT_HOVER_TO_X) + EAST_SELECT_HOVER_WIDTH && y > (getGuiTop() + EAST_SELECT_HOVER_TO_Y) && y < (getGuiTop() + EAST_SELECT_HOVER_TO_Y) + EAST_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 5;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect != 6 && x > (getGuiLeft() + WEST_SELECT_HOVER_TO_X) && x < (getGuiLeft() + WEST_SELECT_HOVER_TO_X) + WEST_SELECT_HOVER_WIDTH && y > (getGuiTop() + WEST_SELECT_HOVER_TO_Y) && y < (getGuiTop() + WEST_SELECT_HOVER_TO_Y) + WEST_SELECT_HOVER_HEIGHT) {
        	
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            sideSelect = 6;
        	//ServerboundExtractorPriorityPacket updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, this.getPriority(sideSelect), pos, this.inventory.player.getUUID());
        	//PacketHandler.T1_ENERGY_EXTRACTOR.sendToServer(updateMessage);
            return true;
        }
        
        if(sideSelect > 0 && x > (getGuiLeft() + PRIORITY_INCREASE_HOVER_TO_X) && x < (getGuiLeft() + PRIORITY_INCREASE_HOVER_TO_X) + PRIORITY_INCREASE_HOVER_WIDTH && y > (getGuiTop() + PRIORITY_INCREASE_HOVER_TO_Y) && y < (getGuiTop() + PRIORITY_INCREASE_HOVER_TO_Y) + PRIORITY_INCREASE_HOVER_HEIGHT) {
        	
        	ServerboundExtractorPriorityPacket updateMessage;
        	
        	if(hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.min(1000, this.getPriority(sideSelect) + 10), pos, this.inventory.player.getUUID());
        		
        	} else if(hasAltDown() && hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.min(1000, this.getPriority(sideSelect) + 100), pos, this.inventory.player.getUUID());
        	} else {
        	
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.min(1000, this.getPriority(sideSelect) + 1), pos, this.inventory.player.getUUID());
        	}
        	
        	PacketHandler.ENERGY_EXTRACTOR_PRIORITY.sendToServer(updateMessage);
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        
        if(sideSelect > 0 && x > (getGuiLeft() + PRIORITY_DECREASE_HOVER_TO_X) && x < (getGuiLeft() + PRIORITY_DECREASE_HOVER_TO_X) + PRIORITY_DECREASE_HOVER_WIDTH && y > (getGuiTop() + PRIORITY_DECREASE_HOVER_TO_Y) && y < (getGuiTop() + PRIORITY_DECREASE_HOVER_TO_Y) + PRIORITY_DECREASE_HOVER_HEIGHT) {
        	
        	ServerboundExtractorPriorityPacket updateMessage;
        	
        	if(hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.max(0, this.getPriority(sideSelect) - 10), pos, this.inventory.player.getUUID());
        		
        	} else if(hasAltDown() && hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.max(0, this.getPriority(sideSelect) - 100), pos, this.inventory.player.getUUID());
        	} else {
        	
        		updateMessage = new ServerboundExtractorPriorityPacket(sideSelect, Math.max(0, this.getPriority(sideSelect) - 1), pos, this.inventory.player.getUUID());
        	}
        	
        	PacketHandler.ENERGY_EXTRACTOR_PRIORITY.sendToServer(updateMessage);
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        
        if(sideSelect > 0 && x > (getGuiLeft() + TRANSFER_INCREASE_HOVER_TO_X) && x < (getGuiLeft() + TRANSFER_INCREASE_HOVER_TO_X) + TRANSFER_INCREASE_HOVER_WIDTH && y > (getGuiTop() + TRANSFER_INCREASE_HOVER_TO_Y) && y < (getGuiTop() + TRANSFER_INCREASE_HOVER_TO_Y) + TRANSFER_INCREASE_HOVER_HEIGHT) {
        	
        	ServerboundExtractorTransferPacket updateMessage;
        	
        	if(hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.min(this.menu.getTransferLimit(), this.getTransfer(sideSelect) + 500), pos, this.inventory.player.getUUID());
        		
        	} else if(hasAltDown() && hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.min(this.menu.getTransferLimit(), this.getTransfer(sideSelect) + 1000), pos, this.inventory.player.getUUID());
        		
        	} else {
        	
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.min(this.menu.getTransferLimit(), this.getTransfer(sideSelect) + 100), pos, this.inventory.player.getUUID());
        	}
        	
        	PacketHandler.ENERGY_EXTRACTOR_TRANSFER.sendToServer(updateMessage);
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        
        if(sideSelect > 0 && x > (getGuiLeft() + TRANSFER_DECREASE_HOVER_TO_X) && x < (getGuiLeft() + TRANSFER_DECREASE_HOVER_TO_X) + TRANSFER_DECREASE_HOVER_WIDTH && y > (getGuiTop() + TRANSFER_DECREASE_HOVER_TO_Y) && y < (getGuiTop() + TRANSFER_DECREASE_HOVER_TO_Y) + TRANSFER_DECREASE_HOVER_HEIGHT) {

        	ServerboundExtractorTransferPacket updateMessage;
        	
        	if(hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.max(0, this.getTransfer(sideSelect) - 500), pos, this.inventory.player.getUUID());
        		
        	} else if(hasAltDown() && hasShiftDown()) {
        		
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.max(0, this.getTransfer(sideSelect) - 1000), pos, this.inventory.player.getUUID());
        		
        	} else {
        	
        		updateMessage = new ServerboundExtractorTransferPacket(sideSelect, Math.max(0, this.getTransfer(sideSelect) - 100), pos, this.inventory.player.getUUID());
        	}
        	
        	PacketHandler.ENERGY_EXTRACTOR_TRANSFER.sendToServer(updateMessage);
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        
        return super.mouseClicked(x, y, btn);
    }
}