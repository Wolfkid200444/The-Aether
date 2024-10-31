package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class BoatMixin {

    @Shadow
    public abstract Boat.Type getVariant();

    @WrapOperation(method = "getGroundFriction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F"))
    private float aetherFabric$adjustFriction(Block instance, Operation<Float> original, @Local() BlockPos.MutableBlockPos blockPos, @Local() BlockState blockState) {
        var livingEntity = (Entity) (Object) this;

        var level = livingEntity.level();

        var betterFriction = blockState.getFriction(level, blockPos, livingEntity);

        return betterFriction != null ? betterFriction : original.call(instance);
    }

    @WrapMethod(method = "getDropItem")
    private Item aetherFabric$adjustBoatItemDrop(Operation<Item> original) {
        if (this.getVariant().getName().equals("AETHER_SKYROOT")) {
            return AetherItems.SKYROOT_BOAT.get();
        }

        return original.call();
    }

    @WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 1))
    private ItemEntity aetherFabric$adjustStickSpawn(Boat instance, ItemLike itemLike, Operation<ItemEntity> original) {
        if (instance.getVariant().getName().equals("AETHER_SKYROOT")) {
            itemLike = AetherItems.SKYROOT_STICK;
        }

        return original.call(instance, itemLike);
    }

    @Mixin(Boat.Type.class)
    public static abstract class TypeMixin {
        @Shadow
        public abstract String getName();

        @WrapMethod(method = "getPlanks")
        private Block aetherFabric$adjustPlanks(Operation<Block> original) {
            if (this.getName().equals("AETHER_SKYROOT")) {
                return AetherBlocks.SKYROOT_PLANKS.get();
            }

            return original.call();
        }
    }
}
