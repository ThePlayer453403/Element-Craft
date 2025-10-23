package com.github.mineastra.item.custom;

import com.github.mineastra.Config;
import com.github.mineastra.component.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.getDamageValue() <= 0) {
            tooltipComponents.add(Component.translatable("original.mineastra.fully_replenished"));
        } else if (stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED)) {
            int replenished = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED);
            if (replenished < 0) {
                tooltipComponents.add(Component.translatable("original.mineastra.stop_replenished"));
            } else {
                tooltipComponents.add(Component.nullToEmpty(200 - stack.getDamageValue() + " / 200"));
                tooltipComponents.add(Component.translatable("original.mineastra.next_replenished_time")
                        .append(" ")
                        .append(String.valueOf(replenished)));
                tooltipComponents.add(Component.translatable("original.mineastra.fully_replenished_time")
                        .append(" ")
                        .append(String.valueOf((stack.getDamageValue() - 1) * Config.REPLENISH_SPEED.getAsInt() + replenished)));
            }
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        long tick = level.getGameTime();

        if (!level.isClientSide() && tick % 20 == 0) {
            UUID uuid = entity.getUUID();
            int speed = Config.REPLENISH_SPEED.getAsInt();

            if (!PlayerReplenished.containsKey(uuid) || PlayerReplenished.get(uuid) != tick){
                if (!stack.has(ModComponents.ORIGINAL_RESIN_REPLENISHED)) {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, speed);
                    stack.setDamageValue(200);
                }

                int replenished = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISHED);
                if (stack.getDamageValue() <= 0) {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, 0);
                } else if (replenished > speed) {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, speed);
                } else if (replenished <= 0) {
                    stack.set(ModComponents.ORIGINAL_RESIN_REPLENISHED, speed);
                    stack.setDamageValue(stack.getDamageValue() - 1);
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