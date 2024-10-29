package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.ItemHooks;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemListener {

    public static final ResourceLocation LOWER_TOOLTIP_PHASE = ResourceLocation.fromNamespaceAndPath("aether", "lower_tooltip_phase");

    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        ItemTooltipCallback.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, LOWER_TOOLTIP_PHASE);
        ItemTooltipCallback.EVENT.register(LOWER_TOOLTIP_PHASE, (stack, tooltipContext, tooltipType, lines) -> ItemListener.onTooltipAdd(stack, tooltipType, lines));
    }

    /**
     * @see ItemHooks#addDungeonTooltips(List, ItemStack, TooltipFlag)
     */
    public static void onTooltipAdd(ItemStack itemStack, TooltipFlag tooltipFlag, List<Component> itemTooltips) {
        ItemHooks.addDungeonTooltips(itemTooltips, itemStack, tooltipFlag);
    }
}
