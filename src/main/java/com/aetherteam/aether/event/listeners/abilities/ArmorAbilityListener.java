package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.fabric.events.EntityEvents;
import com.aetherteam.aether.fabric.events.LivingFallEvent;
import com.aetherteam.aether.item.combat.abilities.armor.GravititeArmor;
import com.aetherteam.aether.item.combat.abilities.armor.NeptuneArmor;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.aetherteam.aether.item.combat.abilities.armor.ValkyrieArmor;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.aetherteam.aether.fabric.events.EntityTickEvents;

public class ArmorAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        EntityTickEvents.AFTER.register(ArmorAbilityListener::onEntityUpdate);
        EntityEvents.LIVING_JUMPED.register(ArmorAbilityListener::onEntityJump);
        LivingFallEvent.EVENT.register(ArmorAbilityListener::onEntityFall);
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> ArmorAbilityListener.onEntityAttack(entity, source));
    }

    /**
     * @see ValkyrieArmor#handleFlight(LivingEntity)
     * @see NeptuneArmor#boostWaterSwimming(LivingEntity)
     * @see PhoenixArmor#boostLavaSwimming(LivingEntity)
     * @see PhoenixArmor#damageArmor(LivingEntity)
     */
    public static void onEntityUpdate(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            ValkyrieArmor.handleFlight(livingEntity);
            NeptuneArmor.boostWaterSwimming(livingEntity);
            PhoenixArmor.boostLavaSwimming(livingEntity);
            PhoenixArmor.damageArmor(livingEntity);
        }
    }

    /**
     * @see GravititeArmor#boostedJump(LivingEntity)
     */
    public static void onEntityJump(LivingEntity livingEntity) {
        GravititeArmor.boostedJump(livingEntity);
    }

    /**
     * @see AbilityHooks.ArmorHooks#fallCancellation(LivingEntity)
     */
    public static void onEntityFall(LivingEntity entity, LivingFallEvent event) {
        if (!event.isCanceled()) {
            event.setCanceled(AbilityHooks.ArmorHooks.fallCancellation(entity));
        }
    }

    /**
     * @see PhoenixArmor#extinguishUser(LivingEntity, DamageSource)
     */
    public static boolean onEntityAttack(LivingEntity livingEntity, DamageSource damageSource) {
        return PhoenixArmor.extinguishUser(livingEntity, damageSource);
    }
}
