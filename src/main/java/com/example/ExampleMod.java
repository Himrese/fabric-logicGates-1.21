package com.example;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "MyMod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



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
		
	}
}