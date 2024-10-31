package net.neoforged.neoforge.mixin;

import com.aetherteam.aether.fabric.pond.IWithData;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import com.aetherteam.aether.fabric.pond.IHolderExtension;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Holder.Reference.class)
public abstract class HolderReferenceMixin<T> implements IHolderExtension<T>, IWithData<T> {

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

    @Override
    public <T1> @Nullable T1 getData(DataMapType<T, T1> type) {
        if (owner instanceof HolderLookup.RegistryLookup<T> lookup) {
            return lookup.getData(type, key);
        }
        return null;
    }
}
