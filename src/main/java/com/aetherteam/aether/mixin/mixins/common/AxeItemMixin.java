package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/UseOnContext;getItemInHand()Lnet/minecraft/world/item/ItemStack;"))
    private void aetherFabric$onLogStripping(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        ToolAbilityListener.doGoldenOakStripping(context.getLevel(), null, context.getItemInHand(), context);
    }
}
