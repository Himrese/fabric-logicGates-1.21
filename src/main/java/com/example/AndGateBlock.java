package com.example;


import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import com.example.WireBlockEntity.ModTypes;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;

public class AndGateBlock extends MyGateBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

    // 方块的状态管理
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    // 构造函数
    public AndGateBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		WireBlockEntity blockEntity = new WireBlockEntity(pos, state);
		blockEntity.TYPE = WireBlockEntity.ModTypes.GATE;
        return blockEntity;
    }


    // 设置方块的朝向

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

    protected boolean getLeftSignal(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);

        // 计算左、右、底部相邻方块的位置
        BlockPos leftPos = pos.offset(facing.rotateYCounterclockwise());
        int leftSignal = 0; // 默认返回0表示没有信号
        BlockEntity entity = world.getBlockEntity(leftPos); // 获取底部方块实体
        if(entity instanceof WireBlockEntity) { // 确保是 WireBlockEntity 类型
            WireBlockEntity wireEntity = (WireBlockEntity) entity;
            leftSignal = wireEntity.SIGNAL;
        }

        return leftSignal > 0;
    }

    protected boolean getRightSignal(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);

        // 计算左、右、底部相邻方块的位置
        BlockPos leftPos = pos.offset(facing.rotateYClockwise());
        int Signal = 0; // 默认返回0表示没有信号
        BlockEntity entity = world.getBlockEntity(leftPos); // 获取底部方块实体
        if(entity instanceof WireBlockEntity) { // 确保是 WireBlockEntity 类型
            WireBlockEntity wireEntity = (WireBlockEntity) entity;
            Signal = wireEntity.SIGNAL;
        }

        return Signal > 0;
    }

    protected boolean getBottomSignal(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);

        // 计算左、右、底部相邻方块的位置
        BlockPos leftPos = pos.offset(facing);
        int Signal = 0; // 默认返回0表示没有信号
        BlockEntity entity = world.getBlockEntity(leftPos); // 获取底部方块实体
        if(entity instanceof WireBlockEntity) { // 确保是 WireBlockEntity 类型
            WireBlockEntity wireEntity = (WireBlockEntity) entity;
            Signal = wireEntity.SIGNAL;
        }
        return Signal > 0;
    }

    @Override
    protected int getGateResult(World world, BlockPos pos, BlockState state) {

        boolean leftSignal = getLeftSignal(world, pos, state);
        boolean rightSignal = getRightSignal(world, pos, state);
        boolean bottomSignal = getBottomSignal(world, pos, state);

        return ((leftSignal && rightSignal) || (leftSignal && bottomSignal) || (rightSignal && bottomSignal)) ? 1 : 0;
    }

    @Override
    public void updateGateSignal(World world, BlockPos pos, BlockState state) {

        WireBlockEntity blockEntity = (WireBlockEntity) world.getBlockEntity(pos);

        blockEntity.SIGNAL = this.getGateResult(world, pos, state); // 初始化时计算一次逻辑门的输出
        blockEntity.markDirty(); // 标记为脏数据以便保存到NBT
        // 更新FACING方向
        WireBlockEntity updateBlockEntity = (WireBlockEntity) world.getBlockEntity(pos.offset(state.get(FACING).getOpposite()));
        if(updateBlockEntity instanceof WireBlockEntity){
            updateBlockEntity.update(blockEntity.SIGNAL, state.get(FACING).getOpposite(),ModTypes.GATE); // 通知更新
        }
    }

    @Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            updateGateSignal(world, pos, state); // 当方块被添加到世界时，计算逻辑门的输出信号并更新相邻方块
		}
	}



}
