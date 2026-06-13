package net.ngill.copycatdiscs.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.ngill.copycatdiscs.component.CopycatDataComponents;
import net.ngill.copycatdiscs.item.CopycatItems;

public class CopyDiscRecipe extends CustomRecipe {

	public CopyDiscRecipe(CraftingBookCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		int nBlank = 0;

		ItemStack discStack = ItemStack.EMPTY;

		for (ItemStack stack : input.items()) {
			if (stack.isEmpty())
				continue;

			if (stack.get(DataComponents.JUKEBOX_PLAYABLE) != null) {
				discStack = stack.copy();
			}

			if (stack.is(CopycatItems.BLANK_DISC.get())) {
				nBlank++;
			}
		}

		Integer gen = discStack.getOrDefault(CopycatDataComponents.COPY_GENERATION, 0);
		if (gen >= 8)
			return false; // don't copy discs more that 7 gen old

		return discStack.getCount() == 1 && nBlank == 1;
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
		ItemStack discStack = ItemStack.EMPTY;
		boolean hasBlank = false;

		for (ItemStack stack : input.items()) {
			if (stack.isEmpty())
				continue;

			if (stack.get(DataComponents.JUKEBOX_PLAYABLE) != null) {
				discStack = stack.copy();
			}

			if (stack.is(CopycatItems.BLANK_DISC.get())) {
				hasBlank = true;
			}
		}

		if (discStack.isEmpty() || !hasBlank) {
			return ItemStack.EMPTY;
		}

		ItemStack result = new ItemStack(CopycatItems.COPIED_DISC.get());

		JukeboxPlayable play = discStack.get(DataComponents.JUKEBOX_PLAYABLE);
		if (play != null) {
			result.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(play.song(), true));
		}

		Integer gen = discStack.getOrDefault(CopycatDataComponents.COPY_GENERATION, 0);
		Integer newGen = ((gen >= 8) ? 8 : (gen + 1));
		result.set(CopycatDataComponents.COPY_GENERATION, newGen);
		result.set(DataComponents.MAX_DAMAGE, (int) Math.pow(2, 8 - newGen));

		return result;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
		NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);

			if (stack.get(DataComponents.JUKEBOX_PLAYABLE) != null) {
				remaining.set(i, stack.copy());
			}
		}

		return remaining;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return CopycatRecipes.COPY_DISC_SERIALIZER.get();
	}
}
