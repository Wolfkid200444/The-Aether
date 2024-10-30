package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.MenuHooks;
import com.aetherteam.cumulus.api.MenuHelper;
import com.aetherteam.cumulus.client.CumulusClient;
import com.aetherteam.cumulus.fabric.OpeningScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.function.Consumer;

public class MenuListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        OpeningScreenEvents.PRE.register((oldScreen, newScreen) -> MenuListener.onGuiOpenHighest());
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> onGuiInitialize(screen, Screens.getButtons(screen)::add));
    }

    /**
     * @see MenuHooks#prepareCustomMenus(MenuHelper)
     */
    public static void onGuiOpenHighest() {
        MenuHooks.prepareCustomMenus(CumulusClient.MENU_HELPER);
    }

    /**
     * @see MenuHooks#setupToggleWorldButton(Screen)
     * @see MenuHooks#setupMenuSwitchButton(Screen)
     * @see MenuHooks#setupQuickLoadButton(Screen)
     */
    public static void onGuiInitialize(Screen screen, Consumer<AbstractButton> consumer) {
        if (screen instanceof TitleScreen titleScreen) {
            MenuHooks.setCustomSplashText(titleScreen);

            Button toggleWorldButton = MenuHooks.setupToggleWorldButton(screen);
            if (toggleWorldButton != null) {
                consumer.accept(toggleWorldButton);
            }

            Button menuSwitchButton = MenuHooks.setupMenuSwitchButton(screen);
            if (menuSwitchButton != null) {
                consumer.accept(menuSwitchButton);
            }

            Button quickLoadButton = MenuHooks.setupQuickLoadButton(screen);
            if (quickLoadButton != null) {
                consumer.accept(quickLoadButton);
            }
        }
    }
}
