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
    public static HashMap<UUID, Long> PlayerRefilled = new HashMap<>();
    public OriginalResin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component component = Component.nullToEmpty(stack.get(ModComponents.ORIGINAL_RESIN_REFILLED).toString());
        tooltipComponents.add(component);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(!level.isClientSide){
            UUID entity_id = entity.getUUID();
            long level_time = level.getGameTime() / 20;

            if(!stack.has(ModComponents.ORIGINAL_RESIN_REFILLED)){
                stack.setDamageValue(200);
                stack.set(ModComponents.ORIGINAL_RESIN_REFILLED, 30);
            }

            if(!PlayerRefilled.containsKey(entity_id) || PlayerRefilled.get(entity_id) < level_time){
                int refill_cooldown = stack.get(ModComponents.ORIGINAL_RESIN_REFILLED);

                if(refill_cooldown <= 0){
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    stack.set(ModComponents.ORIGINAL_RESIN_REFILLED, 30);
                }else{
                    stack.set(ModComponents.ORIGINAL_RESIN_REFILLED, refill_cooldown - 1);
                }

                PlayerRefilled.put(entity_id, level_time);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
