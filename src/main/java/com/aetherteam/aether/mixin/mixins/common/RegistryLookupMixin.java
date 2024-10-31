package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.fabric.pond.RegistryLookupExtension;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HolderLookup.RegistryLookup.class)
public interface RegistryLookupMixin<T> extends RegistryLookupExtension<T> {

    @Mixin(HolderLookup.RegistryLookup.Delegate.class)
    public interface DelegateMixin<T> extends RegistryLookupExtension<T> {
        @Shadow
        HolderLookup.RegistryLookup<T> parent();

        @Override
        default <T1> @Nullable T1 getData(DataMapType<T, T1> type, ResourceKey<T> key) {
            return this.parent().getData(type, key);
        }
    }
}
