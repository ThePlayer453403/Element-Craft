package com.github.elementcraft.item.custom;

import com.github.elementcraft.component.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OriginalResin extends Item {
    public static HashMap<UUID, Long> PlayerReplenished = new HashMap<>();
    int replenishedTime = 8;

    public OriginalResin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.translatable("text.element.next_replenished")
                        .append(" ")
                        .append(String.valueOf(stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED)))
        );
        tooltipComponents.add(
                Component.translatable("text.element.fully_replenished")
                        .append(" ")
                        .append(String.valueOf(stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED)))
        );
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        long tick = level.getGameTime();

        if (!level.isClientSide() && tick % 20 == 0) {
            UUID uuid = entity.getUUID();

            if (!stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED)) {
                stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, replenishedTime);
                stack.setDamageValue(200);
            }
            if (!PlayerReplenished.containsKey(uuid) || PlayerReplenished.get(uuid) != tick) {
                int replenished = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED);

                if (replenished <= 0) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    if (stack.getDamageValue() != 0) {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, replenishedTime);
                    }
                } else {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, replenished - 1);
                }
                PlayerReplenished.put(uuid, tick);
            } else {
                stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, -1);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
