package net.ngill.copycatdiscs.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ngill.copycatdiscs.CopycatDiscs;

public class CopycatRecipes {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister
			.create(Registries.RECIPE_SERIALIZER, CopycatDiscs.MODID);

	public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<CopyDiscRecipe>> COPY_DISC_SERIALIZER = SERIALIZERS
			.register("copy_disc", () -> new SimpleCraftingRecipeSerializer<>(CopyDiscRecipe::new));

	public static void register(IEventBus eventBus) {
		SERIALIZERS.register(eventBus);
	}
}
