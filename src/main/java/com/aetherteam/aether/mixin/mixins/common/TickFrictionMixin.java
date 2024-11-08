package com.aetherteam.aether.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {ExperienceOrb.class, ItemEntity.class})
public abstract class TickFrictionMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F"))
    private float aetherFabric$adjustFriction(Block instance, Operation<Float> original) {
        var livingEntity = (Entity) (Object) this;

        var blockPos = livingEntity.getBlockPosBelowThatAffectsMyMovement();
        var level = livingEntity.level();

        var betterFriction = level.getBlockState(blockPos).aetherFabric$getFriction(level, blockPos, livingEntity);

        return betterFriction != null ? betterFriction : original.call(instance);
    }
}
