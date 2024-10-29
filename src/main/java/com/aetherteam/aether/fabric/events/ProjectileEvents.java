package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class ProjectileEvents {

    public static final Event<OnImpact> ON_IMPACT = EventFactory.createArrayBacked(OnImpact.class, invokers -> (projectile, hitResult, isCancelled) -> {
        for (var invoker : invokers) invoker.onImpact(projectile, hitResult, isCancelled);
    });

    public interface OnImpact {
        void onImpact(Projectile projectile, HitResult hitResult, MutableBoolean isCancelled);
    }
}
