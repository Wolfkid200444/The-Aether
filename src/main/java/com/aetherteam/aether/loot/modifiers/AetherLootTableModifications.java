package com.aetherteam.aether.loot.modifiers;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aetherfabric.common.loot.IGlobalLootModifier;
import com.aetherteam.aetherfabric.pond.LootContextExtension;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class AetherLootTableModifications {

    private static final Map<ResourceLocation, IGlobalLootModifier> CACHED_LOOT_MODIFIERS = new HashMap<>();

    public static final Map<ResourceLocation, Function<HolderLookup.Provider, IGlobalLootModifier>> LOOT_MODIFIERS = new HashMap<>();

    public static void initLootModifiers() {
        register("remove_seeds", () -> {
            return new RemoveSeedsModifier(
                new LootItemCondition[]{
                    AnyOfCondition.anyOf(
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS),
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)
                    ).build(),
                    InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(
                            ItemPredicate.Builder.item()
                                .of(Items.SHEARS)
                        )
                    ).build()
                }
            );
        });

        register("enchanted_grass", () -> {
            return new EnchantedGrassModifier(
                new LootItemCondition[]{
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(AetherBlocks.BERRY_BUSH.get()).build()
                },
                AetherItems.BLUE_BERRY.toStack(1)
            );
        });

        register("double_drops", () -> new DoubleDropsModifier(new LootItemCondition[0]));
        register("pig_drops", () -> new PigDropsModifier(new LootItemCondition[0]));

        register("gloves_loot_leather", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[]{},
                AetherItems.LEATHER_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("leather")))
            );
        });

        register("gloves_loot_chain", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[]{
                    LootTableCondition.builder(ResourceLocation.fromNamespaceAndPath("aether", "chests/dungeon/gold/gold_dungeon_reward"))
                        .invert()
                        .build()
                },
                AetherItems.CHAINMAIL_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("chainmail")))
            );
        });

        register("gloves_loot_iron", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[]{
                    LootTableCondition.builder(ResourceLocation.fromNamespaceAndPath("aether", "chests/ruined_portal"))
                        .invert()
                        .build()
                },
                AetherItems.IRON_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("iron")))
            );
        });

        register("gloves_loot_gold", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[0],
                AetherItems.GOLDEN_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("gold")))
            );
        });

        register("gloves_loot_diamond", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[0],
                AetherItems.DIAMOND_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("diamond")))
            );
        });

        register("gloves_loot_netherite", (provider) -> {
            return new GlovesLootModifier(
                new LootItemCondition[0],
                AetherItems.NETHERITE_GLOVES.toStack(1),
                provider.lookupOrThrow(Registries.ARMOR_MATERIAL).getOrThrow(ResourceKey.create(Registries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace("netherite")))
            );
        });

        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> reset(registries));
    }

    public static void register(String path, Supplier<IGlobalLootModifier> modifier) {
        register(path, provider -> modifier.get());
    }

    public static void register(String path, Function<HolderLookup.Provider, IGlobalLootModifier> modifier) {
        LOOT_MODIFIERS.put(ResourceLocation.fromNamespaceAndPath("aether", path), modifier);
    }

    public static void reset(HolderLookup.Provider provider) {
        CACHED_LOOT_MODIFIERS.clear();
        LOOT_MODIFIERS.forEach((location, supplier) -> CACHED_LOOT_MODIFIERS.put(location, supplier.apply(provider)));
    }

    public static ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (var value : CACHED_LOOT_MODIFIERS.values()) {
            generatedLoot = value.apply(generatedLoot, context);
        }

        return generatedLoot;
    }

    public record LootTableCondition(ResourceLocation lootTableId) implements LootItemCondition {

        public static final LootItemConditionType TYPE = new LootItemConditionType(
            RecordCodecBuilder.<LootTableCondition>mapCodec(instance -> {
                return instance.group(ResourceLocation.CODEC.fieldOf("loot_table_id").forGetter(LootTableCondition::lootTableId))
                    .apply(instance, LootTableCondition::new);
            })
        );

        @Override
        public LootItemConditionType getType() {
            return TYPE;
        }

        @Override
        public boolean test(LootContext context) {
            return Objects.equals(((LootContextExtension) context).getTableId(), lootTableId);
        }

        public static LootTableCondition.Builder builder(ResourceLocation lootTableId) {
            return new Builder(lootTableId);
        }

        public record Builder(ResourceLocation lootTableId) implements LootItemCondition.Builder {
            @Override
            public LootItemCondition build() {
                return new LootTableCondition(lootTableId);
            }
        }
    }
}
