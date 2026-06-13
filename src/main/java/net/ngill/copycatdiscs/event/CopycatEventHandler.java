package net.ngill.copycatdiscs.event;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.ngill.copycatdiscs.component.CopycatDataComponents;

public class CopycatEventHandler {
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		Integer gen = stack.get(CopycatDataComponents.COPY_GENERATION);
		if (gen == null)
			return;

		List<Component> tooltip = event.getToolTip();
		tooltip.add(Component.translatable("tooltip.copycatdiscs.generation", gen).withStyle(ChatFormatting.GRAY));
	}
}
