package net.ngill.copycatdiscs.mixin;

import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.ngill.copycatdiscs.component.CopycatDataComponents;
import net.ngill.copycatdiscs.item.CopycatItems;
import net.ngill.copycatdiscs.mixin.BlockEntityAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin {
	@Shadow
	private ItemStack item;

	@Inject(method = "popOutTheItem", at = @At("HEAD"))
	private void copycatdiscs$popOutTheItem(CallbackInfo ci) {
		ItemStack current = this.item;
		if (current.isEmpty())
			return;

		Integer gen = current.get(CopycatDataComponents.COPY_GENERATION);
		if (gen == null)
			return; // wasn't a copied disc

		if (current.getDamageValue() >= current.getMaxDamage()) {
			ItemStack damaged = new ItemStack(CopycatItems.DAMAGED_DISC.get());
			this.item = damaged.copy();
		}
	}

	@Inject(method = "setTheItem", at = @At("HEAD"))
	private void copycatdiscs$setTheItem(ItemStack stack, CallbackInfo ci) {
		if (stack.isEmpty())
			return;

		Integer gen = stack.get(CopycatDataComponents.COPY_GENERATION);
		if (gen == null)
			return; // wasn't a copied disc

		int newDamage = stack.getDamageValue() + 1;

		Optional<Holder<JukeboxSong>> song = JukeboxSong
				.fromStack(((BlockEntityAccessor) this).copycatdiscs$getLevel().registryAccess(), stack);

		if (song.isPresent()) {
			// play will be triggered later
			stack.setDamageValue(newDamage);
		}
	}
}
