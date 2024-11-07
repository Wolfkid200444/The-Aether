package com.aetherteam.aether.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.MimicModel;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import noobanidus.mods.lootr.fabric.config.ConfigManager;

import java.util.Calendar;

public class MimicRenderer extends MobRenderer<Mimic, MimicModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/mimic/normal.png");
    private static final ResourceLocation XMAS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/mimic/christmas.png");
    private static final ResourceLocation LOOTR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/mimic/lootr.png");
    private static final ResourceLocation OLD_LOOTR_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/mimic/old_lootr.png");

    private boolean isChristmas;

    public MimicRenderer(EntityRendererProvider.Context renderer) {
        super(renderer, new MimicModel(renderer.bakeLayer(AetherModelLayers.MIMIC)), 1.0F);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) { // Time period when chests display as presents in Vanilla.
            this.isChristmas = true;
        }
    }

    /**
     * If the Lootr mod is installed or if it is Christmas, Mimics will have a custom texture.
     *
     * @param Mimic The {@link Mimic} entity.
     * @return The texture {@link ResourceLocation}.
     */
    @Override
    public ResourceLocation getTextureLocation(Mimic Mimic) {
        if (FabricLoader.getInstance().isModLoaded("lootr")) {
            if (!ConfigManager.isVanillaTextures()) {
                if (ConfigManager.isNewTextures()) {
                    return LOOTR_TEXTURE;
                } else {
                    return OLD_LOOTR_TEXTURE;
                }
            }
        }
        return this.isChristmas ? XMAS_TEXTURE : TEXTURE;
    }
}
