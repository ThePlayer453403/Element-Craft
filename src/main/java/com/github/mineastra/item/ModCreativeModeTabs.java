package com.github.mineastra.item;

import com.github.mineastra.MineastraWonderland;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MineastraWonderland.MOD_ID);
    public static final Supplier<CreativeModeTab> ELEMENT_CRAFT_TAB =
            CREATIVE_MODE_TAB.register("element_craft_tab",
                    () -> CreativeModeTab.builder()
                            .icon(() -> new ItemStack(ModItems.ORIGINAL_RESIN.get()))
                            .displayItems(((itemDisplayParameters, output) -> {
                                output.accept(ModItems.ORIGINAL_RESIN);
                            }))
                            .build());
    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TAB.register(modEventBus);
    }
}
