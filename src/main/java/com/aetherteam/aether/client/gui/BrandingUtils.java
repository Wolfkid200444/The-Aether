package com.aetherteam.aether.client.gui;

import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.config.ModMenuConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class BrandingUtils {

    public static void renderLeft(GuiGraphics guiGraphics, TitleScreen titleScreen, Font font, int roundedFadeAmount) {
        renderPositioned(guiGraphics, titleScreen, font, roundedFadeAmount, getMessage(), 2, titleScreen.height - 10);
    }

    public static void renderRight(GuiGraphics guiGraphics, TitleScreen titleScreen, Font font, int roundedFadeAmount) {
        var copyRightWidget = (PlainTextButton) titleScreen.renderables.stream()
            .filter(renderable -> renderable instanceof PlainTextButton btn && btn.getMessage().equals(Component.translatable("title.credits")))
            .findFirst()
            .orElse(null);

        if (copyRightWidget == null) return;

        var message = getMessage();

        var y = copyRightWidget.getY() - font.lineHeight - 2;
        var x = titleScreen.width - font.width(message) - 2;

        renderPositioned(guiGraphics, titleScreen, font, roundedFadeAmount, message, x, y);
    }

    public static void renderPositioned(GuiGraphics guiGraphics, TitleScreen titleScreen, Font font, int roundedFadeAmount, String string, int x, int y) {
        if ((roundedFadeAmount & -67108864) != 0) {
            guiGraphics.drawString(font, string, x, y, 16777215 | roundedFadeAmount);
        }
    }

    private static String getMessage() {
        String string = "Minecraft " + SharedConstants.getCurrentVersion().getName();
        if (Minecraft.getInstance().isDemo()) {
            string = string + " Demo";
        } else {
            string = string + ("release".equalsIgnoreCase(Minecraft.getInstance().getVersionType()) ? "" : "/" + Minecraft.getInstance().getVersionType());
        }

        if (Minecraft.checkModStatus().shouldReportAsModified()) {
            string = string + I18n.get("menu.modded");
        }

        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
            string = modMenuAdjustString(string);
        }

        return string;
    }

    private static String modMenuAdjustString(String string) {
        if (ModMenuConfig.MODIFY_TITLE_SCREEN.getValue() && ModMenuConfig.MOD_COUNT_LOCATION.getValue()
            .isOnTitleScreen()) {
            String count = ModMenu.getDisplayedModCount();
            String specificKey = "modmenu.mods." + count;
            String replacementKey = I18n.exists(specificKey) ? specificKey : "modmenu.mods.n";
            if (ModMenuConfig.EASTER_EGGS.getValue() && I18n.exists(specificKey + ".secret")) {
                replacementKey = specificKey + ".secret";
            }
            return string.replace(I18n.get(I18n.get("menu.modded")), I18n.get(replacementKey, count));
        }
        return string;
    }
}
