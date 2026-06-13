package net.ngill.copycatdiscs.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ngill.copycatdiscs.CopycatDiscs;

public class CopycatDataComponents {
	public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister
			.createDataComponents(Registries.DATA_COMPONENT_TYPE, CopycatDiscs.MODID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COPY_GENERATION = DATA_COMPONENT_TYPES
			.registerComponentType("copy_generation",
					builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

	public static void register(IEventBus eventBus) {
		DATA_COMPONENT_TYPES.register(eventBus);
	}
}
