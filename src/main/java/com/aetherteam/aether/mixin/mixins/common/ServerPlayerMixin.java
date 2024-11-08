package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aetherfabric.pond.EntityExtension;
import com.aetherteam.aetherfabric.events.EntityEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements EntityExtension {
    /**
     * @see AetherPlayerAttachment#removeAerbunny()
     */
    @Inject(at = @At(value = "HEAD"), method = "disconnect()V")
    private void disconnect(CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        serverPlayer.getAttachedOrCreate(AetherDataAttachments.AETHER_PLAYER).removeAerbunny();
    }

    //--

    @Inject(method = "changeDimension", at = @At("HEAD"))
    private void aetherFabric$beforeDimensionChange(DimensionTransition transition, CallbackInfoReturnable<Entity> cir) {
        EntityEvents.BEFORE_DIMENSION_CHANGE.invoker().beforeChange((ServerPlayer) (Object) this, transition.newLevel().dimension());
    }

    @Inject(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private void aetherFabric$captureDroppedStack(ItemStack droppedItem, boolean dropAround, boolean includeThrowerName, CallbackInfoReturnable<ItemEntity> cir, @Local() ItemEntity itemEntity) {
        this.aetherFabric$addCapturedDrops(itemEntity);
    }

    @WrapOperation(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean aetherFabric$preventLevelSpawn(Level instance, Entity entity, Operation<Boolean> original) {
        return (this.aetherFabric$getCapturedDrops() == null) ? original.call(instance, entity) : false;
    }
}
