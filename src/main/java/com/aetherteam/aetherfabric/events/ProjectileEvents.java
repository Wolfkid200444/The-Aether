package com.aetherteam.aetherfabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileEvents {

    public static final Event<OnImpact> ON_IMPACT = EventFactory.createArrayBacked(OnImpact.class, invokers -> (projectile, hitResult, callback) -> {
        for (var invoker : invokers) invoker.onImpact(projectile, hitResult, callback);
    });

    public interface OnImpact {
        void onImpact(Projectile projectile, HitResult hitResult, CancellableCallback callback);
    }
}
