package com.example;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WireBlockEntity extends BlockEntity implements BlockEntityTicker<WireBlockEntity>{

    public int SIGNAL = 0;
    enum ModTypes {
        NONE, // 无类型
        WIRE,
        GATE,
        SIGNAL_SOURCE // 信号源类型
        
    }
    ModTypes TYPE = ModTypes.NONE; // 方块类型，默认为 NONE
    public WireBlockEntity(BlockPos pos, BlockState state) {
        super(WireBlockEntityTypes.RBLOCK, pos, state);
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state, WireBlockEntity blockEntity) {
        

    }

     // 序列化方块实体
    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        // 将数字的当前值保存到 nbt
        nbt.putInt("signal", SIGNAL);
        nbt.putInt("type", TYPE.ordinal());

        
        super.writeNbt(nbt, registries);
    }


    // 反序列化方块实体
    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
    
        SIGNAL = nbt.getInt("signal");
        TYPE = ModTypes.values()[nbt.getInt("type")];
    }






    protected void update(int signal,Direction direction, ModTypes type) {

        if(this.TYPE == ModTypes.NONE){
            return; // 如果类型为 NONE，则不进行更新相邻方块
        }
        if(this.TYPE == ModTypes.WIRE){
            this.SIGNAL = signal; // 更新信号值
            this.markDirty(); // 标记为脏数据以便保存到NBT
            for(Direction dir : Direction.values()){
                if(dir == direction.getOpposite()) continue; // 跳过当前方向，避免循环调用来源方向
                BlockPos neighborPos = this.getPos().offset(dir); // 获取相邻方块位置
                BlockEntity blockEntity = this.world.getBlockEntity(neighborPos);
                if(blockEntity instanceof WireBlockEntity){
                    if(((WireBlockEntity) blockEntity).SIGNAL == this.SIGNAL)//如果数值相同，则不更新，避免循环调用
                        continue; 
                    ((WireBlockEntity) blockEntity).update(this.SIGNAL,dir,this.TYPE); // 通知相邻方块更新
                }
            }
        }
        else if(this.TYPE == ModTypes.GATE){
            //调用andgate的更新信号
            MyGateBlock gb = (MyGateBlock)(world.getBlockState(pos).getBlock());
            gb.updateGateSignal(world, this.getPos(), world.getBlockState(this.getPos())); // 更新逻辑门的输出信号
        }
        else if(this.TYPE == ModTypes.SIGNAL_SOURCE){
            // 信号源类型不需要更新相邻方块，只需保持当前信号即可
            if(this.SIGNAL != 1){
                this.SIGNAL = 1;
                this.markDirty(); // 确保信号源类型的 WireBlockEntity 更新到 NBT
            }
            for(Direction dir : Direction.values()){
                if(dir == direction.getOpposite()) continue; // 跳过当前方向，避免循环调用来源方向
                BlockPos neighborPos = this.getPos().offset(dir); // 获取相邻方块位置
                BlockEntity blockEntity = this.world.getBlockEntity(neighborPos);
                if(blockEntity instanceof WireBlockEntity){
                    if(((WireBlockEntity) blockEntity).SIGNAL == this.SIGNAL)//如果数值相同，则不更新，避免循环调用
                        continue; 
                    ((WireBlockEntity) blockEntity).update(this.SIGNAL,dir,this.TYPE); // 通知相邻方块更新
                }
            }
        }
    }
}
