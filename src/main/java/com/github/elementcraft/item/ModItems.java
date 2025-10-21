package com.github.elementcraft.item;

import com.github.elementcraft.ElementCraft;
import com.github.elementcraft.item.custom.OriginalResin;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElementCraft.MOD_ID);
    public static final DeferredItem<Item> ORIGINAL_RESIN = ITEMS.register("original_resin", () -> new OriginalResin(new Item.Properties().durability(200)));
    public static void register(IEventBus modEventBus){ITEMS.register(modEventBus);}
}
