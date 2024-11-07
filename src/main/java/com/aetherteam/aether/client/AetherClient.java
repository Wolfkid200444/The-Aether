package com.aetherteam.aether.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.api.AetherMenus;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.client.event.listeners.*;
import com.aetherteam.aether.client.event.listeners.abilities.AccessoryAbilityClientListener;
import com.aetherteam.aether.client.gui.screen.inventory.SunAltarScreen;
import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.client.renderer.AetherOverlays;
import com.aetherteam.aether.client.renderer.AetherRenderers;
import com.aetherteam.aether.client.renderer.level.AetherRenderEffects;
import com.aetherteam.aether.event.listeners.ItemListener;
import com.aetherteam.aether.event.listeners.capability.AetherPlayerListener;
import com.aetherteam.aetherfabric.events.RecipeBookCategoriesHelper;
import com.aetherteam.aether.inventory.menu.AetherMenuTypes;
import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.client.accessor.RecipeBookCategoriesAccessor;
import com.aetherteam.aether.perk.CustomizationsOptions;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.nitrogen.event.listeners.TooltipListeners;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import com.aetherteam.aetherfabric.client.AetherFabricClient;

import java.util.HashMap;

public class AetherClient implements ClientModInitializer {
    private static boolean refreshPacks = false;

    @Override
    public void onInitializeClient() {
        AetherClient.clientSetup();
        //bus.addListener(AetherClient::registerSpectatorShaders);
        //bus.addListener(AetherClient::registerDimensionTransitionScreens);
        AetherClient.loadComplete();

        AetherMenus.MENUS.addEntriesToRegistry();

        AetherClient.eventSetup();

        setupRenderTypes();

        AetherFabricClient.init();
    }

    public static void clientSetup() {
        disableCumulusButton();
        Reflection.initialize(CustomizationsOptions.class);
        AetherRenderers.registerAccessoryRenderers();
        AetherAtlases.registerTreasureChestAtlases();
        AetherAtlases.registerWoodTypeAtlases();
        registerItemModelProperties();
        registerTooltipOverrides();
        registerLoreOverrides();
        autoApplyPacks();

        //--

        AetherPlayerListener.listenClient();
        ItemListener.listen();
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

    public static void registerItemModelProperties() {
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"),
                (stack, world, living, i) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F);
        ItemProperties.register(AetherItems.PHOENIX_BOW.get(), ResourceLocation.withDefaultNamespace("pull"),
                (stack, world, living, i) -> living != null ? living.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration(living) - living.getUseItemRemainingTicks()) / 20.0F : 0.0F);

        ItemProperties.register(AetherItems.CANDY_CANE_SWORD.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("green candy cane sword") ? 1.0F : 0.0F);

