package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.MenuHooks;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

public class MenuListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> onGuiInitialize(screen));
    }

    /**
     * @see MenuHooks#setCustomSplashText(TitleScreen)
     */
    public static void onGuiInitialize(Screen screen) {
        if (screen instanceof TitleScreen titleScreen) {
            MenuHooks.setCustomSplashText(titleScreen);
        }
    }
}
