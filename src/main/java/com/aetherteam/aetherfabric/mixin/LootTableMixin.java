package com.aetherteam.aetherfabric.mixin;

import com.aetherteam.aether.loot.modifiers.AetherLootTableModifications;
import com.aetherteam.aetherfabric.pond.LootContextExtension;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin {

    @Inject(
        method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
        at = @At(value = "HEAD")
    )
    private void aetherFabric$addLootTableId1(LootContext context, CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        var lootTableId = context.getLevel().registryAccess()
            .registry(Registries.LOOT_TABLE)
            .flatMap(lootTables -> {
                return lootTables.getResourceKey((LootTable)(Object)this)
                    .map(ResourceKey::location);
            });

        ((LootContextExtension) context).setTableId(lootTableId.orElse(null));
    }

    @Inject(
        method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V",
        at = @At(value = "HEAD")
    )
    private void aetherFabric$addLootTableId2(LootContext context, Consumer<ItemStack> output, CallbackInfo ci) {
        var lootTableId = context.getLevel().registryAccess()
            .registry(Registries.LOOT_TABLE)
            .flatMap(lootTables -> {
                return lootTables.getResourceKey((LootTable)(Object)this)
                    .map(ResourceKey::location);
            });

        ((LootContextExtension) context).setTableId(lootTableId.orElse(null));
    }

    @WrapMethod(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;")
    private ObjectArrayList<ItemStack> aetherFabric$adjustItems(LootContext context, Operation<ObjectArrayList<ItemStack>> original) {
        return AetherLootTableModifications.apply(original.call(context), context);
    }
}
