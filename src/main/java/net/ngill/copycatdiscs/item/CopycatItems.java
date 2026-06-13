package net.ngill.copycatdiscs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ngill.copycatdiscs.CopycatDiscs;

public class CopycatItems {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CopycatDiscs.MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, CopycatDiscs.MODID);

	public static final DeferredItem<Item> DISC_SUBSTRATE = ITEMS.registerSimpleItem("disc_substrate",
			new Item.Properties());
	public static final DeferredItem<Item> BLANK_DISC = ITEMS.registerSimpleItem("blank_disc", new Item.Properties());
	public static final DeferredItem<Item> COPIED_DISC = ITEMS.register("copied_disc",
			() -> new CopiedDiscItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<Item> DAMAGED_DISC = ITEMS.registerSimpleItem("damaged_disc",
			new Item.Properties());

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COPYCAT_DISCS_TAB = CREATIVE_MODE_TABS
			.register("copycatdiscs_tab",
					() -> CreativeModeTab.builder().title(Component.translatable("itemGroup.copycatdiscs"))
							.icon(() -> COPIED_DISC.get().getDefaultInstance()).displayItems((parameters, output) -> {
								output.accept(DISC_SUBSTRATE.get());
								output.accept(BLANK_DISC.get());
							}).build());

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
