package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

public class LivingFallEvent extends Cancellable {

    public static final Event<CallBack> EVENT = EventFactory.createArrayBacked(CallBack.class, invokers -> (entity, event) -> {
        for (var invoker : invokers) invoker.onFall(entity, event);
    });

    private float distance;
    private float damageMultiplier;

    protected LivingFallEvent(float distance, float damageMultiplier) {
        this.setDistance(distance);
        this.setDamageMultiplier(damageMultiplier);
    }

    public static LivingFallEvent invokeEvent(LivingEntity livingEntity, float distance, float damageMultiplier) {
        var event = new LivingFallEvent(distance, damageMultiplier);

        EVENT.invoker().onFall(livingEntity, event);

        return event;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    public interface CallBack {
        void onFall(LivingEntity entity, LivingFallEvent event);
    }
}
