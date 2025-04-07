package com.example;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class NandGateBlock extends AndGateBlock {
    public NandGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected int getGateResult(World world, BlockPos pos, BlockState state) {

        boolean leftSignal = getLeftSignal(world, pos, state);
        boolean rightSignal = getRightSignal(world, pos, state);
        boolean bottomSignal = getBottomSignal(world, pos, state);

        return !((leftSignal && rightSignal) || (leftSignal && bottomSignal) || (rightSignal && bottomSignal)) ? 1 : 0;
    }
}
