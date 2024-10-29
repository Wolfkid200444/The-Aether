package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class EntityEvents {

    public static final Event<StruckByLightningEvent> STRUCK_BY_LIGHTNING = EventFactory.createArrayBacked(StruckByLightningEvent.class, invokers -> (entity, lightning, isCancelled) -> {
        for (var invoker : invokers) invoker.onStrike(entity, lightning, isCancelled);
    });

    public static final Event<BeforeDimensionChange> BEFORE_DIMENSION_CHANGE = EventFactory.createArrayBacked(BeforeDimensionChange.class, invokers -> (entity, targetDimension) -> {
        for (var invoker : invokers) invoker.beforeChange(entity, targetDimension);
    });

    public static final Event<LivingJumped> LIVING_JUMPED = EventFactory.createArrayBacked(LivingJumped.class, invokers -> livingEntity -> {
        for (var invoker : invokers) invoker.onJump(livingEntity);
    });

    public interface StruckByLightningEvent {
        void onStrike(Entity entity, LightningBolt lightning, MutableBoolean isCancelled);
    }

    public interface BeforeDimensionChange {
        void beforeChange(Entity entity, ResourceKey<Level> targetDimension);
    }

    public interface LivingJumped {
        void onJump(LivingEntity livingEntity);
    }
}
