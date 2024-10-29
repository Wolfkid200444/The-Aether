package com.aetherteam.aether.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import com.aetherteam.aether.fabric.events.EntityEvents;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;thunderHit(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LightningBolt;)V"))
    private void aetherFabric$onEntityStruckEvent(Entity instance, ServerLevel level, LightningBolt lightning, Operation<Void> original) {
        var isCancelled = new MutableBoolean(false);

        EntityEvents.STRUCK_BY_LIGHTNING.invoker().onStrike(instance, lightning, isCancelled);

        if (!isCancelled.getValue()) original.call(instance, level, lightning);
    }
}
