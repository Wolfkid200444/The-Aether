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
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if (this.getVariant().getName().equals("aether:skyroot")) {
            return AetherItems.SKYROOT_BOAT.get();
        }

        return original.call();
    }

    @WrapOperation(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/vehicle/Boat;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;", ordinal = 1))
    private ItemEntity aetherFabric$adjustStickSpawn(Boat instance, ItemLike itemLike, Operation<ItemEntity> original) {
        if (instance.getVariant().getName().equals("aether:skyroot")) {
            itemLike = AetherItems.SKYROOT_STICK;
        }

        return original.call(instance, itemLike);
    }

    @Mixin(Boat.Type.class)
    public static abstract class TypeMixin {

//        @Invoker("<init>")
//        public static Boat.Type aetherFabric$invokeNew(String internalName, int ordinal, Block baseBlock, String name) {
//            throw new IllegalStateException("How did this mixin stub get called conc");
//        }
//
//        @Final
//        @Shadow
//        @Mutable
//        private static Boat.Type[] $VALUES;
//
//        @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/vehicle/Boat$Type;$VALUES:[Lnet/minecraft/world/entity/vehicle/Boat$Type;", shift = At.Shift.AFTER, opcode = Opcodes.PUTSTATIC))
//        private static void aetherFabric$addSkyRootBoat(CallbackInfo ci) {
//            var boatTypes = new Boat.Type[$VALUES.length + 1];
//            System.arraycopy($VALUES, 0, boatTypes, 0, $VALUES.length);
//
//            boatTypes[boatTypes.length - 1] = TypeMixin.aetherFabric$invokeNew("AETHER_SKYROOT", Boat.Type.values().length, null, "aether:skyroot");
//
//            $VALUES = boatTypes;
//        }

        @Shadow
        public abstract String getName();

        @WrapMethod(method = "getPlanks")
        private Block aetherFabric$adjustPlanks(Operation<Block> original) {
            if (this.getName().equals("aether:skyroot")) {
                return AetherBlocks.SKYROOT_PLANKS.get();
            }

            return original.call();
        }
    }
}
