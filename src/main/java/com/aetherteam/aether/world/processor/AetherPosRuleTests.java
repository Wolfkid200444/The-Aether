package com.aetherteam.aether.world.processor;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import com.aetherteam.aetherfabric.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherPosRuleTests {
    public static final DeferredRegister<PosRuleTestType<?>> POS_RULE_TESTS = DeferredRegister.create(BuiltInRegistries.POS_RULE_TEST.key(), Aether.MODID);

    public static final Supplier<PosRuleTestType<BorderBoxPosTest>> BORDER_BOX = POS_RULE_TESTS.register("border_box", () -> () -> BorderBoxPosTest.CODEC);
}
