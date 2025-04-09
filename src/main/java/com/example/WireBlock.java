package com.example;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WireBlock extends BlockWithEntity  {
	public static final MapCodec<WireBlock> CODEC = createCodec(WireBlock::new);

	@Override
	public MapCodec<WireBlock> getCodec() {
		return CODEC;
	}

	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		WireBlockEntity blockEntity = new WireBlockEntity(pos, state); // 创建 WireBlockEntity 实例，类型为 WIRE
		blockEntity.TYPE = WireBlockEntity.ModTypes.WIRE; // 设置类型为 WIRE
        return blockEntity;
    }
 
    @Override 
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

	public WireBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!oldState.isOf(state.getBlock()) && !world.isClient) {
			//第一次放下方块，获取附近的方块实体并更新信号
			for (Direction direction : DIRECTIONS) { // 6个方向
				BlockPos neighborPos = pos.offset(direction);
				BlockEntity blockEntity = world.getBlockEntity(pos);
				BlockEntity neighborBlockEntity = world.getBlockEntity(neighborPos);
				if(neighborBlockEntity instanceof WireBlockEntity) { // 确保邻居是 WireBlockEntity
					WireBlockEntity neighborWireEntity = ((WireBlockEntity)neighborBlockEntity);// 当前邻居 WireBlockEntity
					WireBlockEntity wireEntity = ((WireBlockEntity)blockEntity); // 当前 WireBlockEntity

					if(neighborWireEntity.TYPE == WireBlockEntity.ModTypes.GATE) {
						if(world.getBlockState(neighborPos).get(MyGateBlock.FACING) == direction) { // 确保是朝向当前 WireBlock 的
							wireEntity.SIGNAL = neighborWireEntity.SIGNAL;
							wireEntity.markDirty();
							return;
						}
					}else{
						wireEntity.SIGNAL = neighborWireEntity.SIGNAL; // 直接使用信号源的值
						wireEntity.markDirty();
						return;
					}



				}

			}
		}
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WireBlockEntity BlockEntity) {
                player.sendMessage(Text.literal("signal: " + BlockEntity.SIGNAL + ", type: " + BlockEntity.TYPE.name()), true); // 发送当前信号值和类型给玩家
            }
        }
		return ActionResult.PASS;
		
	}
}
