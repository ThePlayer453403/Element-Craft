package com.github.blockastra.item;

import com.github.blockastra.BlockastraWonderland;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlockastraWonderland.MOD_ID);
    public static final Supplier<CreativeModeTab> ELEMENT_CRAFT_TAB =
            CREATIVE_MODE_TAB.register("element_craft_tab",
                    () -> CreativeModeTab.builder()
                            .icon(() -> new ItemStack(ModItems.ORIGINAL_RESIN.get()))
                            .title(Component.translatable("creativetab.blockastra.tab"))
                            .displayItems(((itemDisplayParameters, output) -> {
                                output.accept(ModItems.ORIGINAL_RESIN);
                                output.accept(ModItems.FRAGILE_RESIN);
                                output.accept(ModItems.CONDENSED_RESIN);
                            }))
                            .build());
    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TAB.register(modEventBus);
    }
}
