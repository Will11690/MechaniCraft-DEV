package com.github.will11690.mechanicraft.blocks.machines.tier4.quarry;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class Quarry extends Block {
	
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty MINING = BooleanProperty.create("mining");
    public static final BooleanProperty COMPLETE = BooleanProperty.create("complete");
    public static final BooleanProperty STALLED = BooleanProperty.create("stalled");
    
    boolean breakBlock = false;
	
	public Quarry(Properties properties) {
    	
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MINING, Boolean.valueOf(false)).setValue(COMPLETE, Boolean.valueOf(false)).setValue(STALLED, Boolean.valueOf(false)));
		
	}

    @Override
    public boolean hasTileEntity(BlockState state) {
    	
        return true;
        
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    	
        return TileEntityHandler.TILE_ENTITY_QUARRY.get().create();
        
    }

    /*@Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
    	
        if (world.isClientSide) {
        	
            return ActionResultType.SUCCESS;
            
        }
        
        TileEntity te = world.getBlockEntity(pos);
        if (!(te instanceof TileEntityT1Crusher))
            return ActionResultType.FAIL;

        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, pos);
        return ActionResultType.SUCCESS;
        
    }*/

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
    	
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        
    }
    
    public static void dropItemHandlerContents(final World world, final BlockPos pos, final IItemHandler itemHandler) {
		for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
			ItemStack stack = itemHandler.extractItem(slot, itemHandler.getStackInSlot(slot).getCount(), false);
			InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		}
	}

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
    	
    	 if(!state.is(newState.getBlock())) {
    		 
             TileEntity tileEntity = world.getBlockEntity(pos);
             
             if (tileEntity != null) {
            	 ((TileEntityQuarry) tileEntity).blockBeingBroken(true);
                 LazyOptional<IItemHandler> cap = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                 
                 cap.ifPresent(handler -> {
                	 
                	dropItemHandlerContents(world, pos, handler);
                	((TileEntityQuarry) tileEntity).blockBeingBroken(false);
                     
                 });
             }
             
             super.onRemove(state, world, pos, newState, isMoving);
         }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
    	
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
        
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
    	
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
        
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
    	
        builder.add(MINING, COMPLETE, STALLED, FACING);
        
    }
}