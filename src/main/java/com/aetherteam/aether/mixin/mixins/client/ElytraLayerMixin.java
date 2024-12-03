package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.mixin.AetherMixinHooks;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {
    /**
     * Used to change the elytra texture on an armor stand based on the equipped cape.
     *
     * @param resourceLocation The original {@link ResourceLocation} of the texture for the elytra on this armor stand.
     * @param stack  The elytra {@link ItemStack}.
     * @param entity The {@link LivingEntity} wearing the elytra.
     * @return If the armor stand has an equipped cape, the cape texture, else returns the original texture.
     */
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V")
    private void getElytraTexture(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci, @Local(ordinal = 0) ItemStack stack, @Local(ordinal = 0, argsOnly = true) T entity, @Local() LocalRef<ResourceLocation> resourceLocation) {
        if (entity instanceof ArmorStand armorStand) {
            ItemStack capeStack = AetherMixinHooks.isCapeVisible(armorStand);
            if (!capeStack.isEmpty()) {
                ResourceLocation texture = AetherMixinHooks.getCapeTexture(capeStack);
                if (texture != null) {
                    resourceLocation.set(texture);
                }
            }
        }
    }
}
