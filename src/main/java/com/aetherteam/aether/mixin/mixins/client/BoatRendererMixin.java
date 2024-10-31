package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.entity.SkyrootBoatRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin {

    @WrapOperation(
        method = "render(Lnet/minecraft/world/entity/vehicle/Boat;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;")
    )
    private <V> V aetherFabric$adjustDataGet(Map instance, Object object, Operation<V> original, Boat entity) {
        if (((BoatRenderer)(Object) this) instanceof SkyrootBoatRenderer skyrootBoatRenderer) {
            return (V) skyrootBoatRenderer.getModelWithLocation(entity);
        }

        return original.call(instance, object);
    }
}
