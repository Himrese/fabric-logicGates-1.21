package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "MyMod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	//

	
	public static final Block RBLOCK = new WireBlock(Block.Settings.create().strength(4.0f));
	public static final Block ONSIGNALBLOCK = new OnSignal(Block.Settings.create().strength(1.0f));
	public static final Block OFFSIGNALBLOCK = new OffSignal(Block.Settings.create().strength(1.0f));

	public static final Block AND_BLOCK = new AndGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block NAND_BLOCK = new NandGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block BUFFER_BLOCK = new BufferGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block NOT_BLOCK = new NotGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block OR_BLOCK = new OrGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block XOR_BLOCK = new XorGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block NOR_BLOCK = new NorGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block XNOR_BLOCK = new XnorGateBlock(Block.Settings.create().strength(1.0f));
	public static final Block SIGNAL_BLOCK = new SignalSource(Block.Settings.create().strength(1.0f));
	public static final Block DECODER_INPUT_BLOCK = new DecoderInput(Block.Settings.create().strength(1.0f));
	public static final Block DECODER_OUTPUT_BLOCK = new DecoderOutput(Block.Settings.create().strength(1.0f));



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello EE Course!");
		
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "wire"), RBLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "onsignal"), ONSIGNALBLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "offsignal"), OFFSIGNALBLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "and"), AND_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "nand"), NAND_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "buffer"), BUFFER_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "not"), NOT_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "or"), OR_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "xor"), XOR_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "nor"), NOR_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "xnor"), XNOR_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "signal"), SIGNAL_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "decoder_input"),DECODER_INPUT_BLOCK);
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "decoder_output"),DECODER_OUTPUT_BLOCK);


		Registry.register(Registries.ITEM, Identifier.of("mymod", "wire"), new BlockItem(RBLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "onsignal"), new BlockItem(ONSIGNALBLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "offsignal"), new BlockItem(OFFSIGNALBLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "and"), new BlockItem(AND_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "nand"), new BlockItem(NAND_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "buffer"), new BlockItem(BUFFER_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "not"), new BlockItem(NOT_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "or"), new BlockItem(OR_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "xor"), new BlockItem(XOR_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "nor"), new BlockItem(NOR_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "xnor"), new BlockItem(XNOR_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "signal"), new BlockItem(SIGNAL_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "decoder_input"), new BlockItem(DECODER_INPUT_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, Identifier.of("mymod", "decoder_output"), new BlockItem(DECODER_OUTPUT_BLOCK, new Item.Settings()));

		WireBlockEntityTypes.initialize(); // 初始化 WireBlockEntityTypes，注册 BlockEntityType




		
	}
}