package com.aetherteam.aether.fabric;

import com.aetherteam.aether.mixin.mixins.client.accessor.ButtonBuilderAccessor;
import net.minecraft.client.gui.components.Button;

public class BuilderMadeButton extends Button {

    protected BuilderMadeButton(Button.Builder builder) {
        super(
            ((ButtonBuilderAccessor) builder).aetherFabric$x(),
            ((ButtonBuilderAccessor) builder).aetherFabric$y(),
            ((ButtonBuilderAccessor) builder).aetherFabric$width(),
            ((ButtonBuilderAccessor) builder).aetherFabric$height(),
            ((ButtonBuilderAccessor) builder).aetherFabric$message(),
            ((ButtonBuilderAccessor) builder).aetherFabric$onPress(),
            ((ButtonBuilderAccessor) builder).aetherFabric$createNarration());
    }
}
