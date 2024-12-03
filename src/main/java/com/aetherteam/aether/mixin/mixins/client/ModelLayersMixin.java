package com.aetherteam.aether.mixin.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelLayers.class)
public abstract class ModelLayersMixin {

    @WrapOperation(
        method = {"createBoatModelName", "createChestBoatModelName", "createRaftModelName", "createChestRaftModelName"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelLayers;createLocation(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/client/model/geom/ModelLayerLocation;")
    )
    private static ModelLayerLocation aetherFabric$adjustTypeName(String path, String model, Operation<ModelLayerLocation> original, @Local(argsOnly = true) Boat.Type type) {
        var name = type.getName();

        if (name.contains(":")) {
            var prefix = path.replace(name, "");

            return new ModelLayerLocation(ResourceLocation.parse(name).withPrefix(prefix), model);
        }

        return original.call(path, model);
    }
}
