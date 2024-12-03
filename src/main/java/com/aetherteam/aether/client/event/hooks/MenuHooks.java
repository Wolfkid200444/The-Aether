package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherMenuUtil;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.CumulusClient;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.Calendar;
import java.util.function.Predicate;

public class MenuHooks {
    /**
     * If the current date is July 22nd, displays the Aether's anniversary splash text.
     *
     * @see com.aetherteam.aether.client.event.listeners.MenuListener#onGuiInitialize(ScreenEvent.Init.Post)
     */
    public static void setCustomSplashText(TitleScreen screen) {
        Predicate<Calendar> condition = (calendar) -> calendar.get(Calendar.MONTH) + 1 == 7 && calendar.get(Calendar.DATE) == 22;
        CumulusClient.MENU_HELPER.setCustomSplash(screen, condition, "Happy anniversary to the Aether!");
    }

    /**
     * Toggles between Aether and Minecraft menus, with the default menus to toggle to determined by
     * {@link AetherConfig.Client#default_minecraft_menu} and {@link AetherConfig.Client#default_aether_menu}.
     *
     * @return The {@link String} for the menu's ID.
     */
    private static String toggleBetweenMenus() {
        if (AetherMenuUtil.isAetherMenu()) {
            return AetherConfig.CLIENT.default_minecraft_menu.get();
        }
        return AetherConfig.CLIENT.default_aether_menu.get();
    }

    /**
     * @return An {@link Integer} offset for buttons, dependent on whether Cumulus' menu switcher button is enabled,
     * as determined by {@link CumulusConfig.Client#enable_menu_api} and {@link CumulusConfig.Client#enable_menu_list_button}.
     */
    private static int getButtonOffset() {
        return CumulusConfig.CLIENT.enable_menu_api.get() && CumulusConfig.CLIENT.enable_menu_list_button.get() ? 62 : 0;
    }
}
