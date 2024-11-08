package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aetherfabric.pond.client.KeyMappingExtension;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin implements KeyMappingExtension {
    @Shadow
    private InputConstants.Key key;

    @Override
    public InputConstants.Key aetherFabric$getKey() {
        return this.key;
    }
}
