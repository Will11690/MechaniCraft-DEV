package com.github.will11690.mechanicraft.blocks.machines.tier4.quarry;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import com.github.will11690.mechanicraft.energy.MechaniCraftEnergyStorage;
import com.github.will11690.mechanicraft.init.ModBlocks;
import com.github.will11690.mechanicraft.util.handlers.TileEntityHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityQuarry extends BlockEntity {
	
	//TODO Add a cross section to the top of the structure and then 1 y level down, in  the center of that cross section, place laser core
	
    private BlockPos currentBlockPos;
    private int miningX;
    private int miningY;
    private int miningZ;
	private boolean isStructureValid;
    private boolean isMiningComplete;
    private int mineDelay = 10;//TODO figure out most balanced number for this, right now leaning towards 20
    private int tickCounter = 0;
    
    //Offsets
    //TODO Make these changable via a marker system or GUI's
    //Facing South, Building North offsets
    private int startPosStructureXSouth = -4;
    private int startPosStructureYSouth = 0;
    private int startPosStructureZSouth = -9;
    private int endPosStructureXSouth = 4;
    private int endPosStructureYSouth = 4;
    private int endPosStructureZSouth = -1;
    private BlockPos laserCoreNorth;
    
    //Facing South, Mining North offsets
    private int startPosMiningXSouth = -3;
    private int startPosMiningYSouth = -1;
    private int startPosMiningZSouth = -8;
    private int endPosMiningXSouth = 3;
    private int endPosMiningZSouth = -2;
    
    //Facing North, Building South offsets
    private int startPosStructureXNorth = -4;
    private int startPosStructureYNorth = 0;
    private int startPosStructureZNorth = 9;
    private int endPosStructureXNorth = 4;
    private int endPosStructureYNorth = 4;
    private int endPosStructureZNorth = 1;
    private BlockPos laserCoreSouth;
    
    //Facing North, Mining South offsets
    private int startPosMiningXNorth = -3;
    private int startPosMiningYNorth = -1;
    private int startPosMiningZNorth = 2;
    private int endPosMiningXNorth = 3;
    private int endPosMiningZNorth = 8;

    //Facing East, Building West offsets
    private int startPosStructureXEast = -9;
    private int startPosStructureYEast = 0;
    private int startPosStructureZEast = -4;
    private int endPosStructureXEast = -1;
    private int endPosStructureYEast = 4;
    private int endPosStructureZEast = 4;
    private BlockPos laserCoreEast;
    
    //Facing East, Mining West offsets
    private int startPosMiningXEast = -8;
    private int startPosMiningYEast = -1;
    private int startPosMiningZEast = -3;
    private int endPosMiningXEast = -2;
    private int endPosMiningZEast = 3;

    //Facing West, Building East offsets
    private int startPosStructureXWest = 9;
    private int startPosStructureYWest = 0;
    private int startPosStructureZWest = 4;
    private int endPosStructureXWest = 1;
    private int endPosStructureYWest = 4;
    private int endPosStructureZWest = -4;
    private BlockPos laserCoreWest;
    
    //Facing West, Mining East offsets
    private int startPosMiningXWest = 2;
    private int startPosMiningYWest = -1;
    private int startPosMiningZWest = -3;
    private int endPosMiningXWest = 8;
    private int endPosMiningZWest = 3;
    //TODO Detect when frame is messed up or there is an intrusion to clear it
    //private boolean frameSpaceClear;
    //private boolean frameBroken;
    //private boolean quarryFrameIntrusion;
	
	private ItemStackHandler items = createItems();
	private MechaniCraftEnergyStorage energyStorage = createEnergy();
	
	private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	private final LazyOptional<IItemHandler> itemHandler  = LazyOptional.of(() -> items);
	
	boolean breakBlock = false;

	//TODO bug hunting, miner still only marking complete when last block is air, need to figure out why end position is ignoring Bedrock blocks, May have to do with them getting skipped in the methods handling scanning for blocks to mine
	//TODO calculate amount of power needed to mine block based on hardness
	//TODO Fix voxel shapes of structure pipes
	//TODO GUI and related control elements and such
	//TODO IMMA FIRIN MY LAZER, BWAHHHH
	
    public BlockEntityQuarry() {
        super(BlockEntityHandler.BLOCK_ENTITY_QUARRY.get());
        isStructureValid = false;
        isMiningComplete = false;
        laserCoreNorth = new BlockPos(0,0,0);
        laserCoreSouth = new BlockPos(0,0,0);
        laserCoreEast = new BlockPos(0,0,0);
        laserCoreWest = new BlockPos(0,0,0);
        clearArea();
        //frameSpaceClear = false;
        //frameBroken = false;
        //quarryFrameIntrusion = false;
    }
	
	private ItemStackHandler createItems() {
		
		return new ItemStackHandler(1) {
			
	        @Override
	        protected void onContentsChanged(int slot) {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
	        }
		};
	}

	private MechaniCraftEnergyStorage createEnergy() {
		
		return new MechaniCraftEnergyStorage(1000000) {

			@Override
			protected void onEnergyChanged() {
				if(level != null) {
					BlockState state = level.getBlockState(worldPosition);
					level.sendBlockUpdated(worldPosition, state, state, Constants.BlockFlags.DEFAULT);
					setChanged();
				}
			}
		};
	}

    public void tick(Level level, BlockState state, BlockEntityType<T> type) {
    	
    	if (this.level == null) {

			return;
		}
    	
		this.passItemUp();
		this.updatePositions();
		
    	if(energyStorage.getEnergyStored() < energyStorage.getCapacity() && isMiningComplete == false) {
    		
    		this.receivePower();
    	}
    	
        if (isMiningComplete == false && (isStructureValid != true || level.getGameTime() % 10 == 0)) {
            validateStructure();
        }

        if (isMiningComplete == false && validateStructure() == false) {
            rebuildStructure();
        }
        
        if(tickCounter >= mineDelay) {
        	if (isMiningComplete == false && isStructureValid == true) {
        		tickCounter = 0;
        		startQuarryMining();
        	} else if(isMiningComplete == true && isStructureValid == true) {
        		tickCounter = 0;
        		removeStructure();
        	}
        } else {
        	tickCounter = tickCounter + 1;
        }
    }
    
    @SuppressWarnings("deprecation")
	private boolean validateStructure() {
        Level level = this.getLevel();
        if (level == null) {
            isStructureValid = false;
            return false;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXNorth, startPosStructureYNorth, startPosStructureZNorth);
        	endPos = worldPosition.offset(endPosStructureXNorth, endPosStructureYNorth, endPosStructureZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXSouth, startPosStructureYSouth, startPosStructureZSouth);
        	endPos = worldPosition.offset(endPosStructureXSouth, endPosStructureYSouth, endPosStructureZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosStructureXEast, startPosStructureYEast, startPosStructureZEast);
        	endPos = worldPosition.offset(endPosStructureXEast, endPosStructureYEast, endPosStructureZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosStructureXWest, startPosStructureYWest, startPosStructureZWest);
        	endPos = worldPosition.offset(endPosStructureXWest, endPosStructureYWest, endPosStructureZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {
        	
        	//Check floor outline
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		if ((checkPos.getX() == startPos.getX() || checkPos.getX() == endPos.getX() ||
        				checkPos.getZ() == startPos.getZ() || checkPos.getZ() == endPos.getZ()) &&
        				checkPos.getY() == startPos.getY()) {
        			if (!level.getBlockState(checkPos).getBlock().equals(ModBlocks.STRUCTURE_PIPE.get())) {
        				isStructureValid = false;
        				//frameBroken = true;
        				return false;
        			}
        		}
        	}

        	//Check roof outline
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		if ((checkPos.getX() == startPos.getX() || checkPos.getX() == endPos.getX() ||
        				checkPos.getZ() == startPos.getZ() || checkPos.getZ() == endPos.getZ()) &&
        				checkPos.getY() == endPos.getY()) {
        			if (!level.getBlockState(checkPos).getBlock().equals(ModBlocks.STRUCTURE_PIPE.get())) {
        				isStructureValid = false;
        				return false;
        			}
        		}
        	}

        	//Check corners
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		if ((checkPos.getX() == startPos.getX() || checkPos.getX() == endPos.getX()) &&
        				(checkPos.getZ() == startPos.getZ() || checkPos.getZ() == endPos.getZ()) &&
        				(checkPos.getY() > startPos.getY() && checkPos.getY() < startPos.getY() + 4)) {
        			if (!level.getBlockState(checkPos).getBlock().equals(ModBlocks.STRUCTURE_PIPE.get())) {
        				isStructureValid = false;
        				//frameBroken = true;
        				return false;
        			}
        		}
        	}

        	//Check clear spaces in center
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		if (checkPos.getX() > startPos.getX() && checkPos.getX() < endPos.getX() &&
        				checkPos.getY() > startPos.getY() && checkPos.getY() < endPos.getY() &&
        				checkPos.getZ() > startPos.getZ() && checkPos.getZ() < endPos.getZ()) {
        			if (!level.getBlockState(checkPos).isAir()) {
        				isStructureValid = false;
        				//frameBroken = true;
        				return false;
        			}
        		}
        	}
        
        	//Check clear spaces in walls
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		//Left and Right Walls
        		if ((checkPos.getX() == startPos.getX() || checkPos.getX() == endPos.getX()) &&
        				(checkPos.getZ() > startPos.getZ() && checkPos.getZ() < endPos.getZ()) &&
        				(checkPos.getY() > startPos.getY() && checkPos.getY() < startPos.getY() + 4)) {
        			if (!level.getBlockState(checkPos).isAir()) {
        				isStructureValid = false;
        				//quarryFrameIntrusion = true;
        				return false;
        			}
        		}

            	//Front and Back Walls
            	if ((checkPos.getZ() == startPos.getZ() || checkPos.getZ() == endPos.getZ()) &&
            			(checkPos.getX() > startPos.getX() && checkPos.getX() < endPos.getX()) &&
            			(checkPos.getY() > startPos.getY() && checkPos.getY() < startPos.getY() + 4)) {
            		if (!level.getBlockState(checkPos).isAir()) {
            			isStructureValid = false;
            			//quarryFrameIntrusion = true;
            			return false;
            		}
            	}
        	}

        	//Check clear spaces in roof
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
        		if (checkPos.getX() > startPos.getX() && checkPos.getX() < endPos.getX() &&
        				checkPos.getZ() > startPos.getZ() && checkPos.getZ() < endPos.getZ() &&
        				checkPos.getY() == startPos.getY() + 4) {
                	if (!level.getBlockState(checkPos).isAir()) {
                    	isStructureValid = false;
                    	//quarryFrameIntrusion = true;
                    	return false;
                	}
            	}
        	}

        	//Check clear spaces in floor
        	for (BlockPos checkPos : BlockPos.betweenClosed(startPos, endPos)) {
            	if (checkPos.getX() > startPos.getX() && checkPos.getX() < endPos.getX() &&
                    	checkPos.getZ() > startPos.getZ() && checkPos.getZ() < endPos.getZ() &&
                    	checkPos.getY() == startPos.getY()) {
                	if (!level.getBlockState(checkPos).isAir()) {
                    	isStructureValid = false;
                    	//quarryFrameIntrusion = true;
                    	return false;
                	}
            	}
        	}

        	isStructureValid = true;
        	return true;
        }
        
        isStructureValid = false;
        return false;
    }
    
    private void updatePositions() {
    	
    	int worldPosX = this.worldPosition.getX();
    	int worldPosY = this.worldPosition.getY();
    	int worldPosZ = this.worldPosition.getZ();
    	if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH && (laserCoreNorth == null || (laserCoreNorth.getX() == 0 && laserCoreNorth.getY() == 0 && laserCoreNorth.getZ() == 0))) {
		
    		laserCoreNorth = new BlockPos(worldPosX, worldPosY + 3, worldPosZ + 4);
    	}
	
    	if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH && (laserCoreSouth == null || (laserCoreSouth.getX() == 0 && laserCoreSouth.getY() == 0 && laserCoreSouth.getZ() == 0))) {
		
    		laserCoreSouth = new BlockPos(worldPosX, worldPosY + 3, worldPosZ - 4);
    	}
	
    	if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST && (laserCoreEast == null || (laserCoreEast.getX() == 0 && laserCoreEast.getY() == 0 && laserCoreEast.getZ() == 0))) {
		
    		laserCoreEast = new BlockPos(worldPosX - 4, worldPosY + 3, worldPosZ);
    	}
	
    	if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST && (laserCoreWest == null || (laserCoreWest.getX() == 0 && laserCoreWest.getY() == 0 && laserCoreWest.getZ() == 0))) {
		
    		laserCoreWest = new BlockPos(worldPosX + 4, worldPosY + 3, worldPosZ);
    	}
    	
    	if(currentBlockPos != null) {
    	
    		miningX = currentBlockPos.getX();
    		miningY = currentBlockPos.getY();
    		miningZ = currentBlockPos.getZ();
    	}
    }

    private void buildStructure() {
        Level level = this.getLevel();
        if (level == null) {
            return;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXNorth, startPosStructureYNorth, startPosStructureZNorth);
        	endPos = worldPosition.offset(endPosStructureXNorth, endPosStructureYNorth, endPosStructureZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXSouth, startPosStructureYSouth, startPosStructureZSouth);
        	endPos = worldPosition.offset(endPosStructureXSouth, endPosStructureYSouth, endPosStructureZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosStructureXEast, startPosStructureYEast, startPosStructureZEast);
        	endPos = worldPosition.offset(endPosStructureXEast, endPosStructureYEast, endPosStructureZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosStructureXWest, startPosStructureYWest, startPosStructureZWest);
        	endPos = worldPosition.offset(endPosStructureXWest, endPosStructureYWest, endPosStructureZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {

        	for (BlockPos buildPos : BlockPos.betweenClosed(startPos, endPos)) {
        		BlockState blockState = level.getBlockState(buildPos);
        		Block block = blockState.getBlock();

        		if (block.hasTileEntity(blockState)) {
        			continue;
        		}
            
        		//Floor
        		if ((buildPos.getX() == startPos.getX() || buildPos.getX() == endPos.getX() ||
        				buildPos.getZ() == startPos.getZ() || buildPos.getZ() == endPos.getZ()) &&
        				buildPos.getY() == startPos.getY()) {
					level.setBlockAndUpdate(buildPos, ModBlocks.STRUCTURE_PIPE.get().defaultBlockState());
        		}
            
        		//Roof
        		if ((buildPos.getX() == startPos.getX() || buildPos.getX() == endPos.getX() ||
        				buildPos.getZ() == startPos.getZ() || buildPos.getZ() == endPos.getZ()) &&
        				buildPos.getY() == endPos.getY()) {
					level.setBlockAndUpdate(buildPos, ModBlocks.STRUCTURE_PIPE.get().defaultBlockState());
        		}
            
        		//Corners
        		if ((buildPos.getX() == startPos.getX() || buildPos.getX() == endPos.getX()) &&
        				(buildPos.getZ() == startPos.getZ() || buildPos.getZ() == endPos.getZ()) &&
        				(buildPos.getY() > startPos.getY() && buildPos.getY() < startPos.getY() + 4)) {
					level.setBlockAndUpdate(buildPos, ModBlocks.STRUCTURE_PIPE.get().defaultBlockState());
        		}
        	}
        }
    }
    
    private void removeStructure() {
        Level level = this.getLevel();
        if (level == null) {
            return;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXNorth, startPosStructureYNorth, startPosStructureZNorth);
        	endPos = worldPosition.offset(endPosStructureXNorth, endPosStructureYNorth, endPosStructureZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXSouth, startPosStructureYSouth, startPosStructureZSouth);
        	endPos = worldPosition.offset(endPosStructureXSouth, endPosStructureYSouth, endPosStructureZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosStructureXEast, startPosStructureYEast, startPosStructureZEast);
        	endPos = worldPosition.offset(endPosStructureXEast, endPosStructureYEast, endPosStructureZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosStructureXWest, startPosStructureYWest, startPosStructureZWest);
        	endPos = worldPosition.offset(endPosStructureXWest, endPosStructureYWest, endPosStructureZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {

        	for (BlockPos removePos : BlockPos.betweenClosed(startPos, endPos)) {
        		if (!removePos.equals(worldPosition)) {
					level.setBlockAndUpdate(removePos, Blocks.AIR.defaultBlockState());
        		}
        	}
        }
    }

    private void rebuildStructure() {
        removeStructure();
        buildStructure();
        isStructureValid = validateStructure();
    }

	private void clearArea() {
        Level level = this.getLevel();
        if (level == null) {
            return;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXNorth, startPosStructureYNorth, startPosStructureZNorth);
        	endPos = worldPosition.offset(endPosStructureXNorth, endPosStructureYNorth, endPosStructureZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXSouth, startPosStructureYSouth, startPosStructureZSouth);
        	endPos = worldPosition.offset(endPosStructureXSouth, endPosStructureYSouth, endPosStructureZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosStructureXEast, startPosStructureYEast, startPosStructureZEast);
        	endPos = worldPosition.offset(endPosStructureXEast, endPosStructureYEast, endPosStructureZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosStructureXWest, startPosStructureYWest, startPosStructureZWest);
        	endPos = worldPosition.offset(endPosStructureXWest, endPosStructureYWest, endPosStructureZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {

        	for (BlockPos clearPos : BlockPos.betweenClosed(startPos, endPos)) {
        		BlockState blockState = level.getBlockState(clearPos);
        		if (blockState.isAir()) {
        			continue;
        		}
        		if (blockState.hasTileEntity()) {
        			continue;
        		}
				level.destroyBlock(clearPos, false);
        		//frameSpaceClear = true;
        	}
        }
    }

	public void startQuarryMining() {
        Level level = this.getLevel();
        if (level == null) {
            return;
        }

        
        if (currentBlockPos == null) {
        	
        	if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        		
        		currentBlockPos = worldPosition.offset(startPosMiningXNorth, startPosMiningYNorth, startPosMiningZNorth);
        		
        	} else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        		
        		currentBlockPos = worldPosition.offset(startPosMiningXSouth, startPosMiningYSouth, startPosMiningZSouth);
        		
        	} else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        		
        		currentBlockPos =worldPosition.offset(startPosMiningXEast, startPosMiningYEast, startPosMiningZEast);
        		
        	} else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        		
        		currentBlockPos = worldPosition.offset(startPosMiningXWest, startPosMiningYWest, startPosMiningZWest);
        	}
        }
        
        BlockState blockState = level.getBlockState(currentBlockPos);
        ItemStack minedStack = ItemStack.EMPTY;
        
        if(blockState.isAir() || blockState.hasTileEntity() || blockState.getBlock() == Blocks.BEDROCK) {
        	
        	moveNextBlock();
        }
        
        //TODO add config for energy values
        if(this.energyStorage.getEnergyStored() >= 1000) {
        	
        	this.energyStorage.consumeEnergy(100);
        	
        	List<ItemStack> drops = Block.getDrops(blockState, (ServerLevel) level, currentBlockPos, null);
        	for (ItemStack drop : drops) {
        		
        		minedStack = drop;
        		System.out.println("Mined Stack: " + minedStack);
        		break;
        	}
        	
        	if(minedStack.getItem().equals(items.getStackInSlot(0).getItem()) || items.getStackInSlot(0).isEmpty() || minedStack.isEmpty()) {
        	
        		if(!minedStack.isEmpty()) {
        			items.insertItem(0, minedStack, false);
        		}

				level.destroyBlock(currentBlockPos, false);
        		minedStack = ItemStack.EMPTY;
        		this.passItemUp();
        		moveNextBlock();
        	}
        }
    }
    
    public boolean scanForFluids() {
        Level level = this.getLevel();
        if (level == null) {
            return false;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXNorth, startPosStructureYNorth, startPosStructureZNorth);
        	endPos = worldPosition.offset(endPosStructureXNorth, 0/*Zero because this number doesn't matter*/, endPosStructureZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosStructureXSouth, startPosStructureYSouth, startPosStructureZSouth);
        	endPos = worldPosition.offset(endPosStructureXSouth, 0/*Zero because this number doesn't matter*/, endPosStructureZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosStructureXEast, startPosStructureYEast, startPosStructureZEast);
        	endPos = worldPosition.offset(endPosStructureXEast, 0/*Zero because this number doesn't matter*/, endPosStructureZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosStructureXWest, startPosStructureYWest, startPosStructureZWest);
        	endPos = worldPosition.offset(endPosStructureXWest, 0/*Zero because this number doesn't matter*/, endPosStructureZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {

        	boolean foundFluid = false;
        	for (int x = startPos.getX(); x <= endPos.getX(); x++) {
        		for (int y = startPos.getY(); y >= 0; y--) {
        			for (int z = startPos.getZ(); z <= endPos.getZ(); z++) {
        				BlockPos currentPos = new BlockPos(x, y, z);
        				BlockState blockState = level.getBlockState(currentPos);
        				FluidState fluidState = blockState.getFluidState();

        				if (!fluidState.isEmpty()) {
        					foundFluid = true;
        					removeFluidAndNeighbors(currentPos, level);
        					currentBlockPos = currentPos;
        				}
        			}
        		}
        	}

        	return foundFluid;
        }
        return false;
    }
    
    private void removeFluidAndNeighbors(BlockPos pos, Level level) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getFluidState().isEmpty()) {
            return;
        }

		level.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
        this.energyStorage.consumeEnergy(100); // Consume energy for the block

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            FluidState neighborFluidState = neighborState.getFluidState();

            if (!neighborFluidState.isEmpty()) {
				level.setBlockAndUpdate(neighborPos, Blocks.COBBLESTONE.defaultBlockState());
                this.energyStorage.consumeEnergy(100); // Consume energy for the neighbor block
                removeFluidAndNeighbors(neighborPos, level); // Recursively remove neighbors
            }
        }
    }

	private void moveNextBlock() {
        Level level = this.getLevel();
        if (level == null) {
            return;
        }
        
        BlockPos startPos;
        BlockPos endPos;
        
        if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	startPos = worldPosition.offset(startPosMiningXNorth, startPosMiningYNorth, startPosMiningZNorth);
        	endPos = worldPosition.offset(endPosMiningXNorth, 0/*Zero because this number doesn't matter*/, endPosMiningZNorth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	startPos = worldPosition.offset(startPosMiningXSouth, startPosMiningYSouth, startPosMiningZSouth);
        	endPos = worldPosition.offset(endPosMiningXSouth, 0/*Zero because this number doesn't matter*/, endPosMiningZSouth);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	startPos = worldPosition.offset(startPosMiningXEast, startPosMiningYEast, startPosMiningZEast);
        	endPos = worldPosition.offset(endPosMiningXEast, 0/*Zero because this number doesn't matter*/, endPosMiningZEast);
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	startPos = worldPosition.offset(startPosMiningXWest, startPosMiningYWest, startPosMiningZWest);
        	endPos = worldPosition.offset(endPosMiningXWest, 0/*Zero because this number doesn't matter*/, endPosMiningZWest);
        	
        } else {
        
        	startPos = null;
        	endPos = null;
        }
        
        if(startPos != null && endPos != null) {

        	for (int x = startPos.getX(); x <= endPos.getX(); x++) {
            	for (int y = startPos.getY(); y >= 0; y--) {
                	for (int z = startPos.getZ(); z <= endPos.getZ(); z++) {
                    	BlockPos blockPos = new BlockPos(x, y, z);
                    	BlockState blockState = level.getBlockState(blockPos);
                    
                    	if(blockState.isAir() || blockState.hasTileEntity() || blockState.getBlock() == Blocks.BEDROCK) {
                    		continue;
                    	}
                    
                    	if (!blockState.isAir() && !blockState.hasTileEntity() && blockState.getBlock() != Blocks.BEDROCK) {
                    		if(blockState.getFluidState().getType() == Fluids.WATER || blockState.getFluidState().getType() == Fluids.LAVA ||
                    			blockState.getFluidState().getType() == Fluids.FLOWING_LAVA || blockState.getFluidState().getType() == Fluids.FLOWING_WATER) {
                    			scanForFluids();
                    		} else {
                    			currentBlockPos = blockPos;
                    			return;
                    		}
                    	}
                	}
            	}
        	}

        	if (currentBlockPos.equals(new BlockPos(endPos.getX(), 1, endPos.getZ())) && (level.getBlockState(currentBlockPos).isAir() || level.getBlockState(currentBlockPos).getBlock().equals(Blocks.BEDROCK))) {
        		isMiningComplete = true;
        		
        		if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        		
        			laserCoreNorth = new BlockPos(this.worldPosition.getX() + 4, this.worldPosition.getY() + 3, this.worldPosition.getZ() + 4);
        			miningX = laserCoreNorth.getX();
        			miningY = laserCoreNorth.getY();
        			miningZ = laserCoreNorth.getZ();
        		}
        		if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        		
        			laserCoreSouth = new BlockPos(this.worldPosition.getX() + 4, this.worldPosition.getY() + 3, this.worldPosition.getZ() + 4);
        			miningX = laserCoreSouth.getX();
        			miningY = laserCoreSouth.getY();
        			miningZ = laserCoreSouth.getZ();
        		}
        		if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        		
        			laserCoreEast = new BlockPos(this.worldPosition.getX() + 4, this.worldPosition.getY() + 3, this.worldPosition.getZ() + 4);
        			miningX = laserCoreEast.getX();
        			miningY = laserCoreEast.getY();
        			miningZ = laserCoreEast.getZ();
        		}
        		if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        		
        			laserCoreWest = new BlockPos(this.worldPosition.getX() + 4, this.worldPosition.getY() + 3, this.worldPosition.getZ() + 4);
        			miningX = laserCoreWest.getX();
        			miningY = laserCoreWest.getY();
        			miningZ = laserCoreWest.getZ();
        		}
        	}
        }
    }
    
    public void passItemUp() {
        BlockPos neighborPosUp = worldPosition.above();
        BlockState neighborStateUp = level.getBlockState(neighborPosUp);
        Block neighborBlockUp = neighborStateUp.getBlock();
        BlockEntity neighborEntityUp = level.getBlockEntity(neighborPosUp);

        if (neighborBlockUp.hasTileEntity(neighborStateUp) && neighborEntityUp.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).isPresent()) {
            IItemHandler neighborInventory = neighborEntityUp.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElse(null);

            if (neighborInventory != null && neighborInventory.getSlots() > 1) {
                int slotCount = neighborInventory.getSlots();
                int currentIndex = 0;
                int initialIndex = currentIndex;

                while (true) {
                    ItemStack neighborStack = neighborInventory.getStackInSlot(currentIndex);

                    if (neighborStack.isEmpty() || neighborStack.getItem().equals(items.getStackInSlot(0).getItem())) {
                        if (neighborStack.getCount() < neighborStack.getMaxStackSize()) {
                            ItemStack simulate = neighborInventory.insertItem(currentIndex, items.getStackInSlot(0), true);
                            int availableSpace = items.getStackInSlot(0).getCount() - simulate.getCount();

                            neighborInventory.insertItem(currentIndex, items.getStackInSlot(0).split(availableSpace), false);
                            items.extractItem(0, availableSpace, false);
                        }
                    }

                    currentIndex = (currentIndex + 1) % slotCount;

                    if (currentIndex == initialIndex) {
                        //TODO Add a state update to block if mining progress is stalled by inventory being full and nowhere to put items to continue mining
                        break;
                    }
                }
                
            } else if (neighborInventory != null && (neighborInventory.getStackInSlot(0).isEmpty() || neighborInventory.getStackInSlot(0).getItem().equals(items.getStackInSlot(0).getItem()))) {
                ItemStack simulate = neighborInventory.insertItem(0, items.getStackInSlot(0), true);
                int availableSpace = items.getStackInSlot(0).getCount() - simulate.getCount();

                neighborInventory.insertItem(0, items.getStackInSlot(0).split(availableSpace), false);
                items.extractItem(0, availableSpace, false);
            }
            
        } else if (!items.getStackInSlot(0).isEmpty()) {
            Inventory storedInv = new Inventory(items.getStackInSlot(0));
            InventoryHelper.dropContents(level, worldPosition.above(), storedInv);
            items.setStackInSlot(0, ItemStack.EMPTY);
        }
    }
	
	private void receivePower() {
		
        AtomicInteger energy = new AtomicInteger(energyStorage.getEnergyStored());
        
        if(energy.get() < energyStorage.getCapacity()) {
        	
            for(Direction direction : Direction.values()) {
            	
                BlockEntity te = level.getBlockEntity(worldPosition.relative(direction));
                
                if(te != null) {
                	
                    boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                    	
                        if(handler.canExtract()) {
                                	
                            int extracted = handler.extractEnergy(Math.min(handler.getEnergyStored(), energyStorage.getCapacity()), false);
                            energy.addAndGet(extracted);
                            energyStorage.addEnergy(extracted);
                            setChanged();
                                    
                            return energy.get() > 0;
                                    
                            } else {
                                	
                                return true;
                            }
                                
                        }).orElse(true);
                    
                    if(!doContinue) {
                    	
                        return;
                    }
                }
            }
        }
    }
	
	public int getMiningX() {
		
		return this.miningX;
	}
	
	public int getMiningY() {
		
		return this.miningY;
	}
	
	public int getMiningZ() {
		
		return this.miningZ;
	}
	
	public int getLaserCoreX() {
		
		if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	return laserCoreNorth.getX();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	return laserCoreSouth.getX();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	return laserCoreEast.getX();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	return laserCoreWest.getX();
        	
        } else {
        
        	return 0;
        }
	}
	
	public int getLaserCoreY() {
		
		if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	return laserCoreNorth.getY();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	return laserCoreSouth.getY();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	return laserCoreEast.getY();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	return laserCoreWest.getY();
        	
        } else {
        
        	return 0;
        }
	}
	
	public int getLaserCoreZ() {
		
		if(this.getBlockState().getValue(Quarry.FACING) == Direction.NORTH) {
        	
        	return laserCoreNorth.getZ();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.SOUTH) {
        	
        	return laserCoreSouth.getZ();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.EAST) {
        	
        	return laserCoreEast.getZ();
        	
        } else if(this.getBlockState().getValue(Quarry.FACING) == Direction.WEST) {
        	
        	return laserCoreWest.getZ();
        	
        } else {
        
        	return 0;
        }
	}
	
	boolean blockBreak() {
		Quarry block = (Quarry) level.getBlockState(this.worldPosition).getBlock();
		
		return block.breakBlock;
	}

	boolean blockBeingBroken(boolean onRemoved) {
		
		return breakBlock = onRemoved;
	}
	
	@Nullable
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {

		if (!this.remove && side != null) {

			if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				
				return itemHandler.cast();
			}

			if (cap == CapabilityEnergy.ENERGY) {
				
				return energy.cast();
			}
			
		} else if(breakBlock == true && side == null) {

			if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				
				return itemHandler.cast();
			}
		}

		return super.getCapability(cap, side);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT tags) {

		super.load(state, tags);
		this.items.deserializeNBT(tags.getCompound("items"));
		this.energyStorage.deserializeNBT(tags.getCompound("energy"));
		this.miningX = tags.getInt("miningX");
		this.miningY = tags.getInt("miningY");
		this.miningZ = tags.getInt("miningZ");
		this.laserCoreNorth = new BlockPos(tags.getInt("corePosNorthX"), tags.getInt("corePosNorthY"), tags.getInt("corePosNorthX"));
		this.laserCoreSouth = new BlockPos(tags.getInt("corePosSouthX"), tags.getInt("corePosSouthY"), tags.getInt("corePosSouthX"));
		this.laserCoreEast = new BlockPos(tags.getInt("corePosEastX"), tags.getInt("corePosEastY"), tags.getInt("corePosEastX"));
		this.laserCoreWest = new BlockPos(tags.getInt("corePosWestX"), tags.getInt("corePosWestY"), tags.getInt("corePosWestX"));
		
		if(tags.contains("mining_pos_x") && tags.contains("mining_pos_y") && tags.contains("mining_pos_z")) {
			
			BlockPos savedPos = new BlockPos(tags.getInt("mining_pos_x"), tags.getInt("mining_pos_y"), tags.getInt("mining_pos_z"));
			currentBlockPos = savedPos;
		}
	}

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
    	
    	CompoundNBT tags = this.getUpdateTag();
        this.save(tags);
        return new SUpdateTileEntityPacket(this.worldPosition, 1, tags);
    }
    
    @Override
    public CompoundNBT getUpdateTag() {
    	
    	CompoundNBT tags = new CompoundNBT();

		tags.put("items", items.serializeNBT());
		tags.put("energy", energyStorage.serializeNBT());
		tags.putInt("miningX", miningX);
		tags.putInt("miningY", miningY);
		tags.putInt("miningZ", miningZ);
		tags.putInt("corePosNorthX", laserCoreNorth.getX());
		tags.putInt("corePosNorthY", laserCoreNorth.getY());
		tags.putInt("corePosNorthZ", laserCoreNorth.getZ());
		
		tags.putInt("corePosSouthX", laserCoreSouth.getX());
		tags.putInt("corePosSouthY", laserCoreSouth.getY());
		tags.putInt("corePosSouthZ", laserCoreSouth.getZ());
		
		tags.putInt("corePosEastX", laserCoreEast.getX());
		tags.putInt("corePosEastY", laserCoreEast.getY());
		tags.putInt("corePosEastZ", laserCoreEast.getZ());
		
		tags.putInt("corePosWestX", laserCoreWest.getX());
		tags.putInt("corePosWestY", laserCoreWest.getY());
		tags.putInt("corePosWestZ", laserCoreWest.getZ());
		
		if(currentBlockPos != null) {
			tags.putInt("mining_pos_x", currentBlockPos.getX());
			tags.putInt("mining_pos_y", currentBlockPos.getY());
			tags.putInt("mining_pos_z", currentBlockPos.getZ());
		}
        
        this.save(tags);
        
        return tags;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tags) {

		this.items.deserializeNBT(tags.getCompound("items"));
		this.energyStorage.deserializeNBT(tags.getCompound("energy"));
		this.miningX = tags.getInt("miningX");
		this.miningY = tags.getInt("miningY");
		this.miningZ = tags.getInt("miningZ");
		this.laserCoreNorth = new BlockPos(tags.getInt("corePosNorthX"), tags.getInt("corePosNorthY"), tags.getInt("corePosNorthX"));
		this.laserCoreSouth = new BlockPos(tags.getInt("corePosSouthX"), tags.getInt("corePosSouthY"), tags.getInt("corePosSouthX"));
		this.laserCoreEast = new BlockPos(tags.getInt("corePosEastX"), tags.getInt("corePosEastY"), tags.getInt("corePosEastX"));
		this.laserCoreWest = new BlockPos(tags.getInt("corePosWestX"), tags.getInt("corePosWestY"), tags.getInt("corePosWestX"));
		
		if(tags.contains("mining_pos_x") && tags.contains("mining_pos_y") && tags.contains("mining_pos_z")) {
			
			BlockPos savedPos = new BlockPos(tags.getInt("mining_pos_x"), tags.getInt("mining_pos_y"), tags.getInt("mining_pos_z"));
			currentBlockPos = savedPos;
		}
        
        this.load(state, tags);
        
    	super.handleUpdateTag(state, tags);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
    	
        this.load(this.getBlockState(), packet.getTag());
        super.onDataPacket(net, packet);
    }

	@Override
	public CompoundNBT save(CompoundNBT tags) {

		super.save(tags);
		tags.put("items", items.serializeNBT());
		tags.put("energy", energyStorage.serializeNBT());
		tags.putInt("miningX", miningX);
		tags.putInt("miningY", miningY);
		tags.putInt("miningZ", miningZ);
		tags.putInt("corePosNorthX", laserCoreNorth.getX());
		tags.putInt("corePosNorthY", laserCoreNorth.getY());
		tags.putInt("corePosNorthZ", laserCoreNorth.getZ());
		
		tags.putInt("corePosSouthX", laserCoreSouth.getX());
		tags.putInt("corePosSouthY", laserCoreSouth.getY());
		tags.putInt("corePosSouthZ", laserCoreSouth.getZ());
		
		tags.putInt("corePosEastX", laserCoreEast.getX());
		tags.putInt("corePosEastY", laserCoreEast.getY());
		tags.putInt("corePosEastZ", laserCoreEast.getZ());
		
		tags.putInt("corePosWestX", laserCoreWest.getX());
		tags.putInt("corePosWestY", laserCoreWest.getY());
		tags.putInt("corePosWestZ", laserCoreWest.getZ());
		
		if(currentBlockPos != null) {
			tags.putInt("mining_pos_x", currentBlockPos.getX());
			tags.putInt("mining_pos_y", currentBlockPos.getY());
			tags.putInt("mining_pos_z", currentBlockPos.getZ());
		}
		return tags;
	}
	
	@Override
	public void setRemoved() {

		itemHandler.invalidate();
		energy.invalidate();
		//blocksBelow.clear();
		//blocksToSkip.clear();
		//blocksBelowCheck.clear();
		super.setRemoved();
	}
}