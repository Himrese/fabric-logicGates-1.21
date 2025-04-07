package com.example;


import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WireBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("mymod", path), blockEntityType);
    }

    // 如果多个方块（例如：RBLOCK、RBLOCK2、RBLOCK3）都使用同一个 WireBlockEntity，
    // 则在这里都要传入到 Builder.create 方法中。
    public static final BlockEntityType<WireBlockEntity> RBLOCK = register(
        "wireblock", // 注册的路径
        // 对于 1.21.2 之前的版本，请使用 BlockEntityType.Builder。
        BlockEntityType.Builder.create(
            WireBlockEntity::new,
            ExampleMod.RBLOCK,
            ExampleMod.AND_BLOCK,
            ExampleMod.NAND_BLOCK,
            ExampleMod.ONSIGNALBLOCK,
            ExampleMod.OFFSIGNALBLOCK
        ).build() // 使用 WireBlockEntity 的构造函数和对应的注册方块们
    );

    public static void initialize() {
    }
}
