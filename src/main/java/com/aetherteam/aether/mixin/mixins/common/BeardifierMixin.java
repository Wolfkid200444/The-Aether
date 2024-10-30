package com.aetherteam.aether.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.neoforged.neoforge.common.world.PieceBeardifierModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(Beardifier.class)
public abstract class BeardifierMixin {
    @WrapOperation(method = "method_42694", constant = @Constant(classValue = PoolElementStructurePiece.class))
    private static boolean aetherFabric$checkCustomBeardifier(StructurePiece structurePiece, Operation<Boolean> original) {
        return !(structurePiece instanceof PieceBeardifierModifier) && original.call(structurePiece);
    }

    @WrapOperation(method = "method_42694", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectList;add(Ljava/lang/Object;)Z", ordinal = 2))
    private static boolean aetherFabric$useCustomBeardifier(ObjectList instance, Object object, Operation<Boolean> original, @Local() StructurePiece structurePiece) {
        if (structurePiece instanceof PieceBeardifierModifier modifier) {
            if (modifier.getTerrainAdjustment() == TerrainAdjustment.NONE) return false;

            object = new Beardifier.Rigid(modifier.getBeardifierBox(), modifier.getTerrainAdjustment(), modifier.getGroundLevelDelta());
        }

        return original.call(instance, object);
    }
}
