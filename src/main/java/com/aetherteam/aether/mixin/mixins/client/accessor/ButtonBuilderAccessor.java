package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Button.Builder.class)
public interface ButtonBuilderAccessor {

    @Accessor("message") Component aetherFabric$message();
    @Accessor("onPress") Button.OnPress aetherFabric$onPress();
    @Accessor("tooltip") @Nullable Tooltip aetherFabric$tooltip();
    @Accessor("x") int aetherFabric$x();
    @Accessor("y") int aetherFabric$y();
    @Accessor("width") int aetherFabric$width();
    @Accessor("height") int aetherFabric$height();
    @Accessor("createNarration") Button.CreateNarration aetherFabric$createNarration();
}
