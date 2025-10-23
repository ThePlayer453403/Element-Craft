package com.github.mineastra.item.custom;

import com.github.mineastra.Config;
import com.github.mineastra.component.ModComponents;
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
    HashMap<UUID, Long> PlayerReplenished = new HashMap<>();

    public OriginalResin(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED)) {
            return Component.translatable("item.mineastra.original_resin")
                    .append(" ")
                    .append(stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED) + " / 200");
        } else {
            return super.getName(stack);
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED) && stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED) >= 200) {
            tooltipComponents.add(Component.translatable("original.mineastra.fully_replenished"));
        } else if (stack.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME)) {
            int replenish_time = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME);
            int replenished = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED);
            if (replenish_time < 0) {
                tooltipComponents.add(Component.translatable("original.mineastra.stop_replenished"));
            } else {
                tooltipComponents.add(Component.translatable("original.mineastra.next_replenished_time")
                        .append(" ")
                        .append(String.valueOf(replenish_time)));
                tooltipComponents.add(Component.translatable("original.mineastra.fully_replenished_time")
                        .append(" ")
                        .append(String.valueOf((199 - replenished) * Config.REPLENISH_SPEED.getAsInt() + replenish_time)));
            }
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        long tick = level.getGameTime();

        if (!level.isClientSide()) {
            int speed = Config.REPLENISH_SPEED.getAsInt();

            if (!stack.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) || !stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED)) {
                stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, 0);
            }
            if (tick % 20 == 0) {
                UUID uuid = entity.getUUID();

                if (!PlayerReplenished.containsKey(uuid) || PlayerReplenished.get(uuid) != tick) {

                    int replenish_time = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME);
                    int replenished = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED);
                    if (replenished >= 200) {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, 0);
                    } else if (replenish_time > speed) {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                    } else if (replenish_time <= 0) {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, replenished + 1);
                    } else {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, replenish_time - 1);
                    }
                    PlayerReplenished.put(uuid, tick);
                } else {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, -1);
                }
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}