package com.github.will11690.mechanicraft.blocks.chute.energychute.tier1.extractor;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.NetworkHooks;

public class T1EnergyExtractor extends Block {
	
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
	private static final VoxelShape DOWN_SHAPE_BLOCK = VoxelShapes.join(Block.box(4, 0, 4, 12, 4, 12), Block.box(6, 4, 6, 10, 6, 10), IBooleanFunction.OR);
	private static final VoxelShape UP_SHAPE_BLOCK = VoxelShapes.join(Block.box(4, 12, 4, 12, 16, 12), Block.box(6, 10, 6, 10, 12, 10), IBooleanFunction.OR);
	private static final VoxelShape NORTH_SHAPE_BLOCK = VoxelShapes.join(Block.box(4, 4, 0, 12, 12, 4), Block.box(6, 6, 4, 10, 10, 6), IBooleanFunction.OR);
	private static final VoxelShape SOUTH_SHAPE_BLOCK = VoxelShapes.join(Block.box(4, 4, 12, 12, 12, 16), Block.box(6, 6, 10, 10, 10, 12), IBooleanFunction.OR);
	private static final VoxelShape EAST_SHAPE_BLOCK = VoxelShapes.join(Block.box(12, 4, 4, 16, 12, 12), Block.box(10, 6, 6, 12, 10, 10), IBooleanFunction.OR);
	private static final VoxelShape WEST_SHAPE_BLOCK = VoxelShapes.join(Block.box(0, 4, 4, 4, 12, 12), Block.box(4, 6, 6, 6, 10, 10), IBooleanFunction.OR);
	
	
	public T1EnergyExtractor(Properties properties) {
    	
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(BLOCK_DOWN, Boolean.valueOf(false)).setValue(BLOCK_EAST, Boolean.valueOf(false)).setValue(BLOCK_NORTH, Boolean.valueOf(false))
				.setValue(BLOCK_SOUTH, Boolean.valueOf(false)).setValue(BLOCK_UP, Boolean.valueOf(false)).setValue(BLOCK_WEST, Boolean.valueOf(false))
				.setValue(CHUTE_DOWN, Boolean.valueOf(false)).setValue(CHUTE_EAST, Boolean.valueOf(false)).setValue(CHUTE_NORTH, Boolean.valueOf(false))
				.setValue(CHUTE_SOUTH, Boolean.valueOf(false)).setValue(CHUTE_UP, Boolean.valueOf(false)).setValue(CHUTE_WEST, Boolean.valueOf(false)));
        
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
	//TODO Handler neighbor being an inserter
	public static boolean canInserterConnectChute(IBlockReader reader, BlockPos pos, Direction facing) {
		
		BlockPos neighborPos = pos.relative(facing);
		BlockState neighborState = reader.getBlockState(neighborPos);
		Block neighborBlock = neighborState.getBlock();
		
		if(neighborBlock == ModBlocks.T1_ENERGY_CHUTE.get() || neighborBlock == ModBlocks.T2_ENERGY_CHUTE.get() || neighborBlock == ModBlocks.T3_ENERGY_CHUTE.get() ||
		   neighborBlock == ModBlocks.T4_ENERGY_CHUTE.get() || neighborBlock == ModBlocks.T5_ENERGY_CHUTE.get() || neighborBlock == ModBlocks.T6_ENERGY_CHUTE.get()) {
			
			return true;
		}
		
		else return false;
	}
	
