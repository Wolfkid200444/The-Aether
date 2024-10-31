package com.aetherteam.aether.mixin.mixins.common;

import net.minecraft.core.HolderLookup;
import com.aetherteam.aether.fabric.pond.IHolderLookupProviderExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HolderLookup.Provider.class)
public interface HolderLookupProviderMixin extends IHolderLookupProviderExtension {
}
