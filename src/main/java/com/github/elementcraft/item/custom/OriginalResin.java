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
    public static HashMap<UUID, Long> PlayerRegenerated = new HashMap<>();
    int regeneratedTick = 600;

    public OriginalResin(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component component = Component.nullToEmpty(stack.get(ModComponents.ORIGINAL_RESIN_REGENERATES).toString());
        tooltipComponents.add(component);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide){
            UUID uuid = entity.getUUID();
            long tick = level.getGameTime();

            if(!stack.has(ModComponents.ORIGINAL_RESIN_REGENERATES)){
                stack.setDamageValue(200);
                stack.set(ModComponents.ORIGINAL_RESIN_REGENERATES, 0);
            }

            if(!PlayerRegenerated.containsKey(uuid) || PlayerRegenerated.get(uuid) < tick){
                int regenerates = stack.get(ModComponents.ORIGINAL_RESIN_REGENERATES);

                if(regenerates >= regeneratedTick){
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    stack.set(ModComponents.ORIGINAL_RESIN_REGENERATES, 0);
                    PlayerRegenerated.put(uuid, tick);
                }else{
                    stack.set(ModComponents.ORIGINAL_RESIN_REGENERATES, regenerates + 1);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
