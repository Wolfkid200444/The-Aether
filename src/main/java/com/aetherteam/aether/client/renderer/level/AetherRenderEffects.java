package com.aetherteam.aether.client.renderer.level;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;

public class AetherRenderEffects {
    public final static AetherSkyRenderEffects AETHER_EFFECTS = new AetherSkyRenderEffects();

    /**
     * @see AetherClient#eventSetup()
     */
    public static void registerRenderEffects() {
        DimensionRenderingRegistry.registerDimensionEffects(AetherDimensions.AETHER_DIMENSION_TYPE.location(), AETHER_EFFECTS);
        DimensionRenderingRegistry.registerCloudRenderer(AetherDimensions.AETHER_LEVEL, AETHER_EFFECTS.CLOUD_RENDERER);
        DimensionRenderingRegistry.registerSkyRenderer(AetherDimensions.AETHER_LEVEL, AETHER_EFFECTS.SKY_RENDERER);
    }
}
