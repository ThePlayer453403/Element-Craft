package com.github.blockastra.item.custom;

import com.github.blockastra.component.ModComponents;
import com.github.blockastra.item.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class FragileResin extends Item {
    public FragileResin(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player.isCrouching()) {
            for (ItemStack item : player.getInventory().items) {
                if (item.is(
                        ModItems.ORIGINAL_RESIN) &&
                        item.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) && item.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) >= 0 &&
                        item.has(ModComponents.ORIGINAL_RESIN_AMOUNT) && item.get(ModComponents.ORIGINAL_RESIN_AMOUNT) <= 1940
                ) {
                    item.set(ModComponents.ORIGINAL_RESIN_AMOUNT, item.get(ModComponents.ORIGINAL_RESIN_AMOUNT) + 60);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}
