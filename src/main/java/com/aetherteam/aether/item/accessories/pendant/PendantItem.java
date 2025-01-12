package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.inventory.AetherAccessorySlots;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import com.aetherteam.aether.item.accessories.SlotIdentifierHolder;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class PendantItem extends AccessoryItem implements SlotIdentifierHolder {
    protected ResourceLocation PENDANT_LOCATION;

    public PendantItem(String pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        this(ResourceLocation.fromNamespaceAndPath(Aether.MODID, pendantLocation), pendantSound, properties);
    }

    public PendantItem(ResourceLocation pendantLocation, Holder<SoundEvent> pendantSound, Properties properties) {
        super(pendantSound, properties);
        this.setRenderTexture(pendantLocation.getNamespace(), pendantLocation.getPath());
    }

    public void setRenderTexture(String modId, String registryName) {
        this.PENDANT_LOCATION = ResourceLocation.fromNamespaceAndPath(modId, "textures/models/accessory/pendant/" + registryName + "_accessory.png");
    }

    public ResourceLocation getPendantTexture() {
        return this.PENDANT_LOCATION;
    }

    /**
     * @return {@link PendantItem}'s own identifier for its accessory slot,
     * using a static method as it is used in other conditions without access to an instance.
     */
    @Override
    public SlotTypeReference getIdentifier() {
        return getStaticIdentifier();
    }

    public static SlotTypeReference getStaticIdentifier() {
        return AetherConfig.COMMON.use_default_accessories_menu.get() ? new SlotTypeReference("necklace") : AetherAccessorySlots.getPendantSlotType();
    }
}
