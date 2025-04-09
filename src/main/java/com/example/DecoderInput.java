package com.example;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DecoderInput extends WireBlock{

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    @Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}


    public DecoderInput(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		WireBlockEntity blockEntity = new WireBlockEntity(pos, state);
		blockEntity.TYPE = WireBlockEntity.ModTypes.DECODER_INPUT; // 设置类型为 DECODER_INPUT
        return blockEntity;
    }


    public ArrayList<BlockPos> checkOutputState(World world, BlockPos pos, Direction facing){
        ArrayList<BlockPos> outputs = new ArrayList<>();
        for(int i = 0;i<8;i++){
            //facing 的左边2格
            BlockPos neighborPos = pos.offset(facing.getOpposite(), 2).offset(facing.getOpposite().rotateYCounterclockwise(), 7-(2*i));

            BlockEntity blockEntity = world.getBlockEntity(neighborPos);
            if (blockEntity instanceof WireBlockEntity) {
                WireBlockEntity wireEntity = (WireBlockEntity) blockEntity;
                if(wireEntity.TYPE == WireBlockEntity.ModTypes.DECODER_OUTPUT){
                    outputs.add(neighborPos);
                }
            }
        }

        if(outputs.size() == 8){
            return outputs;
        }
        return null;
    }


    @Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        WireBlockEntity blockEntity = (WireBlockEntity) world.getBlockEntity(pos);
        blockEntity.decoderOutputList = checkOutputState(world, pos, state.get(FACING));
		super.onBlockAdded(state, world, pos, oldState, notify);
	}


    @Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WireBlockEntity BlockEntity) {
                if(BlockEntity.decoderOutputList != null)
                    player.sendMessage(Text.literal("signal: " + BlockEntity.SIGNAL + ", type: " + BlockEntity.TYPE.name() + ", State: "+BlockEntity.decoderOutputList.size()), true); // 发送当前信号值和类型给玩家
                else
                    player.sendMessage(Text.literal("No decoder outputs found."), true); // 发送当前信号值和类型给玩家
            }
        }
		return ActionResult.PASS;
		
	}

    

}
