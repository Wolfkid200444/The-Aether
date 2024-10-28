package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import com.aetherteam.aether.fabric.events.EntityEvents;
import com.aetherteam.aether.fabric.events.OnProjectileImpact;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class WeaponAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> WeaponAbilityListener.onDartHurt(entity, source));
        OnProjectileImpact.EVENT.register(WeaponAbilityListener::onArrowHit);
        EntityEvents.STRUCK_BY_LIGHTNING.register(WeaponAbilityListener::onLightningStrike);
        bus.addListener(WeaponAbilityListener::onEntityDamage);
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
    public static void onArrowHit(Projectile projectile, HitResult hitResult, MutableBoolean isCancelled) {
        if (!isCancelled.getValue()) {
            AbilityHooks.WeaponHooks.phoenixArrowHit(hitResult, projectile);
        }
    }

    /**
     * @see AbilityHooks.WeaponHooks#lightningTracking(Entity, LightningBolt)
     */
    public static void onLightningStrike(Entity entity, LightningBolt lightningBolt, MutableBoolean isCancelled) {
        if (!isCancelled.getValue() && AbilityHooks.WeaponHooks.lightningTracking(entity, lightningBolt)) {
            isCancelled.setValue(true);
        }
    }

    /**
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceWeaponEffectiveness(LivingEntity, Entity, float)
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#reduceArmorEffectiveness(LivingEntity, Entity, float)
     */
    public static void onEntityDamage(LivingDamageEvent.Pre event) {
        LivingEntity targetEntity = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity sourceEntity = damageSource.getDirectEntity();
        event.setNewDamage(AbilityHooks.WeaponHooks.reduceWeaponEffectiveness(targetEntity, sourceEntity, event.getNewDamage()));
        event.setNewDamage(AbilityHooks.WeaponHooks.reduceArmorEffectiveness(targetEntity, sourceEntity, event.getNewDamage()));
    }
}
