package com.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignalSource extends OnSignal {


	public SignalSource(Settings settings) {
		super(settings);
		defaultSignal = 0;
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WireBlockEntity BlockEntity) {
				BlockEntity.SIGNAL++;
				BlockEntity.markDirty();
				try {
					BlockEntity.update(BlockEntity.SIGNAL,null,BlockEntity.TYPE);
				} catch (MyModException e) {
					e.printStackTrace();
				}
				player.sendMessage(Text.literal("signal: " + BlockEntity.SIGNAL + ", type: " + BlockEntity.TYPE.name()), true); // 发送当前信号值和类型给玩家

            }
        }
		return ActionResult.PASS;
	}
	
}
