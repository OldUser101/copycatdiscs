package net.ngill.copycatdiscs;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.ngill.copycatdiscs.component.CopycatDataComponents;
import net.ngill.copycatdiscs.event.CopycatEventHandler;
import net.ngill.copycatdiscs.item.CopycatItems;
import net.ngill.copycatdiscs.recipe.CopycatRecipes;

@Mod(CopycatDiscs.MODID)
public class CopycatDiscs {
	public static final String MODID = "copycatdiscs";

	public CopycatDiscs(IEventBus modEventBus, ModContainer modContainer) {
		CopycatItems.register(modEventBus);
		CopycatRecipes.register(modEventBus);
		CopycatDataComponents.register(modEventBus);
		NeoForge.EVENT_BUS.register(new CopycatEventHandler());
	}
}
