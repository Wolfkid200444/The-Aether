package com.aetherteam.aether.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AetherData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
//        DataGenerator generator = event.getGenerator();
//        ExistingFileHelper fileHelper = event.getExistingFileHelper();
//        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
//        PackOutput packOutput = generator.getPackOutput();
//
//        Reflection.initialize(AetherMobCategory.class);
//
//        // Client Data
////        generator.addProvider(event.includeClient(), new AetherBlockStateData(packOutput, fileHelper));
////        generator.addProvider(event.includeClient(), new AetherItemModelData(packOutput, fileHelper));
//        generator.addProvider(event.includeClient(), new AetherLanguageData(packOutput));
//        generator.addProvider(event.includeClient(), new AetherSoundData(packOutput, fileHelper));
//
//        // Server Data
//        generator.addProvider(event.includeServer(), new AetherRecipeData(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), AetherLootTableData.create(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), new AetherLootModifierData(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), new AetherAdvancementData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherDataMapData(packOutput, lookupProvider));
//        DatapackBuiltinEntriesProvider registrySets = new AetherRegistrySets(packOutput, lookupProvider);
//        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
//        generator.addProvider(event.includeServer(), registrySets);
//        // Tags
//        AetherBlockTagData blockTags = new AetherBlockTagData(packOutput, lookupProvider, fileHelper);
//        generator.addProvider(event.includeServer(), blockTags);
//        generator.addProvider(event.includeServer(), new AetherItemTagData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
//        generator.addProvider(event.includeServer(), new AetherEntityTagData(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), new AetherFluidTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherBiomeTagData(packOutput, lookupProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherStructureTagData(packOutput, registryProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherDamageTypeTagData(packOutput, registryProvider, fileHelper));
//        generator.addProvider(event.includeServer(), new AetherSoundTagData(packOutput, registryProvider, fileHelper));
//
//        // pack.mcmeta
//        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
//                Component.translatable("pack.aether.mod.description"),
//                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
//                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }
}
