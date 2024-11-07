package com.aetherteam.aetherfabric.pond;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;

public interface ItemStackExtension {
    default int getEnchantmentLevel(Holder<Enchantment> enchantment) {
        return throwUnimplementedException();
    }

    default Set<Object2IntMap.Entry<Holder<Enchantment>>> getAllEnchantments(HolderLookup.RegistryLookup<Enchantment> registry) {
        return throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
