package com.aetherteam.aether.fabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.mutable.MutableBoolean;

public interface OnProjectileImpact {

    Event<OnProjectileImpact> EVENT = EventFactory.createArrayBacked(OnProjectileImpact.class, invokers -> (projectile, hitResult, isCancelled) -> {
        for (var invoker : invokers) invoker.onImpact(projectile, hitResult, isCancelled);
    });

    void onImpact(Projectile projectile, HitResult hitResult, MutableBoolean isCancelled);
}
