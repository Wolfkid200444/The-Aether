package com.aetherteam.aether.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import com.aetherteam.aether.fabric.events.EntityTickEvents;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @WrapOperation(method = "tickNonPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private void aether$entityTickEvents(Entity instance, Operation<Void> original) {
        var shouldCancelEvent = new MutableBoolean(false);

        EntityTickEvents.BEFORE.invoker().beforeTick(instance, shouldCancelEvent);

        if (!shouldCancelEvent.getValue()) {
            original.call(instance);
            EntityTickEvents.AFTER.invoker().afterTick(instance);
        }
    }
}
