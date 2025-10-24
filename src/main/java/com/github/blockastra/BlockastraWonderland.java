package com.github.blockastra;

import com.github.blockastra.component.ModComponents;
import com.github.blockastra.item.ModCreativeModeTabs;
import com.github.blockastra.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(BlockastraWonderland.MOD_ID)
public class BlockastraWonderland {
    public static final String MOD_ID = "blockastra";
    public static final Logger LOGGER = LogUtils.getLogger();
    public BlockastraWonderland(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModComponents.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
