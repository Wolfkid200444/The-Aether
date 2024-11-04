package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.fabric.events.AddPackFindersEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    /**
     * Used by the world preview system.<br>
     * Unloads the currently loaded world preview level if a new level is being created.
     *
     * @param minecraft The {@link Minecraft} instance.
     * @param screen    The last {@link Screen}.
     * @param ci        The {@link CallbackInfo} for the void method return.
     * @see WorldDisplayHelper#isActive()
     * @see WorldDisplayHelper#stopLevel(Screen)
     */
    @Inject(at = @At(value = "HEAD"), method = "openFresh(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/Screen;)V")
    private static void openFresh(Minecraft minecraft, Screen screen, CallbackInfo ci) {
        if (WorldDisplayHelper.isActive()) {
            WorldDisplayHelper.stopLevel(new GenericMessageScreen(Component.literal("")));
        }
    }

    //--

    @Inject(method = "openFresh", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;createDefaultLoadConfig(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/WorldDataConfiguration;)Lnet/minecraft/server/WorldLoader$InitConfig;"))
    private static void aetherFabric$addClientPackResources(Minecraft minecraft, Screen lastScreen, CallbackInfo ci, @Local() PackRepository repository) {
        AddPackFindersEvent.invokeEvent(PackType.CLIENT_RESOURCES, repository);
    }
}
