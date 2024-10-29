package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.fabric.events.ItemAttributeModifierEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @WrapOperation(
        method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;forEach(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V")
    )
    private void aetherFabric$modifyAttributeEvent_1(ItemAttributeModifiers instance, EquipmentSlot equipmentSlot, BiConsumer<Holder<Attribute>, AttributeModifier> action, Operation<Void> original) {
        var event = ItemAttributeModifierEvent.invokeEvent((ItemStack) (Object) this, instance);

        instance = new ItemAttributeModifiers(event.getModifiers(), instance.showInTooltip());

        original.call(instance, equipmentSlot, action);
    }

    @WrapOperation(
        method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/ItemAttributeModifiers;forEach(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V")
    )
    private void aetherFabric$modifyAttributeEvent_2(ItemAttributeModifiers instance, EquipmentSlotGroup slotGroup, BiConsumer<Holder<Attribute>, AttributeModifier> action, Operation<Void> original) {
        var event = ItemAttributeModifierEvent.invokeEvent((ItemStack) (Object) this, instance);

        instance = new ItemAttributeModifiers(event.getModifiers(), instance.showInTooltip());

        original.call(instance, slotGroup, action);
    }
}
