package com.aetherteam.aether.integration.jei.categories.fuel;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDataMaps;
import com.aetherteam.nitrogen.integration.jei.categories.fuel.FuelRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class AetherFuelRecipeMaker {
    private AetherFuelRecipeMaker() {
    }

    public static List<FuelRecipe> getFuelRecipes() {
        List<FuelRecipe> fuelRecipes = new ArrayList<>();
        BuiltInRegistries.ITEM.aetherFabric$getDataMap(AetherDataMaps.ALTAR_FUEL).forEach((item, fuel) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.get(item))), fuel.burnTime(), AetherBlocks.ALTAR.get())));
        BuiltInRegistries.ITEM.aetherFabric$getDataMap(AetherDataMaps.FREEZER_FUEL).forEach((item, fuel) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.get(item))), fuel.burnTime(), AetherBlocks.FREEZER.get())));
        BuiltInRegistries.ITEM.aetherFabric$getDataMap(AetherDataMaps.INCUBATOR_FUEL).forEach((item, fuel) -> fuelRecipes.add(new FuelRecipe(List.of(new ItemStack(BuiltInRegistries.ITEM.get(item))), fuel.burnTime(), AetherBlocks.INCUBATOR.get())));
        return fuelRecipes;
    }
}
