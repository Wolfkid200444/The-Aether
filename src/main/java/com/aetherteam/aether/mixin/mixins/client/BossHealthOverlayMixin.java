package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.client.event.listeners.GuiListener;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    /**
     * Determines whether a vanilla-style boss bar should display or not.
     * @see GuiListener#onRenderBossBar(GuiGraphics, int, int, BossEvent)
     */
    @WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;)V"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V")
    private boolean drawBar(BossHealthOverlay instance, GuiGraphics guiGraphics, int k, int j, BossEvent bossEvent, @Share("allow") LocalBooleanRef allow) {
        boolean renderVanillaBar = !GuiListener.onRenderBossBar(guiGraphics, k, j, bossEvent);
        allow.set(renderVanillaBar);
        return renderVanillaBar;
    }

    /**
     * Determines whether the name for a vanilla-style boss bar should display or not; determined by the shared value set by {@link BossHealthOverlayMixin#drawBar(BossHealthOverlay, GuiGraphics, int, int, BossEvent, LocalBooleanRef)}.
     */
    @WrapWithCondition(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V")
    private boolean drawString(GuiGraphics instance, Font font, Component component, int n, int o, int color, @Share("allow") LocalBooleanRef allow) {
        return allow.get();
    }

    /**
     * Increases the offset increment between boss bars when an Aether-style boss bar is rendered; determined by the shared value set by {@link BossHealthOverlayMixin#drawBar(BossHealthOverlay, GuiGraphics, int, int, BossEvent, LocalBooleanRef)}.
     */
    @ModifyVariable(method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("STORE"), ordinal = 1,
        slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)I"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;guiHeight()I")
        ))
    private int increment(int x, @Share("allow") LocalBooleanRef allow) {
        return allow.get() ? x : x + 13;
    }

    /**
     * Cancels the CustomizeGuiOverlayEvent.BossEventProgress GUI event after the event hook has been called for it.
     * Made as a workaround for Jade's boss bar pushdown.<br>
     * This modifies the assignment of the CustomizeGuiOverlayEvent.BossEventProgress event variable.
     *
     * @param event The original net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress parameter value.
     * @return The modified net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress parameter value.
     */
    // TODO: [Fabric Porting] NEED TO DEAL WITH THIS?
//    @ModifyVariable(at = @At(value = "STORE"), method = "render(Lnet/minecraft/client/gui/GuiGraphics;)V", index = 7)
//    private CustomizeGuiOverlayEvent.BossEventProgress event(CustomizeGuiOverlayEvent.BossEventProgress event) {
//        if (Minecraft.getInstance().level != null &&
//                GuiHooks.BOSS_EVENTS.containsKey(event.getBossEvent().getId()) &&
//                Minecraft.getInstance().level.getEntity(GuiHooks.BOSS_EVENTS.get(event.getBossEvent().getId())) instanceof AetherBossMob<?>) {
//            event.setCanceled(true);
//        }
//        return event;
//    }
}
