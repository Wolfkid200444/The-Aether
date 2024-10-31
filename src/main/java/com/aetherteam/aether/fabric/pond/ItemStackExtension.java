package com.aetherteam.aether.fabric.pond;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public interface ItemStackExtension {
    default int getEnchantmentLevel(Holder<Enchantment> enchantment) {
        return throwUnimplementedException();
    }

    static <T> T throwUnimplementedException() {
        throw new IllegalStateException("Injected Interface method not implement!");
    }
}
