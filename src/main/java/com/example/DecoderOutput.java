package com.example;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DecoderOutput extends WireBlock{

    public DecoderOutput(Settings settings) {
        super(settings);
    }

	@Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		WireBlockEntity blockEntity = new WireBlockEntity(pos, state);
		blockEntity.TYPE = WireBlockEntity.ModTypes.DECODER_OUTPUT; // 设置类型为 DECODER_INPUT
        return blockEntity;
    }

    

    

}
