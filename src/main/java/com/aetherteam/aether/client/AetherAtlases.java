package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherWoodTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AetherAtlases {
    public static Material TREASURE_CHEST_MATERIAL;
    public static Material TREASURE_CHEST_LEFT_MATERIAL;
    public static Material TREASURE_CHEST_RIGHT_MATERIAL;

    /**
     * Need to register these static values here from {@link AetherClient#clientSetup()},
     * otherwise they'll be loaded too early from static initialization in the field.
     */
    public static void registerTreasureChestAtlases() {
        TREASURE_CHEST_MATERIAL = getChestMaterial("treasure_chest");
        TREASURE_CHEST_LEFT_MATERIAL = getChestMaterial("treasure_chest_left");
        TREASURE_CHEST_RIGHT_MATERIAL = getChestMaterial("treasure_chest_right");
    }

    public static void registerWoodTypeAtlases() {
        Sheets.SIGN_MATERIALS.put(AetherWoodTypes.SKYROOT, createSignMaterial(AetherWoodTypes.SKYROOT));
        Sheets.HANGING_SIGN_MATERIALS.put(AetherWoodTypes.SKYROOT, createHangingSignMaterial(AetherWoodTypes.SKYROOT));
    }

    public static Material getChestMaterial(String chestName) {
        return new Material(Sheets.CHEST_SHEET, ResourceLocation.fromNamespaceAndPath(Aether.MODID, "entity/tiles/chest/" + chestName));
    }

    private static Material createSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, ResourceLocation.withDefaultNamespace("entity/signs/" + woodType.name()));
    }

    private static Material createHangingSignMaterial(WoodType woodType) {
        return new Material(Sheets.SIGN_SHEET, ResourceLocation.withDefaultNamespace("entity/signs/hanging/" + woodType.name()));
    }

}
