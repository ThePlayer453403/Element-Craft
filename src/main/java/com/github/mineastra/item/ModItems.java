package com.github.mineastra.item;

import com.github.mineastra.MineastraWonderland;
import com.github.mineastra.item.custom.FragileResin;
import com.github.mineastra.item.custom.OriginalResin;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MineastraWonderland.MOD_ID);
    public static final DeferredItem<Item> ORIGINAL_RESIN = ITEMS.register("original_resin", () -> new OriginalResin(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FRAGILE_RESIN = ITEMS.register("fragile_resin", () -> new FragileResin(new Item.Properties()));
    public static final DeferredItem<Item> CONDENSED_RESIN = ITEMS.register("condensed_resin", () -> new Item(new Item.Properties().stacksTo(5)));
    public static void register(IEventBus modEventBus){ITEMS.register(modEventBus);}
}
