package com.example;

import com.mojang.serialization.MapCodec;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;





public class AndGateBlock extends AbstractRedstoneGateBlock {
    public static final MapCodec<AndGateBlock> CODEC = createCodec(AndGateBlock::new);

    // 
    public static final BooleanProperty LEFT_POWERED = BooleanProperty.of("left_powered_input");
    public static final BooleanProperty RIGHT_POWERED = BooleanProperty.of("right_powered_input");
    public static final BooleanProperty BOTTOM_POWERED = BooleanProperty.of("bottom_powered_input"); 
    
    
    public static final Block AND_BLOCK = register("and", new AndGateBlock(Block.Settings.create().strength(1.0f)));
 
    public static void initialize() {
    }

    private static <T extends Block> T register(String path, T block) {
        Registry.register(Registries.BLOCK, Identifier.of("mymod", path), block);
        Registry.register(Registries.ITEM, Identifier.of("mymod", path), new BlockItem(block, new Item.Settings()));
        return block;
    }



    // 方块的状态管理
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LEFT_POWERED, RIGHT_POWERED, BOTTOM_POWERED);
    }
    
    // 构造函数
    public AndGateBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LEFT_POWERED, false).with(RIGHT_POWERED, false).with(POWERED, false).with(BOTTOM_POWERED, false));
    }

    @Override
    protected MapCodec<? extends AbstractRedstoneGateBlock> getCodec() {
        return CODEC; // 继承自 AbstractRedstoneGateBlock 的编码器
    }

    // 设置方块的朝向
    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = super.getPlacementState(ctx);
		return blockState;
	}

    // 检查左边和右边的输入信号强度
    @Override
    protected boolean hasPower(World world, BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        boolean leftInput = world.getEmittedRedstonePower(pos.offset(facing.rotateYCounterclockwise()), facing.rotateYCounterclockwise()) > 0;
        boolean rightInput = world.getEmittedRedstonePower(pos.offset(facing.rotateYClockwise()), facing.rotateYClockwise()) > 0;
        boolean bottomInput = world.getEmittedRedstonePower(pos.offset(facing), facing) > 0;

        return (leftInput && rightInput) || (bottomInput && leftInput) || (bottomInput && rightInput);
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 0; // 更新延迟
    }

    // 更新信号输出状态
    @Override
    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        boolean newPowered = this.hasPower(world, pos, state);
        
        // 如果状态有变化，更新输出
        if ((Boolean)state.get(POWERED) != newPowered) {
            world.setBlockState(pos, state.with(POWERED, newPowered), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        // 如果方块被激活，返回最大红石输出,仅前方
        Direction facing = state.get(FACING);
    
        // 如果方块正在激活，并且信号输出方向是方块朝向的正前方
        if (state.get(POWERED) && direction == facing) {
            return 15;  // 发出最大红石信号
        }
        return 0; // 否则没有红石输出
    }
    
    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true; // 该方块会发出红石信号
    }
}
