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


	public static final Block RBLOCK = new CustomRedstoneWireBlock(Block.Settings.create().strength(4.0f));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello EE Course!");
		AndGateBlock.initialize();
		OrGateBlock.initialize();
		NotGateBlock.initialize();
		BufferGateBlock.initialize();
		NandGateBlock.initialize();
		NorGateBlock.initialize();
		XorGateBlock.initialize();
		XnorGateBlock.initialize();
		Registry.register(Registries.BLOCK, Identifier.of("mymod", "wire"), RBLOCK);
		Registry.register(Registries.ITEM, Identifier.of("mymod", "wire"), new BlockItem(RBLOCK, new Item.Settings()));
		
	}
}