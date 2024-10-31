package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.LanguageExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(Language.class)
public class LanguageMixin implements LanguageExtension {

    @Unique
    private Map<String, String> languageData = new HashMap<>();

    @Override
    public Map<String, String> getLanguageData() {
        return Collections.unmodifiableMap(languageData);
    }

    @Override
    public void setLanguageData(Map<String, String> data) {
        this.languageData = data;
    }
}
