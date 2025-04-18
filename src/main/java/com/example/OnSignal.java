package com.example;

import com.example.WireBlockEntity.ModTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class OnSignal extends WireBlock {

	Integer defaultSignal = 1; // 信号值为1，表示有信号输出

	public OnSignal(Settings settings) {
		super(settings);
	}

	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		WireBlockEntity blockEntity = new WireBlockEntity(pos, state);
		blockEntity.TYPE = WireBlockEntity.ModTypes.SIGNAL_SOURCE; // 设置类型为 SIGNAL_SOURCE
        return blockEntity;
    }

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock()) && !world.isClient) {
			// 当方块被添加到世界时，更新信号值
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof WireBlockEntity) {
				WireBlockEntity wireEntity = (WireBlockEntity) blockEntity;
				wireEntity.SIGNAL = defaultSignal; // 设置为1表示有信号输出
				wireEntity.markDirty(); // 标记为脏数据以便保存到NBT
			}
			//更新别的六个相邻方块的信号值
			for(Direction direction : Direction.values()) { // 6个方向
				BlockPos neighborPos = pos.offset(direction);
				BlockEntity neighborBlockEntity = world.getBlockEntity(neighborPos);
				if (neighborBlockEntity instanceof WireBlockEntity) {
					WireBlockEntity neighborWireEntity = (WireBlockEntity) neighborBlockEntity;
					// 更新相邻方块的信号值为当前方块的信号值
					try {
						neighborWireEntity.update(defaultSignal, direction, ModTypes.SIGNAL_SOURCE);
					} catch (MyModException e) {
						e.printStackTrace();
					} // 通知更新
				}
			}
		}
	}




	

}
