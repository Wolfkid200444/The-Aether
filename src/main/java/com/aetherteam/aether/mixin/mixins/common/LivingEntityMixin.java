package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener;
import com.aetherteam.aether.fabric.ExtraServerLivingEntityEvents;
import com.aetherteam.aether.fabric.events.LivingFallEvent;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    /**
     * Handles vertical swimming for Phoenix Armor in lava without being affected by the upwards speed debuff from lava.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see PhoenixArmor#boostVerticalLavaSwimming(LivingEntity)
     */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidJumpThreshold()D", shift = At.Shift.AFTER), method = "travel(Lnet/minecraft/world/phys/Vec3;)V")
    private void travel(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        PhoenixArmor.boostVerticalLavaSwimming(livingEntity);
    }

    @WrapWithCondition(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"))
    private boolean hurt(LivingEntity instance, double strength, double x, double z, @Local(argsOnly = true) DamageSource source) {
        return (!(instance instanceof ValkyrieQueen) || !(source.getDirectEntity() instanceof Projectile));
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
    private void aetherFabric$adjustDamageAmount(DamageSource damageSource, float damageAmount, CallbackInfo ci, @Local(argsOnly = true) LocalFloatRef damageAmountRef) {
        var newDamage = new MutableFloat(damageAmount);

        ExtraServerLivingEntityEvents.MODIFY_DAMAGE.invoker().modifyDamage((LivingEntity) (Object) this, damageSource, damageAmount, newDamage);

        damageAmountRef.set(newDamage.getValue());
    }

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double aetherFabric$adjustEntityVisibility(double original, @Local(argsOnly = true) @Nullable Entity lookingEntity) {
        var value = new MutableDouble(original);

        AccessoryAbilityListener.onTargetSet((LivingEntity) (Object) this, lookingEntity, value);

        return value.getValue();
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void aetherFabric$adjustFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true, ordinal = 0) LocalFloatRef fallDistanceRef, @Local(argsOnly = true, ordinal = 1) LocalFloatRef multiplierRef) {
        var event = LivingFallEvent.invokeEvent((LivingEntity) (Object) this, fallDistance, multiplier);

        if (event.isCanceled()) cir.setReturnValue(false);

        fallDistanceRef.set(event.getDistance());
        multiplierRef.set(event.getDamageMultiplier());
    }
}
