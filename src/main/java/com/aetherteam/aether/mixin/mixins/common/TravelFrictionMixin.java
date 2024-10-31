package com.aetherteam.aether.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {FlyingMob.class, LivingEntity.class})
public abstract class TravelFrictionMixin {
    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F"))
    private float aetherFabric$adjustFriction(Block instance, Operation<Float> original) {
        var livingEntity = (Entity) (Object) this;

        var blockPos = livingEntity.getBlockPosBelowThatAffectsMyMovement();
        var level = livingEntity.level();

        var betterFriction = level.getBlockState(blockPos).getFriction(level, blockPos, livingEntity);

        return betterFriction != null ? betterFriction : original.call(instance);
    }
}
