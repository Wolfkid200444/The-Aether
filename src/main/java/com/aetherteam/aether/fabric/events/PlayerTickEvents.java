package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class PlayerTickEvents {

    public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, invokers -> (entity) -> {
        for (var invoker : invokers) invoker.beforeTick(entity);
    });

    public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, invokers -> entity -> {
        for (var invoker : invokers) invoker.afterTick(entity);
    });

    public interface Before {
        void beforeTick(Player entity);
    }

    public interface After {
        void afterTick(Player entity);
    }
}
