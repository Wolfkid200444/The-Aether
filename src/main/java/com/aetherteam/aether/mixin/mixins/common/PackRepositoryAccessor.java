package com.aetherteam.aether.mixin.mixins.common;

import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(PackRepository.class)
public interface PackRepositoryAccessor {
    @Accessor("sources")
    Set<RepositorySource> aetherFabric$sources();

    @Accessor("sources")
    void aetherFabric$sources(Set<RepositorySource> sources);
}