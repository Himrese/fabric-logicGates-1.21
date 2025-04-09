package com.example;

import java.util.ArrayList;

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
        SIGNAL_SOURCE,
        DECODER_INPUT,
        DECODER_OUTPUT
    }
    
    ArrayList<BlockPos> decoderOutputList = new ArrayList<>();

    public ModTypes TYPE = ModTypes.NONE; // 方块类型，默认为 NONE
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
            return;
        }
        if(this.TYPE == ModTypes.WIRE || this.TYPE == ModTypes.DECODER_OUTPUT){
            this.SIGNAL = signal; // 更新信号值
            this.markDirty(); // 标记为脏数据以便保存到NBT
            for(Direction dir : Direction.values()){
                if(dir == direction.getOpposite()) continue; // 跳过当前方向，避免循环调用来源方向
                BlockPos neighborPos = this.getPos().offset(dir); // 获取相邻方块位置
                BlockEntity blockEntity = this.world.getBlockEntity(neighborPos);
                if(blockEntity instanceof WireBlockEntity){
                    if((((WireBlockEntity) blockEntity).SIGNAL == this.SIGNAL) && ((WireBlockEntity) blockEntity).TYPE==ModTypes.WIRE)//如果数值相同，则不更新，避免循环调用
                        continue; 
                    ((WireBlockEntity) blockEntity).update(this.SIGNAL,dir,this.TYPE); // 通知相邻方块更新
                }
            }
        }
        else if(this.TYPE == ModTypes.GATE){
            //调用gate的更新信号
            MyGateBlock gb = (MyGateBlock)(world.getBlockState(this.getPos()).getBlock());
            gb.updateGateSignal(world, this.getPos(), world.getBlockState(this.getPos())); // 更新逻辑门的输出信号
        }
        else if(this.TYPE == ModTypes.SIGNAL_SOURCE){
            for(Direction dir : Direction.values()){
                if((direction != null) && (dir == direction.getOpposite())) continue; // 跳过当前方向，避免循环调用来源方向
                BlockPos neighborPos = this.getPos().offset(dir); // 获取相邻方块位置
                BlockEntity blockEntity = this.world.getBlockEntity(neighborPos);
                if(blockEntity instanceof WireBlockEntity){
                    if(((WireBlockEntity) blockEntity).SIGNAL == this.SIGNAL)//如果数值相同，则不更新，避免循环调用
                        continue; 
                    ((WireBlockEntity) blockEntity).update(this.SIGNAL,dir,this.TYPE); // 通知相邻方块更新
                }
            }
        }else if(this.TYPE == ModTypes.DECODER_INPUT){
            DecoderInput db = (DecoderInput)(world.getBlockState(this.getPos()).getBlock());
            this.decoderOutputList = db.checkOutputState(world, pos, world.getBlockState(this.getPos()).get(DecoderInput.FACING));

            this.SIGNAL = signal;
            this.markDirty();
            
            if(this.decoderOutputList!=null && this.decoderOutputList.size() == 8){
                for(BlockPos blockPos : this.decoderOutputList){
                    if(world.getBlockEntity(blockPos) instanceof WireBlockEntity blockEntity){
                        //signal to binary
                        int binaryValue = this.SIGNAL & 0xFF; // 取低8位作为二进制值
                        int outputSignal = (binaryValue >> (7 - (this.decoderOutputList.indexOf(blockPos)))) & 1; // 获取对应的信号值
                        Direction facing = world.getBlockState(this.getPos()).get(DecoderInput.FACING);
                        blockEntity.update(outputSignal,facing.getOpposite(),ModTypes.DECODER_INPUT);
                    }

                }
            }
        }


    }
}
