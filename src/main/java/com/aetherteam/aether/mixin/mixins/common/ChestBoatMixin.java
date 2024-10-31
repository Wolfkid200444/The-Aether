package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.item.AetherItems;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChestBoat.class)
public class ChestBoatMixin {

    @WrapMethod(method = "getDropItem")
    private Item aetherFabric$adjustBoatItemDrop(Operation<Item> original) {
        if (((Boat)(Object) this).getVariant().getName().equals("AETHER_SKYROOT")) {
            return AetherItems.SKYROOT_CHEST_BOAT.get();
        }

        return original.call();
    }
}
