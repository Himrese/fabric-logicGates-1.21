package com.example;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;



public class OrGateBlock extends MyGateBlock {

    public OrGateBlock(Settings settings) {
        super(settings);
    }


    protected int getGateResult(World world, BlockPos pos, BlockState state) {

        boolean leftSignal = getLeftSignal(world, pos, state);
        boolean rightSignal = getRightSignal(world, pos, state);
        boolean bottomSignal = getBottomSignal(world, pos, state);

        return (leftSignal || rightSignal || bottomSignal)?1:0;
    }
    
}
