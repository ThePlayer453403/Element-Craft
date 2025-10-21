package com.github.elementcraft.component;

import com.github.elementcraft.ElementCraft;
import com.mojang.serialization.Codec;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ElementCraft.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ORIGINAL_RESIN_REFILLED = register("original_resin_refilled", builder -> builder.persistent(Codec.INT));
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
    public static void register(IEventBus modEventBus){
        DATA_COMPONENT_TYPE.register(modEventBus);
    }
}
