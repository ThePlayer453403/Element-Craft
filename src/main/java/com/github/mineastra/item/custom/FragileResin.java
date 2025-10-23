package com.github.mineastra.item.custom;

import com.github.mineastra.component.ModComponents;
import com.github.mineastra.item.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class FragileResin extends Item {
    public FragileResin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if (context.getPlayer().isCrouching()) {
            System.out.println(context.getPlayer().getInventory().items);
            for (ItemStack item : context.getPlayer().getInventory().items) {
                if (item.is(
                        ModItems.ORIGINAL_RESIN) &&
                        item.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) && item.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) >= 0 &&
                        item.has(ModComponents.ORIGINAL_RESIN_REPLENISHED) && item.get(ModComponents.ORIGINAL_RESIN_REPLENISHED) <= 1940
                ) {
                    item.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, item.get(ModComponents.ORIGINAL_RESIN_REPLENISHED) + 60);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
