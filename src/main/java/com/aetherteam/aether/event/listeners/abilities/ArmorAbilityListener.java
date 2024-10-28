package com.aetherteam.aether.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import com.aetherteam.aether.item.combat.abilities.armor.GravititeArmor;
import com.aetherteam.aether.item.combat.abilities.armor.NeptuneArmor;
import com.aetherteam.aether.item.combat.abilities.armor.PhoenixArmor;
import com.aetherteam.aether.item.combat.abilities.armor.ValkyrieArmor;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import com.aetherteam.aether.fabric.events.EntityTickEvents;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class ArmorAbilityListener {
    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        EntityTickEvents.AFTER.register(ArmorAbilityListener::onEntityUpdate);
        bus.addListener(ArmorAbilityListener::onEntityJump);
        bus.addListener(ArmorAbilityListener::onEntityFall);
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
    public static void onEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        GravititeArmor.boostedJump(livingEntity);
    }

    /**
     * @see AbilityHooks.ArmorHooks#fallCancellation(LivingEntity)
     */
    public static void onEntityFall(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (!event.isCanceled()) {
            event.setCanceled(AbilityHooks.ArmorHooks.fallCancellation(livingEntity));
        }
    }

    /**
     * @see PhoenixArmor#extinguishUser(LivingEntity, DamageSource)
     */
    public static boolean onEntityAttack(LivingEntity livingEntity, DamageSource damageSource) {
        return PhoenixArmor.extinguishUser(livingEntity, damageSource);
    }
}
