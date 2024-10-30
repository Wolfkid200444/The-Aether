package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
}
