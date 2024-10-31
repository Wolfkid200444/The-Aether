package com.aetherteam.aether.inventory;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.inventory.RecipeBookType;

import java.util.HashMap;
import java.util.List;

public class AetherRecipeBookTypes {
    public static final RecipeBookType ALTAR = RecipeBookType.valueOf("AETHER_ALTAR");
    public static final RecipeBookType FREEZER = RecipeBookType.valueOf("AETHER_FREEZER");
    public static final RecipeBookType INCUBATOR = RecipeBookType.valueOf("AETHER_INCUBATOR");

    static {
        // TODO: [Fabric Port] Need API TO PREVENT CRASHING OR ISSUES WHEN CONNECTING TO VANILLA SERVER
        List<RecipeBookType> AETHER_TYPES = List.of(ALTAR, FREEZER, INCUBATOR);

        var map = new HashMap<>(RecipeBookSettings.TAG_FIELDS);

        for (var aetherType : AETHER_TYPES) {
            var name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, aetherType.name());

            map.put(aetherType, Pair.of("is" + name + "GuiOpen", "is" + name + "FilteringCraftable"));
        }

        RecipeBookSettings.TAG_FIELDS = ImmutableMap.copyOf(map);
    }
}
