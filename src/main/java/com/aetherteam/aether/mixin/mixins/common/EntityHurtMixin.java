package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.listeners.abilities.WeaponAbilityListener;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Player.class, LivingEntity.class})
public class EntityHurtMixin {
    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
    private void aetherFabric$adjustDamageAmount(DamageSource damageSource, float damageAmount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef damageAmountRef) {
        damageAmountRef.set(WeaponAbilityListener.onEntityDamage((LivingEntity)(Object)this, damageSource, damageAmountRef.get()));
    }
}
