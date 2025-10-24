package com.github.blockastra.item;

import com.github.blockastra.BlockastraWonderland;
import com.github.blockastra.item.custom.FragileResin;
import com.github.blockastra.item.custom.OriginalResin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockastraWonderland.MOD_ID);
    public static final DeferredItem<Item> ORIGINAL_RESIN = ITEMS.register("original_resin", () -> new OriginalResin(new Item.Properties().durability(200).setNoRepair().rarity(Rarity.RARE)));
    public static final DeferredItem<Item> FRAGILE_RESIN = ITEMS.register("fragile_resin", () -> new FragileResin(new Item.Properties().rarity(Rarity.EPIC)));
    public static final DeferredItem<Item> CONDENSED_RESIN = ITEMS.register("condensed_resin", () -> new Item(new Item.Properties().stacksTo(5).rarity(Rarity.EPIC)));
    public static void register(IEventBus modEventBus){ITEMS.register(modEventBus);}
}
