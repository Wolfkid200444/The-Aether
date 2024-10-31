package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class LivingEntityEvents {

    public static final Event<Jumped> ON_JUMP = EventFactory.createArrayBacked(Jumped.class, invokers -> livingEntity -> {
        for (var invoker : invokers) invoker.onJump(livingEntity);
    });

    public static final Event<ShieldBlock> ON_SHIELD_BLOCK = EventFactory.createArrayBacked(ShieldBlock.class, invokers -> (damageSource, callback) -> {
        for (var invoker : invokers) invoker.onBlock(damageSource, callback);
    });

    public static final Event<Fall> ON_FALL = EventFactory.createArrayBacked(Fall.class, invokers -> (entity, helper) -> {
        for (var invoker : invokers) invoker.onFall(entity, helper);
    });

    public static final Event<ExperienceDrop> ON_EXPERIENCE_DROP = EventFactory.createArrayBacked(ExperienceDrop.class, invokers -> (entity, attackingPlayer, helper) -> {
        for (var invoker : invokers) invoker.onExperienceDrop(entity, attackingPlayer, helper);
    });

    public static final Event<OnDrops> ON_DROPS = EventFactory.createArrayBacked(OnDrops.class, invokers -> (entity, source, drops, recentlyHit, callback) -> {
        for (var invoker : invokers) invoker.onDrops(entity, source, drops, recentlyHit, callback);
    });
    public static final Event<ModifyDamage> MODIFY_DAMAGE = EventFactory.createArrayBacked(ModifyDamage.class, callbacks -> (entity, source, originalDamage, newDamage) -> {
        for (var callback : callbacks) callback.modifyDamage(entity, source, originalDamage, newDamage);
    });

    public interface Jumped {
        void onJump(LivingEntity livingEntity);
    }

    public interface ShieldBlock {
        void onBlock(DamageSource damageSource, CancellableCallback callback);
    }

    public interface Fall {
        void onFall(LivingEntity entity, FallHelper event);
    }

    public interface ExperienceDrop {
        void onExperienceDrop(LivingEntity entity, @Nullable Player attackingPlayer, ExperienceDropHelper helper);
    }

    public interface OnDrops {
        void onDrops(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops, boolean recentlyHit, CancellableCallback callback);
    }

    @FunctionalInterface
    public interface ModifyDamage {
        /**
         * Called when a living entity is going to take damage. Can be used to cancel the damage entirely.
         *
         * <p>The amount corresponds to the "incoming" damage amount, before armor and other mitigations have been applied.
         *
         * @param entity         the entity
         * @param source         the source of the damage
         * @param originalDamage the amount of damage that the entity will take (before modifications)
         * @param newDamage      the amount of damage that the entity will be taking
         */
        void modifyDamage(LivingEntity entity, DamageSource source, float originalDamage, MutableFloat newDamage);
    }
}
