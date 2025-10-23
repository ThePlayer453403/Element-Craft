package com.github.mineastra;

import com.github.mineastra.component.ModComponents;
import com.github.mineastra.item.ModCreativeModeTabs;
import com.github.mineastra.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MineastraWonderland.MOD_ID)
public class MineastraWonderland {
    public static final String MOD_ID = "mineastra";
    public static final Logger LOGGER = LogUtils.getLogger();
    public MineastraWonderland(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModComponents.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
