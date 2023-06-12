package com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;

public class LaserCore extends Block {
	
	
	public LaserCore(Properties properties) {
    	
		super(properties);
        
	}
	
	@Override
	public boolean isPossibleToRespawnInThis() {
		
	      return false;
	}
	
    @Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
    	
        return false; 
    }
    
    @Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
    	
        return false;
    }
    
    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
    	
        return ItemStack.EMPTY;
    }

}
