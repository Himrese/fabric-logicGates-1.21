package com.example;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;



public class NotGateBlock extends MyGateBlock {

    public NotGateBlock(Settings settings) {
        super(settings);
    }


    protected int getGateResult(World world, BlockPos pos, BlockState state) {
        boolean bottomSignal = getBottomSignal(world, pos, state);

        return bottomSignal?0:1;
    }
    
   



}

