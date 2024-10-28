package net.neoforged.neoforge.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.extensions.IHolderExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Reference.class)
public class HolderReferenceMixin<T> implements IHolderExtension<T> {

    @Shadow
    @Nullable
    private ResourceKey<T> key;

    @Shadow
    @Final
    private HolderOwner<T> owner;

    @Override
    public HolderLookup.@Nullable RegistryLookup<T> unwrapLookup() {
        return this.owner instanceof HolderLookup.RegistryLookup<T> rl ? rl : null;
    }

    @Override
    @Nullable
    public ResourceKey<T> getKey() {
        return this.key;
    }
}
