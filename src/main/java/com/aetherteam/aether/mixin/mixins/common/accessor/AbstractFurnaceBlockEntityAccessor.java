package com.aetherteam.aether.mixin.mixins.common.accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface AbstractFurnaceBlockEntityAccessor {
    @Accessor("quickCheck")
    RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> aether$getQuickCheck();

    @Invoker
    static boolean callCanBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> stacks, int stackSize) {
        throw new AssertionError();
    }

    @Accessor("litTime")
    int aether$getLitTime();

    @Accessor("litTime")
    void aether$setLitTime(int litTime);

    @Accessor("litDuration")
    void aether$setLitDuration(int litDuration);

    @Accessor("cookingProgress")
    int aether$getCookingProgress();

    @Accessor("cookingProgress")
    void aether$setCookingProgress(int cookingProgress);

    @Accessor("cookingTotalTime")
    int aether$getCookingTotalTime();

    @Accessor("cookingTotalTime")
    void aether$setCookingTotalTime(int cookingTotalTime);

    @Invoker
    boolean callIsLit();

    @Invoker
    static int callGetTotalCookTime(Level level, AbstractFurnaceBlockEntity blockEntity) {
        throw new AssertionError();
    }

    @Accessor("items")
    NonNullList<ItemStack> aether$getItems();

    @Invoker
    int callGetBurnDuration(ItemStack pFuel);
}
