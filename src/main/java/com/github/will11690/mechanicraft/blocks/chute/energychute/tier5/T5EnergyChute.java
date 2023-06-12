package com.github.will11690.mechanicraft.blocks.chute.energychute.tier5;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

public class T5EnergyChute extends Block {
	
	//BLOCK CONNECTION BOOLS
	static final BooleanProperty BLOCK_UP = BooleanProperty.create("block_up");
	static final BooleanProperty BLOCK_DOWN = BooleanProperty.create("block_down");
	static final BooleanProperty BLOCK_NORTH = BooleanProperty.create("block_north");
	static final BooleanProperty BLOCK_SOUTH = BooleanProperty.create("block_south");
	static final BooleanProperty BLOCK_WEST = BooleanProperty.create("block_west");
	static final BooleanProperty BLOCK_EAST = BooleanProperty.create("block_east");
	
	//CHUTE CONNECTION BOOLS
	static final BooleanProperty CHUTE_UP = BooleanProperty.create("chute_up");
	static final BooleanProperty CHUTE_DOWN = BooleanProperty.create("chute_down");
	static final BooleanProperty CHUTE_NORTH = BooleanProperty.create("chute_north");
	static final BooleanProperty CHUTE_SOUTH = BooleanProperty.create("chute_south");
	static final BooleanProperty CHUTE_WEST = BooleanProperty.create("chute_west");
	static final BooleanProperty CHUTE_EAST = BooleanProperty.create("chute_east");
	
	//BASE SHAPE
	private static final VoxelShape CORE_SHAPE = Block.box(6, 6, 6, 10, 10, 10);
	
	//CHUTE CONNECTION SHAPES
	private static final VoxelShape DOWN_SHAPE_CHUTE = Block.box(6, 0, 6, 10, 6, 10);
	private static final VoxelShape UP_SHAPE_CHUTE = Block.box(6, 10, 6, 10, 16, 10);
	private static final VoxelShape NORTH_SHAPE_CHUTE = Block.box(6, 6, 0, 10, 10, 6);
	private static final VoxelShape SOUTH_SHAPE_CHUTE = Block.box(6, 6, 10, 10, 10, 16);
	private static final VoxelShape EAST_SHAPE_CHUTE = Block.box(10, 6, 6, 16, 10, 10);
	private static final VoxelShape WEST_SHAPE_CHUTE = Block.box(0, 6, 6, 6, 10, 10);
	
