package com.aetherteam.aether.fabric;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ExtraServerLivingEntityEvents {

    public static final Event<ModifyDamage> MODIFY_DAMAGE = EventFactory.createArrayBacked(ModifyDamage.class, callbacks -> (entity, source, originalDamage, newDamage) -> {
        for (var callback : callbacks) callback.modifyDamage(entity, source, originalDamage, newDamage);
    });

    @FunctionalInterface
    public interface ModifyDamage {
        /**
         * Called when a living entity is going to take damage. Can be used to cancel the damage entirely.
         *
         * <p>The amount corresponds to the "incoming" damage amount, before armor and other mitigations have been applied.
         *
         * @param entity         the entity
         * @param source         the source of the damage
         * @param originalDamage the amount of damage that the entity will take (before modifications)
         * @param newDamage      the amount of damage that the entity will be taking
         */
        void modifyDamage(LivingEntity entity, DamageSource source, float originalDamage, MutableFloat newDamage);
    }
}
