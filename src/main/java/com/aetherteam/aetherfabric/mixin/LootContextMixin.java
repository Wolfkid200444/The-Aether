package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aetherfabric.pond.LootContextExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootContext.class)
public abstract class LootContextMixin implements LootContextExtension {

    @Nullable
    @Unique
    private ResourceLocation aetherFabric$tableId = null;

    @Override
    public @Nullable ResourceLocation getTableId() {
        return this.aetherFabric$tableId;
    }

    @Override
    public void setTableId(ResourceLocation tableId) {
        this.aetherFabric$tableId = tableId;
    }
}
