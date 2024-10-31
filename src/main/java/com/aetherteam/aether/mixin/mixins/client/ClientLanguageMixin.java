package com.aetherteam.aether.mixin.mixins.client;

import com.aetherteam.aether.fabric.LanguageExtension;
import net.minecraft.client.resources.language.ClientLanguage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.Map;

@Mixin(ClientLanguage.class)
public class ClientLanguageMixin implements LanguageExtension {

    @Shadow
    @Final
    private Map<String, String> storage;

    @Override
    public void setLanguageData(Map<String, String> data) { /* NO-OP */ }

    @Override
    public Map<String, String> getLanguageData() {
        return Collections.unmodifiableMap(this.storage);
    }
}
