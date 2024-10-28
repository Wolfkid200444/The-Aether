package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class EntityEvents {

    public static final Event<StruckByLightningEvent> STRUCK_BY_LIGHTNING = EventFactory.createArrayBacked(StruckByLightningEvent.class, invokers -> (entity, lightning, isCancelled) -> {
        for (var invoker : invokers) invoker.onStrike(entity, lightning, isCancelled);
    });

    public interface StruckByLightningEvent {
        void onStrike(Entity entity, LightningBolt lightning, MutableBoolean isCancelled);
    }
}
