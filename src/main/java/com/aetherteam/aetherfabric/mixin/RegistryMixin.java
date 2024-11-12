package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.IRegistryExtension;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Registry.class)
public interface RegistryMixin<T> extends IRegistryExtension<T> {
}
