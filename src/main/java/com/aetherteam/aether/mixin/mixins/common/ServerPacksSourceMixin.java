package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.fabric.events.AddPackFindersEvent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPacksSource.class)
public abstract class ServerPacksSourceMixin {

    @Inject(method = "createPackRepository(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At("RETURN"))
    private static void aetherFabric$addServerPackResources(LevelStorageSource.LevelStorageAccess level, CallbackInfoReturnable<PackRepository> cir) {
        AddPackFindersEvent.invokeEvent(PackType.CLIENT_RESOURCES, cir.getReturnValue());
    }
}
