package com.aetherteam.aether.attachment;

import com.aetherteam.aether.Aether;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AetherDataAttachments {
    public static final AttachmentType<AetherPlayerAttachment> AETHER_PLAYER = register("aether_player", (builder) -> builder.initializer(AetherPlayerAttachment::new).persistent(AetherPlayerAttachment.CODEC).copyOnDeath());
    public static final AttachmentType<MobAccessoryAttachment> MOB_ACCESSORY = register("mob_accessory", (builder) -> builder.initializer(MobAccessoryAttachment::new).persistent(MobAccessoryAttachment.CODEC).copyOnDeath());
    public static final AttachmentType<PhoenixArrowAttachment> PHOENIX_ARROW = register("phoenix_arrow", (builder) -> builder.initializer(PhoenixArrowAttachment::new).persistent(PhoenixArrowAttachment.CODEC));
    public static final AttachmentType<LightningTrackerAttachment> LIGHTNING_TRACKER = register("lightning_tracker", (builder) -> builder.initializer(LightningTrackerAttachment::new).persistent(LightningTrackerAttachment.CODEC));
    public static final AttachmentType<DroppedItemAttachment> DROPPED_ITEM = register("dropped_item", (builder) -> builder.initializer(DroppedItemAttachment::new).persistent(DroppedItemAttachment.CODEC));
    public static final AttachmentType<AetherTimeAttachment> AETHER_TIME = register("aether_time", (builder) -> builder.initializer(AetherTimeAttachment::new).persistent(AetherTimeAttachment.CODEC));

    private static <T> AttachmentType<T> register(String path, Consumer<AttachmentRegistry.Builder<T>> builderConsumer) {
        var builder = AttachmentRegistry.<T>builder();

        builderConsumer.accept(builder);

        return builder.buildAndRegister(ResourceLocation.fromNamespaceAndPath(Aether.MODID, path));
    }

    public static void init() {}
}