	public static boolean canInserterConnectBlock(IBlockReader reader, BlockPos pos, Direction direction) {
		
		BlockPos neighborPos = pos.relative(direction);
		BlockState neighborState = reader.getBlockState(neighborPos);
		Block neighborBlock = neighborState.getBlock();
		TileEntity neighborEntity = reader.getBlockEntity(neighborPos);
		
		if(neighborBlock.hasTileEntity(neighborState) && neighborEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).isPresent() &&
		   neighborBlock != ModBlocks.T1_ENERGY_CHUTE.get() && neighborBlock != ModBlocks.T2_ENERGY_CHUTE.get() && neighborBlock != ModBlocks.T3_ENERGY_CHUTE.get() &&
		   neighborBlock != ModBlocks.T4_ENERGY_CHUTE.get()  && neighborBlock != ModBlocks.T5_ENERGY_CHUTE.get()  && neighborBlock != ModBlocks.T6_ENERGY_CHUTE.get()) {
			
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
    	
        return TileEntityHandler.TILE_ENTITY_T1_ENERGY_EXTRACTOR.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTrace) {
    	
        if (world.isClientSide) {
        	
            return ActionResultType.SUCCESS;
        }
        
        TileEntity te = world.getBlockEntity(pos);
        if (!(te instanceof TileEntityT1EnergyExtractor))
            return ActionResultType.FAIL;

        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, pos);
        return ActionResultType.SUCCESS;
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

		if(canInserterConnectChute(reader, pos, Direction.DOWN)) {
			
			baseShape = VoxelShapes.join(baseShape, DOWN_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.DOWN)) {
			
			baseShape = VoxelShapes.join(baseShape, DOWN_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canInserterConnectChute(reader, pos, Direction.UP)) {
			
			baseShape = VoxelShapes.join(baseShape, UP_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.UP)) {
			
			baseShape = VoxelShapes.join(baseShape, UP_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canInserterConnectChute(reader, pos, Direction.NORTH)) {
			
			baseShape = VoxelShapes.join(baseShape, NORTH_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.NORTH)) {
			
			baseShape = VoxelShapes.join(baseShape, NORTH_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canInserterConnectChute(reader, pos, Direction.SOUTH)) {
			
			baseShape = VoxelShapes.join(baseShape, SOUTH_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.SOUTH)) {
			
			baseShape = VoxelShapes.join(baseShape, SOUTH_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canInserterConnectChute(reader, pos, Direction.WEST)) {
			
			baseShape = VoxelShapes.join(baseShape, WEST_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.WEST)) {
			
			baseShape = VoxelShapes.join(baseShape, WEST_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
		if(canInserterConnectChute(reader, pos, Direction.EAST)) {
			
			baseShape = VoxelShapes.join(baseShape, EAST_SHAPE_CHUTE, IBooleanFunction.OR);
		}
		
		if(canInserterConnectBlock(reader, pos, Direction.EAST)) {
			
			baseShape = VoxelShapes.join(baseShape, EAST_SHAPE_BLOCK, IBooleanFunction.OR);
		}
		
        return baseShape;
    }

    private BlockState setConnections(IBlockReader reader, BlockPos pos, BlockState state) {
    	
        return state.setValue(BLOCK_UP, canInserterConnectBlock(reader, pos, Direction.UP)).setValue(BLOCK_DOWN, canInserterConnectBlock(reader, pos, Direction.DOWN))
        			.setValue(BLOCK_NORTH, canInserterConnectBlock(reader, pos, Direction.NORTH)).setValue(BLOCK_SOUTH, canInserterConnectBlock(reader, pos, Direction.SOUTH))
                	.setValue(BLOCK_EAST, canInserterConnectBlock(reader, pos, Direction.EAST)).setValue(BLOCK_WEST, canInserterConnectBlock(reader, pos, Direction.WEST))
                	.setValue(CHUTE_UP, canInserterConnectChute(reader, pos, Direction.UP)).setValue(CHUTE_DOWN, canInserterConnectChute(reader, pos, Direction.DOWN))
        			.setValue(CHUTE_NORTH, canInserterConnectChute(reader, pos, Direction.NORTH)).setValue(CHUTE_SOUTH, canInserterConnectChute(reader, pos, Direction.SOUTH))
                	.setValue(CHUTE_EAST, canInserterConnectChute(reader, pos, Direction.EAST)).setValue(CHUTE_WEST, canInserterConnectChute(reader, pos, Direction.WEST));
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