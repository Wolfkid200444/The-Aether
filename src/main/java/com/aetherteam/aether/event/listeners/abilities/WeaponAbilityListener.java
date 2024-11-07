package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aetherfabric.events.CancellableCallback;
import com.aetherteam.aetherfabric.events.ProjectileEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import com.aetherteam.aetherfabric.events.EntityEvents;

public class WeaponAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> WeaponAbilityListener.onDartHurt(entity, source));
        ProjectileEvents.ON_IMPACT.register(WeaponAbilityListener::onArrowHit);
        EntityEvents.STRUCK_BY_LIGHTNING.register(WeaponAbilityListener::onLightningStrike);
        // EntityHurtMixin.aetherFabric$adjustDamageAmount -> WeaponAbilityListener::onEntityDamage
    }

    /**
     * @see AbilityHooks.WeaponHooks#stickDart(LivingEntity, DamageSource)
     */
    public static void onDartHurt(LivingEntity livingEntity, DamageSource damageSource) {
        AbilityHooks.WeaponHooks.stickDart(livingEntity, damageSource);
    }

    /**
     * @see AbilityHooks.WeaponHooks#phoenixArrowHit(HitResult, Projectile)
     */
    public static void onArrowHit(Projectile projectile, HitResult hitResult, CancellableCallback callback) {
        if (!callback.isCanceled()) {
            AbilityHooks.WeaponHooks.phoenixArrowHit(hitResult, projectile);
        }
    }

    /**
     * @see AbilityHooks.WeaponHooks#lightningTracking(Entity, LightningBolt)
     */
    public static void onLightningStrike(Entity entity, LightningBolt lightningBolt, CancellableCallback callback) {
        if (!callback.isCanceled() && AbilityHooks.WeaponHooks.lightningTracking(entity, lightningBolt)) {
            callback.setCanceled(true);
        }
    }

    /**
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceWeaponEffectiveness(LivingEntity, Entity, float)
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceArmorEffectiveness(LivingEntity, Entity, float)
     */
    public static float onEntityDamage(LivingEntity targetEntity, DamageSource damageSource, float damage) {
        Entity sourceEntity = damageSource.getDirectEntity();
        damage = AbilityHooks.WeaponHooks.reduceWeaponEffectiveness(targetEntity, sourceEntity, damage);
        damage = AbilityHooks.WeaponHooks.reduceArmorEffectiveness(targetEntity, sourceEntity, damage);
        return damage;
    }
}
