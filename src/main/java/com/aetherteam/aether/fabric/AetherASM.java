package com.aetherteam.aether.fabric;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import me.shedaniel.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class AetherASM implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.enumBuilder(mapC("class_5421"))
            .addEnum("AETHER_ALTAR")
            .addEnum("AETHER_FREEZER")
            .addEnum("AETHER_INCUBATOR")
            .build();

        ClassTinkerers.enumBuilder(mapC("class_1814"), int.class, String.class, ("L" + mapC("class_124") + ";")) // Rarity // ChatFormatting
            .addEnum("AETHER_LOOT", () -> new Object[] { 4, "aether:loot", ChatFormatting.GREEN })
            .build();

        ClassTinkerers.enumBuilder(mapC("class_314"), ("[L" + mapC("class_1799") + ";"))
            .addEnum("AETHER_ENCHANTING_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
            .addEnum("AETHER_ENCHANTING_FOOD", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ENCHANTED_BERRY.get()) }})
            .addEnum("AETHER_ENCHANTING_BLOCKS", () -> new Object[] { new ItemStack[] {new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()) }})
            .addEnum("AETHER_ENCHANTING_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get()) }})
            .addEnum("AETHER_ENCHANTING_REPAIR", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ZANITE_PICKAXE.get()) }})

            .addEnum("AETHER_FREEZABLE_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
            .addEnum("AETHER_FREEZABLE_BLOCKS", () -> new Object[] { new ItemStack[] {new ItemStack(AetherBlocks.BLUE_AERCLOUD.get()) }})
            .addEnum("AETHER_FREEZABLE_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.ICE_RING.get()) }})

            .addEnum("AETHER_INCUBATION_SEARCH", () -> new Object[] { new ItemStack[] {new ItemStack(Items.COMPASS) }})
            .addEnum("AETHER_INCUBATION_MISC", () -> new Object[] { new ItemStack[] {new ItemStack(AetherItems.BLUE_MOA_EGG.get()) }})
            .build();

        ClassTinkerers.enumBuilder(mapC("class_1311"), String.class, int.class, boolean.class, boolean.class, int.class) // MobCategory
            .addEnum("AETHER_AETHER_SURFACE_MONSTER", "aether:aether_surface_monster", 15, false, false, 128)
            .addEnum("AETHER_AETHER_DARKNESS_MONSTER", "aether:aether_darkness_monster", 5, false, false, 128)
            .addEnum("AETHER_AETHER_SKY_MONSTER", "aether:aether_sky_monster", 4, false, false, 128)
            .addEnum("AETHER_AETHER_AERWHALE", "aether:aether_aerwhale", 1, true, false, 128)
            .build(); // MobCategory

        ClassTinkerers.enumBuilder(mapC("class_1690$class_1692"), ("L" + mapC("class_2248") + ";"), String.class)
            .addEnum("AETHER_SKYROOT", () -> new Object[] { null, "aether:skyroot"})
            .build();
    }

    public static String mapC(String intermediaryName) {
        return FabricLoader.getInstance().getMappingResolver()
            .mapClassName("intermediary", "net.minecraft." + intermediaryName)
            .replace('.', '/');
    }
}
