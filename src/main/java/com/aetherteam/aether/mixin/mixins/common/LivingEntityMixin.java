package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.aether.event.listeners.abilities.AccessoryAbilityListener;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.aetherteam.aether.item.combat.loot.CloudStaffItem;
import com.aetherteam.aetherfabric.events.CancellableCallbackImpl;
import com.aetherteam.aetherfabric.events.ExperienceDropHelper;
import com.aetherteam.aetherfabric.events.FallHelper;
import com.aetherteam.aetherfabric.events.LivingEntityEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    @Nullable
    protected Player lastHurtByPlayer;

    @Shadow
    protected int lastHurtByPlayerTime;

    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand hand);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

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

    //--

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double aetherFabric$adjustEntityVisibility(double original, @Local(argsOnly = true) @Nullable Entity lookingEntity) {
        var value = new MutableDouble(original);

        AccessoryAbilityListener.onTargetSet((LivingEntity) (Object) this, lookingEntity, value);

        return value.getValue();
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void aetherFabric$adjustFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true, ordinal = 0) LocalFloatRef fallDistanceRef, @Local(argsOnly = true, ordinal = 1) LocalFloatRef multiplierRef) {
        var helper = new FallHelper(fallDistance, multiplier);

        LivingEntityEvents.ON_FALL.invoker().onFall((LivingEntity) (Object) this, helper);

        if (helper.isCanceled()) cir.setReturnValue(false);

        fallDistanceRef.set(helper.getDistance());
        multiplierRef.set(helper.getDamageMultiplier());
    }

    @WrapOperation(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z"))
    private boolean aetherFabric$checkIfBlock(LivingEntity instance, DamageSource damageSource, Operation<Boolean> original) {
        var callback = new CancellableCallbackImpl(!original.call(instance, damageSource));

        LivingEntityEvents.ON_SHIELD_BLOCK.invoker().onBlock(damageSource, callback);

        return !callback.isCanceled();
    }

    @WrapOperation(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
    private void aetherFabric$adjustExperienceAmount(ServerLevel level, Vec3 pos, int amount, Operation<Void> original) {
        var helper = new ExperienceDropHelper(amount);

        LivingEntityEvents.ON_EXPERIENCE_DROP.invoker().onExperienceDrop((LivingEntity) (Object) this, this.lastHurtByPlayer, helper);

        original.call(level, pos, helper.getFinalExperienceAmount());
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void aetherFabric$onSwing(InteractionHand hand, boolean updateSelf, CallbackInfo ci) {
        var stack = this.getItemInHand(hand);

        if (!stack.isEmpty() && stack.getItem() instanceof CloudStaffItem staffItem) {
            staffItem.onEntitySwing(stack, (LivingEntity)(Object) this, hand);
        }
    }

    @WrapOperation(
        method = {"addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", "forceAddEffect"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;canBeAffected(Lnet/minecraft/world/effect/MobEffectInstance;)Z")
    )
    private boolean aetherFabric$adjustIfEffectIsApplicable(LivingEntity instance, MobEffectInstance effectInstance, Operation<Boolean> original) {
        var result = LivingEntityEvents.ON_EFFECT.invoker().onEffect(instance, effectInstance, TriState.DEFAULT);

        return result != TriState.DEFAULT ? result.get() : original.call(instance, effectInstance);
    }

    @WrapOperation(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float aetherFabric$adjustDamageAmount(LivingEntity instance, DamageSource damageSource, float damageAmount, Operation<Float> original) {
        var newDamage = new MutableFloat(original.call(instance, damageSource, damageAmount));

        LivingEntityEvents.ON_DAMAGE.invoker().modifyDamage((LivingEntity) (Object) this, damageSource, damageAmount, newDamage);

        return newDamage.getValue();
    }
}