        ItemProperties.register(AetherItems.HAMMER_OF_KINGBDOGZ.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "named"), // Easter Egg texture.
                (stack, world, living, i) -> stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb") ? 1.0F : 0.0F);
    }

    public static void registerTooltipOverrides() {
        TooltipListeners.PREDICATES.put(AetherItems.BLUE_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.GOLDEN_GUMMY_SWET, (player, stack, components, context, component) -> {
            if (AetherConfig.SERVER.healing_gummy_swets.get() && component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey() + ".health");
            } else {
                return component;
            }
        });
        TooltipListeners.PREDICATES.put(AetherItems.LIFE_SHARD, (player, stack, components, context, component) -> {
            if (component.getContents() instanceof TranslatableContents contents && contents.getKey().endsWith(".1")) {
                return Component.translatable(contents.getKey(), AetherConfig.SERVER.maximum_life_shards.get());
            } else {
                return component;
            }
        });
    }

    /**
     * Applies a unique lore entry in the Book of Lore for the Hammer of Jeb Easter Egg item texture.
     */
    public static void registerLoreOverrides() {
        LoreBookMenu.addLoreEntryOverride(registryAccess -> stack -> stack.is(AetherItems.HAMMER_OF_KINGBDOGZ.get()) && stack.getHoverName().getString().equalsIgnoreCase("hammer of jeb"), "lore.item.aether.hammer_of_jeb");
        LoreBookMenu.addLoreEntryOverride(registryAccess -> stack -> ItemStack.isSameItemSameComponents(stack, AetherItems.createSwetBannerItemStack(registryAccess.registryOrThrow(Registries.BANNER_PATTERN).asLookup())), "lore.item.aether.swet_banner");
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

    public static void eventSetup() {
        AccessoryAbilityClientListener.listen();
        //AetherPlayerClientListener.listen();
        AudioListener.listen();
        DimensionClientListener.listen();
        GuiListener.listen();
        LevelClientListener.listen();
        MenuListener.listen();
        WorldPreviewListener.listen();

        AetherMenuTypes.registerMenuScreens();

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> AetherColorResolvers.registerBlockColor());
        AetherColorResolvers.registerItemColor();
        AetherKeys.registerKeyMappings();
        AetherRecipeCategories.registerRecipeCategories();
        AetherParticleTypes.registerParticleFactories();
        AetherOverlays.registerOverlays();
        AetherRenderers.registerEntityRenderers();
        AetherRenderers.registerLayerDefinitions();
        AetherRenderers.addEntityLayers();
        //neoBus.addListener(AetherRenderers::bakeModels);
        AetherRenderEffects.registerRenderEffects();

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            var aggregatedCategories = new HashMap<>(RecipeBookCategories.AGGREGATE_CATEGORIES);

            aggregatedCategories.putAll(RecipeBookCategoriesHelper.INSTANCE.aggregateCategories);

            RecipeBookCategoriesAccessor.aetherFabric$setAGGREGATE_CATEGORIES(ImmutableMap.copyOf(aggregatedCategories));
        });
    }

    /**
     * Registers a unique shader for spectating the Sun Spirit, which tints the screen red.
     */
//    public static void registerSpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
//        event.register(AetherEntityTypes.SUN_SPIRIT.get(), ResourceLocation.fromNamespaceAndPath(Aether.MODID, "shaders/post/sun_spirit.json"));
//    }

//    public static void registerDimensionTransitionScreens(RegisterDimensionTransitionScreenEvent event) {
//        event.registerIncomingEffect(AetherDimensions.AETHER_LEVEL, AetherReceivingLevelScreen::new);
//        event.registerOutgoingEffect(AetherDimensions.AETHER_LEVEL, AetherReceivingLevelScreen::new);
//    }

    /**
     * Refreshes resource packs at the end of loading, so that auto-applied packs in {@link AetherClient#autoApplyPacks()} get processed.
     */
    public static void loadComplete() {
        if (refreshPacks) {
            Minecraft.getInstance().reloadResourcePacks();
            refreshPacks = false;
        }
    }

    /**
     * Used to work around a classloading crash on the server.
     */
    public static void setToSunAltarScreen(Component name, int timeScale) {
        Minecraft.getInstance().setScreen(new SunAltarScreen(name, timeScale));
    }

    public static void setupRenderTypes() {
        RenderType cutout = RenderType.cutout();

        BlockRenderLayerMap.INSTANCE.putBlocks(cutout,
            AetherBlocks.BERRY_BUSH.get(),
            AetherBlocks.BERRY_BUSH_STEM.get(),
            AetherBlocks.PURPLE_FLOWER.get(),
            AetherBlocks.WHITE_FLOWER.get(),
            AetherBlocks.SKYROOT_SAPLING.get(),
            AetherBlocks.GOLDEN_OAK_SAPLING.get(),
            AetherBlocks.SKYROOT_DOOR.get(),
            AetherBlocks.SKYROOT_TRAPDOOR.get(),
            AetherBlocks.AMBROSIUM_TORCH.get(),
            AetherBlocks.AMBROSIUM_WALL_TORCH.get());

        RenderType translucent = RenderType.translucent();
        BlockRenderLayerMap.INSTANCE.putBlocks(translucent,
            AetherBlocks.AETHER_PORTAL.get(),
            AetherBlocks.COLD_AERCLOUD.get(),
            AetherBlocks.BLUE_AERCLOUD.get(),
            AetherBlocks.GOLDEN_AERCLOUD.get(),
            AetherBlocks.QUICKSOIL_GLASS.get(),
            AetherBlocks.QUICKSOIL_GLASS_PANE.get());
    }
}
