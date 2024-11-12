package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.IRegistryExtension;
import com.aetherteam.aetherfabric.registries.DeferredHolder;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends IRegistryExtension<T> {
    @Inject(method = "safeCastToReference", at = @At(value = "HEAD"))
    private void aetherFabric$adjustCastTarget(CallbackInfoReturnable<DataResult<Holder.Reference<T>>> cir, @Local(argsOnly = true) LocalRef<Holder<T>> holderRef) {
        if (holderRef.get() instanceof DeferredHolder<?, ?> deferredHolder) {
            holderRef.set((Holder<T>) deferredHolder.aetherFabric$getDelegate());
        }
    }
}
