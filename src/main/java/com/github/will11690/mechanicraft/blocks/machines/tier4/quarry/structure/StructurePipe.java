package com.github.will11690.mechanicraft.blocks.machines.tier4.quarry.structure;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class StructurePipe extends Block {
	
	static final BooleanProperty PIPE_UP = BooleanProperty.create("pipe_up");
	static final BooleanProperty PIPE_DOWN = BooleanProperty.create("pipe_down");
	static final BooleanProperty PIPE_NORTH = BooleanProperty.create("pipe_north");
	static final BooleanProperty PIPE_SOUTH = BooleanProperty.create("pipe_south");
	static final BooleanProperty PIPE_WEST = BooleanProperty.create("pipe_west");
	static final BooleanProperty PIPE_EAST = BooleanProperty.create("pipe_east");
	
	//BASE SHAPE
	private static final VoxelShape CORE_SHAPE = Block.box(6, 6, 6, 10, 10, 10);
	
	//CHUTE CONNECTION SHAPES
	private static final VoxelShape DOWN_SHAPE_PIPE = Block.box(6, 0, 6, 10, 6, 10);
	private static final VoxelShape UP_SHAPE_PIPE = Block.box(6, 10, 6, 10, 16, 10);
	private static final VoxelShape NORTH_SHAPE_PIPE = Block.box(6, 6, 0, 10, 10, 6);
	private static final VoxelShape SOUTH_SHAPE_PIPE = Block.box(6, 6, 10, 10, 10, 16);
	private static final VoxelShape EAST_SHAPE_PIPE = Block.box(10, 6, 6, 16, 10, 10);
	private static final VoxelShape WEST_SHAPE_PIPE = Block.box(0, 6, 6, 6, 10, 10);
	
	
	public StructurePipe(Properties properties) {
    	
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(PIPE_DOWN, Boolean.valueOf(false)).setValue(PIPE_EAST, Boolean.valueOf(false)).setValue(PIPE_NORTH, Boolean.valueOf(false))
				.setValue(PIPE_SOUTH, Boolean.valueOf(false)).setValue(PIPE_UP, Boolean.valueOf(false)).setValue(PIPE_WEST, Boolean.valueOf(false)));
        
	}
	
	@Override
	public void onNeighborChange(BlockState state, IWorldReader reader, BlockPos currentPos, BlockPos neighbor) {
		
		state = setConnections(reader, currentPos, state);
		super.onNeighborChange(state, reader, currentPos, neighbor);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
	    	  
		return setConnections(world, currentPos, state);
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
	
	public static boolean canPipeConnect(IBlockReader reader, BlockPos pos, Direction facing) {
		
		BlockPos neighborPos = pos.relative(facing);
		BlockState neighborState = reader.getBlockState(neighborPos);
		Block neighborBlock = neighborState.getBlock();
		
		if(neighborBlock == ModBlocks.STRUCTURE_PIPE.get() || neighborBlock == ModBlocks.QUARRY.get() || (facing == Direction.DOWN && neighborBlock == ModBlocks.LASER_CORE.get())) {
			
			return true;
		}
		
		else return false;
	}
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctxSupplier) {
    	
    	if(ctxSupplier.getEntity() instanceof LivingEntity || ctxSupplier.getEntity() instanceof FallingBlockEntity) {
    		
			return this.getShape(state, reader, pos, ctxSupplier);
		}
		return VoxelShapes.empty();
	}
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
    	
    	VoxelShape baseShape = CORE_SHAPE;
		
		if(canPipeConnect(reader, pos, Direction.DOWN)) {
			
			baseShape = VoxelShapes.join(baseShape, DOWN_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
		if(canPipeConnect(reader, pos, Direction.UP)) {
			
			baseShape = VoxelShapes.join(baseShape, UP_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
		if(canPipeConnect(reader, pos, Direction.NORTH)) {
			
			baseShape = VoxelShapes.join(baseShape, NORTH_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
		if(canPipeConnect(reader, pos, Direction.SOUTH)) {
			
			baseShape = VoxelShapes.join(baseShape, SOUTH_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
		if(canPipeConnect(reader, pos, Direction.WEST)) {
			
			baseShape = VoxelShapes.join(baseShape, EAST_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
		if(canPipeConnect(reader, pos, Direction.EAST)) {
			
			baseShape = VoxelShapes.join(baseShape, WEST_SHAPE_PIPE, IBooleanFunction.OR);
		}
		
        return baseShape;
    }

    private BlockState setConnections(IBlockReader reader, BlockPos pos, BlockState state) {
    	
        return state.setValue(PIPE_UP, canPipeConnect(reader, pos, Direction.UP)).setValue(PIPE_DOWN, canPipeConnect(reader, pos, Direction.DOWN))
        			.setValue(PIPE_NORTH, canPipeConnect(reader, pos, Direction.NORTH)).setValue(PIPE_SOUTH, canPipeConnect(reader, pos, Direction.SOUTH))
                	.setValue(PIPE_EAST, canPipeConnect(reader, pos, Direction.EAST)).setValue(PIPE_WEST, canPipeConnect(reader, pos, Direction.WEST));
      }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
    	
    	World world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        BlockState blockState = defaultBlockState();
        blockState = this.setConnections(world, blockPos, blockState);
    	
        return blockState;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
    	
        builder.add(PIPE_DOWN, PIPE_EAST, PIPE_NORTH, PIPE_SOUTH, PIPE_UP, PIPE_WEST);
        
    }
}