	//BLOCK CONNECTION SHAPES
	private static final VoxelShape DOWN_SHAPE_BLOCK = VoxelShapes.join(Block.box(6, 3, 6, 10, 6, 10), Block.box(5, 0, 5, 11, 3, 11), IBooleanFunction.OR);
	private static final VoxelShape UP_SHAPE_BLOCK = VoxelShapes.join(Block.box(6, 10, 6, 10, 13, 10), Block.box(5, 13, 5, 11, 16, 11), IBooleanFunction.OR);
	private static final VoxelShape NORTH_SHAPE_BLOCK = VoxelShapes.join(Block.box(6, 6, 3, 10, 10, 6), Block.box(5, 5, 0, 11, 11, 3), IBooleanFunction.OR);
	private static final VoxelShape SOUTH_SHAPE_BLOCK = VoxelShapes.join(Block.box(6, 6, 10, 10, 10, 13), Block.box(5, 5, 13, 11, 11, 16), IBooleanFunction.OR);
	private static final VoxelShape EAST_SHAPE_BLOCK = VoxelShapes.join(Block.box(10, 6, 6, 13, 10, 10), Block.box(13, 5, 5, 16, 11, 11), IBooleanFunction.OR);
	private static final VoxelShape WEST_SHAPE_BLOCK = VoxelShapes.join(Block.box(3, 6, 6, 6, 10, 10), Block.box(0, 5, 5, 3, 11, 11), IBooleanFunction.OR);
	
	
	public T5EnergyChute(Properties properties) {
    	
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(BLOCK_DOWN, Boolean.valueOf(false)).setValue(BLOCK_EAST, Boolean.valueOf(false)).setValue(BLOCK_NORTH, Boolean.valueOf(false))
				.setValue(BLOCK_SOUTH, Boolean.valueOf(false)).setValue(BLOCK_UP, Boolean.valueOf(false)).setValue(BLOCK_WEST, Boolean.valueOf(false))
				.setValue(CHUTE_DOWN, Boolean.valueOf(false)).setValue(CHUTE_EAST, Boolean.valueOf(false)).setValue(CHUTE_NORTH, Boolean.valueOf(false))
				.setValue(CHUTE_SOUTH, Boolean.valueOf(false)).setValue(CHUTE_UP, Boolean.valueOf(false)).setValue(CHUTE_WEST, Boolean.valueOf(false)));
        
	}
	
	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos currentPos, BlockPos neighbor) {
		
		state = setConnections(world, currentPos, state);
		super.onNeighborChange(state, world, currentPos, neighbor);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
	    	  
		return setConnections(world, currentPos, state);
	}
	
	public static boolean canChuteConnectChute(IBlockReader reader, BlockPos pos, Direction facing) {
		
		BlockPos neighborPos = pos.relative(facing);
		BlockState neighborState = reader.getBlockState(neighborPos);
		Block neighborBlock = neighborState.getBlock();
		
		if(neighborBlock == ModBlocks.T5_ENERGY_CHUTE.get()) {
			
			return true;
		}
		
		else return false;
	}
	
	public static boolean canChuteConnectBlock(IBlockReader reader, BlockPos pos, Direction direction) {
		
		BlockPos neighborPos = pos.relative(direction);
		BlockState neighborState = reader.getBlockState(neighborPos);
		Block neighborBlock = neighborState.getBlock();
		TileEntity neighborEntity = reader.getBlockEntity(neighborPos);
		
		if(neighborBlock.hasTileEntity(neighborState) && neighborEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent() && neighborBlock != ModBlocks.T5_ENERGY_CHUTE.get()) {
			
			return true;
		}
		
		else return false;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		
		return true;
	}

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader reader) {
    	
        return TileEntityHandler.TILE_ENTITY_T5_ENERGY_CHUTE.get().create();
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

		if(canChuteConnectChute(reader, pos, Direction.DOWN)) {
			
			baseShape = VoxelShapes.join(baseShape, DOWN_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.DOWN)) {
			
			baseShape = VoxelShapes.join(baseShape, DOWN_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canChuteConnectChute(reader, pos, Direction.UP)) {
			
			baseShape = VoxelShapes.join(baseShape, UP_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.UP)) {
			
			baseShape = VoxelShapes.join(baseShape, UP_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canChuteConnectChute(reader, pos, Direction.NORTH)) {
			
			baseShape = VoxelShapes.join(baseShape, NORTH_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.NORTH)) {
			
			baseShape = VoxelShapes.join(baseShape, NORTH_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canChuteConnectChute(reader, pos, Direction.SOUTH)) {
			
			baseShape = VoxelShapes.join(baseShape, SOUTH_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.SOUTH)) {
			
			baseShape = VoxelShapes.join(baseShape, SOUTH_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canChuteConnectChute(reader, pos, Direction.WEST)) {
			
			baseShape = VoxelShapes.join(baseShape, WEST_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.WEST)) {
			
			baseShape = VoxelShapes.join(baseShape, WEST_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canChuteConnectChute(reader, pos, Direction.EAST)) {
			
			baseShape = VoxelShapes.join(baseShape, EAST_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canChuteConnectBlock(reader, pos, Direction.EAST)) {
			
			baseShape = VoxelShapes.join(baseShape, EAST_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
        return baseShape;
    }

    private BlockState setConnections(IBlockReader reader, BlockPos pos, BlockState state) {
    	
        return state.setValue(BLOCK_UP, canChuteConnectBlock(reader, pos, Direction.UP)).setValue(BLOCK_DOWN, canChuteConnectBlock(reader, pos, Direction.DOWN))
        			.setValue(BLOCK_NORTH, canChuteConnectBlock(reader, pos, Direction.NORTH)).setValue(BLOCK_SOUTH, canChuteConnectBlock(reader, pos, Direction.SOUTH))
                	.setValue(BLOCK_EAST, canChuteConnectBlock(reader, pos, Direction.EAST)).setValue(BLOCK_WEST, canChuteConnectBlock(reader, pos, Direction.WEST))
                	.setValue(CHUTE_UP, canChuteConnectChute(reader, pos, Direction.UP)).setValue(CHUTE_DOWN, canChuteConnectChute(reader, pos, Direction.DOWN))
        			.setValue(CHUTE_NORTH, canChuteConnectChute(reader, pos, Direction.NORTH)).setValue(CHUTE_SOUTH, canChuteConnectChute(reader, pos, Direction.SOUTH))
                	.setValue(CHUTE_EAST, canChuteConnectChute(reader, pos, Direction.EAST)).setValue(CHUTE_WEST, canChuteConnectChute(reader, pos, Direction.WEST));
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
    	
        builder.add(BLOCK_DOWN, BLOCK_EAST, BLOCK_NORTH, BLOCK_SOUTH, BLOCK_UP, BLOCK_WEST,
        		    CHUTE_DOWN, CHUTE_EAST, CHUTE_NORTH, CHUTE_SOUTH, CHUTE_UP, CHUTE_WEST);
        
    }
}