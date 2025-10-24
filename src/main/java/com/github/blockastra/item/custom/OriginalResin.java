package com.github.blockastra.item.custom;

import com.github.blockastra.Config;
import com.github.blockastra.component.ModComponents;
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
        if (stack.has(ModComponents.ORIGINAL_RESIN_AMOUNT)) {
            return Component.translatable("item.blockastra.original_resin")
                    .append(" ")
                    .append(stack.get(ModComponents.ORIGINAL_RESIN_AMOUNT) + " / 200");
        } else {
            return super.getName(stack);
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // 如果树脂全部恢复则直接返回「已全部恢复」
        if (stack.has(ModComponents.ORIGINAL_RESIN_AMOUNT) && stack.get(ModComponents.ORIGINAL_RESIN_AMOUNT) >= 200) {
            tooltipComponents.add(Component.translatable("original.blockastra.fully_replenished"));

        } else if (stack.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME)) {
            int replenish_time = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME);
            int amount = stack.get(ModComponents.ORIGINAL_RESIN_AMOUNT);

            // 如果已经有树脂恢复, 就显示无法恢复
            if (replenish_time < 0) {
                tooltipComponents.add(Component.translatable("original.blockastra.stop_replenished"));
            } else { // 否则就是正常恢复, 显示恢复进度
                tooltipComponents.add(Component.translatable("original.blockastra.next_replenished_time")
                        .append(" ")
                        .append(String.valueOf(replenish_time)));
                tooltipComponents.add(Component.translatable("original.blockastra.fully_replenished_time")
                        .append(" ")
                        .append(String.valueOf((199 - amount) * Config.REPLENISH_SPEED.getAsInt() + replenish_time)));
            }
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        long tick = level.getGameTime();

        if (!level.isClientSide()) {
            int speed = Config.REPLENISH_SPEED.getAsInt();

            // 如果没有Components, 则添加Components
            if (!stack.has(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME) || !stack.has(ModComponents.ORIGINAL_RESIN_AMOUNT)) {
                stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                stack.set(ModComponents.ORIGINAL_RESIN_AMOUNT, 0);
            }
            int replenish_time = stack.get(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME);
            int amount = stack.get(ModComponents.ORIGINAL_RESIN_AMOUNT);

            // 将树脂量同步到耐久值
            stack.setDamageValue(200 - Math.min(amount, 200));

            // 每秒更新一次
            if (tick % 20 == 0) {
                UUID uuid = entity.getUUID();

                // 如果玩家身上已经有树脂恢复, 则直接将恢复进度设为-1
                if (!PlayerReplenished.containsKey(uuid) || PlayerReplenished.get(uuid) != tick) {

                    // 如果树脂数量大于200, 则将恢复进度清零
                    if (amount >= 200) {
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, 0);
                    } else if (replenish_time > speed) { // 如果恢复进度大于设置值, 直接将进度设为设置值
                        // 当更改设置时触发
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                    } else if (replenish_time <= 0) { // 如果恢复进度达标, 增加树脂
                        stack.set(ModComponents.ORIGINAL_RESIN_REPLENISH_TIME, speed);
                        stack.set(ModComponents.ORIGINAL_RESIN_AMOUNT, amount + 1);
                    } else { // 否则更新恢复进度
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