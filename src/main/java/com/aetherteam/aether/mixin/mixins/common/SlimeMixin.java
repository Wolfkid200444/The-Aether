package com.aetherteam.aether.mixin.mixins.common;

import com.aetherteam.aether.event.hooks.EntityHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slime.class)
public abstract class SlimeMixin {

    @Shadow
    public abstract EntityType<? extends Slime> getType();

    @ModifyExpressionValue(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Slime;isDeadOrDying()Z"))
    private boolean aetherFabric$dontSplitForSwets(boolean original) {
        return original && !EntityHooks.preventSplit((Mob) (Object) this);
    }
}
