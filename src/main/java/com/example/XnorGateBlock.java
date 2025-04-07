package com.example;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;






public class XnorGateBlock extends AndGateBlock {
    public XnorGateBlock(Settings settings) {
        super(settings);
        //TODO Auto-generated constructor stub
    }

    // 检查左边和右边的输入信号强度
    // @Override
    // public boolean hasPower(World world, BlockPos pos, BlockState state) {
    //     boolean leftInput = getLeftSignal(world, pos, state); // 获取左侧输入信号
    //     boolean rightInput = getRightSignal(world, pos, state); // 获取右侧输入信号

    //     return !(leftInput ^ rightInput);
    // }


    // @Override
    // protected int getReceivedRedstoneSignal(World world, BlockPos pos) {

	// 	// 只检查直接方向
    //     boolean leftInput = getLeftSignal(world, pos, world.getBlockState(pos)); // 获取左侧输入信号
    //     boolean rightInput = getRightSignal(world, pos, world.getBlockState(pos)); // 获取右侧输入信号

	// 	return !(leftInput ^ rightInput)?1:0;
	// }

}
