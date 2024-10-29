package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.fabric.events.ProjectileEvents;
import com.aetherteam.aether.item.accessories.abilities.ShieldOfRepulsionAccessory;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.jetbrains.annotations.Nullable;

public class AccessoryAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> onBlockBreak(level, player, pos, state));
        // LivingEntityMixin.aether$adjustEntityVisibility -> AccessoryAbilityListener.onTargetSet;
        ProjectileEvents.ON_IMPACT.register(AccessoryAbilityListener::onProjectileImpact);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> onEntityHurt(entity, source));
    }

    /**
     * @see AbilityHooks.AccessoryHooks#damageZaniteRing(LivingEntity, LevelAccessor, BlockState, BlockPos)
     * @see AbilityHooks.AccessoryHooks#damageZanitePendant(LivingEntity, LevelAccessor, BlockState, BlockPos)
     */
    public static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state) {
        AbilityHooks.AccessoryHooks.damageZaniteRing(player, level, state, pos);
        AbilityHooks.AccessoryHooks.damageZanitePendant(player, level, state, pos);
    }

    /**
     * Makes the wearer invisible to other mobs' targeting if wearing an Invisibility Cloak.
     *
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.AccessoryHooks#preventTargeting(LivingEntity, Entity)
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.AccessoryHooks#recentlyAttackedWithInvisibility(LivingEntity, Entity)
     */
    public static void onTargetSet(LivingEntity livingEntity, @Nullable Entity lookingEntity, MutableDouble visibilityValue) {
        if (AbilityHooks.AccessoryHooks.preventTargeting(livingEntity, lookingEntity) && !AbilityHooks.AccessoryHooks.recentlyAttackedWithInvisibility(livingEntity, lookingEntity)) {
            visibilityValue.setValue(0.0);
        }
        if (AbilityHooks.AccessoryHooks.recentlyAttackedWithInvisibility(livingEntity, lookingEntity)) {
            visibilityValue.setValue(1.0);
        }
    }

    /**
     * @see ShieldOfRepulsionAccessory#deflectProjectile(HitResult, Projectile)
     */
    public static void onProjectileImpact(Projectile projectile, HitResult hitResult, MutableBoolean isCancelled) {
        if (ShieldOfRepulsionAccessory.deflectProjectile(hitResult, projectile)) { // Has to take event due to the event being canceled within a lambda and also mid-behavior.
            isCancelled.setValue(true);
        }
    }

    /**
     * @see AbilityHooks.AccessoryHooks#preventMagmaDamage(LivingEntity, DamageSource)
     */
    public static boolean onEntityHurt(LivingEntity livingEntity, DamageSource damageSource) {
        AbilityHooks.AccessoryHooks.setAttack(damageSource);
        if (AbilityHooks.AccessoryHooks.preventMagmaDamage(livingEntity, damageSource)) {
            return false;
        }
        return true;
    }
}
