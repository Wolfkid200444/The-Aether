package com.aetherteam.aether.fabric;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StructureTemplateUtils {

    private static ThreadLocal<StructureTemplate> processBlockInfosTemplateCache = ThreadLocal.withInitial(() -> null);

    public static void setStructureTemplate(StructureTemplate structureTemplate) {
        processBlockInfosTemplateCache.set(structureTemplate);
    }

    public static StructureTemplate getStructureTemplate() {
        return processBlockInfosTemplateCache.get();
    }
}
