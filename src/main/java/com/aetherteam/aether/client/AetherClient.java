package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.event.listeners.*;
import com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener;
import com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener;
import com.aetherteam.aether.client.gui.screen.inventory.*;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.client.renderer.level.AetherRenderEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.cumulus.CumulusConfig;
import com.google.common.reflect.Reflection;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.RegisterEntitySpectatorShadersCallback;
import io.github.fabricators_of_create.porting_lib.config.ConfigEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.Map;

public class AetherClient implements ClientModInitializer {
    private static boolean refreshPacks = false;

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> disableCumulusButton());
        Reflection.initialize(CustomizationsOptions.class);
        AetherAtlases.registerTreasureChestAtlases();
        AetherAtlases.registerWoodTypeAtlases();
        AetherParticleTypes.registerParticleFactories();
        AetherKeys.registerKeyMappings();
        AetherRenderEffects.registerRenderEffects();
        AetherRenderers.init();
        registerGuiFactories();
        registerItemModelProperties();
        registerLoreOverrides();
        autoApplyPacks();

        AetherPacketHandler.INSTANCE.initClientListener();

        initEvents();

        RegisterEntitySpectatorShadersCallback.EVENT.register(AetherClient::registerSpectatorShaders);
        ClientLifecycleEvents.CLIENT_STARTED.register(this::loadComplete);
    }

    public void initEvents() {
        AccessoryAbilityClientListener.init();
        AetherPlayerClientListener.init();
        DimensionClientListener.init();
        WorldPreviewListener.init();
        LevelClientListener.init();
        HandRenderListener.init();
        AudioListener.init();
        MenuListener.init();
        GuiListener.init();

        AetherOverlays.registerOverlays();
    }

    /**
     * Disables the Cumulus menu switcher button, since Aether has its own for theme toggling.
     */
    public static void disableCumulusButton() {
        if (AetherConfig.CLIENT.should_disable_cumulus_button.get()) {
            CumulusConfig.CLIENT.enable_menu_list_button.set(false);
            CumulusConfig.CLIENT.enable_menu_list_button.save();
            AetherConfig.CLIENT.should_disable_cumulus_button.set(false);
            AetherConfig.CLIENT.should_disable_cumulus_button.save();
        }
    }

    public static void registerGuiFactories() {
        MenuScreens.register(AetherMenuTypes.ACCESSORIES.get(), AccessoriesScreen::new);
        MenuScreens.register(AetherMenuTypes.BOOK_OF_LORE.get(), LoreBookScreen::new);
        MenuScreens.register(AetherMenuTypes.ALTAR.get(), AltarScreen::new);
        MenuScreens.register(AetherMenuTypes.FREEZER.get(), FreezerScreen::new);
        MenuScreens.register(AetherMenuTypes.INCUBATOR.get(), IncubatorScreen::new);
    }

    public static void registerItemModelProperties() {
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pulling"),
                (stack, world, living, i) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), new ResourceLocation("pull"),
                (stack, world, living, i) -> living != null ? living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F : 0.0F);

        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), new ResourceLocation(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemProperties.register(AetherItems.HAMMER_OF_KINGBDOGZ.get(), new ResourceLocation(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    /**
     * Applies a unique lore entry in the Book of Lore for the Hammer of Jeb Easter Egg item texture.
     */
    public static void registerLoreOverrides() {
        LoreBookMenu.addLoreEntryOverride(stack -> stack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()) && stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb"), "lore.item.aether.hammer_of_jeb");
    }

    /**
     * Auto applies resource packs on load.
     */
    public static void autoApplyPacks() {
        if (FabricLoader.getInstance().isModLoaded("tipsmod")) {
            if (AetherConfig.CLIENT.enable_trivia.get()) {
                Minecraft.getInstance().getResourcePackRepository().addPack("builtin/aether_tips");
            } else {
                Minecraft.getInstance().getResourcePackRepository().removePack("builtin/aether_tips");
            }
            refreshPacks = true;
        }
    }

    /**
     * Registers a unique shader for spectating the Sun Spirit, which tints the screen red.
     */
    public static void registerSpectatorShaders(Map<EntityType<?>, ResourceLocation> shaders) {
        shaders.put(AetherEntityTypes.SUN_SPIRIT.get(), new ResourceLocation(Aether.MODID, "shaders/post/sun_spirit.json"));
    }

    /**
     * Refreshes resource packs at the end of loading, so that auto-applied packs in {@link AetherClient#autoApplyPacks()} get processed.
     */
    public void loadComplete(Minecraft client) {
        if (refreshPacks) {
            Minecraft.getInstance().reloadResourcePacks();
            refreshPacks = false;
        }
    }

    /**
     * Used to work around a classloading crash on the server.
     */
    public static void setToSunAltarScreen(Component name) {
        Minecraft.getInstance().setScreen(new SunAltarScreen(name));
    }
}
