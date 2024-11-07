package com.aetherteam.aether;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.GameEvent;
import com.aetherteam.aetherfabric.registries.DeferredHolder;
import com.aetherteam.aetherfabric.registries.DeferredRegister;

public class AetherGameEvents {
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create(Registries.GAME_EVENT, Aether.MODID);

    public static final DeferredHolder<GameEvent, GameEvent> ICESTONE_FREEZABLE_UPDATE = GAME_EVENTS.register("icestone_freezable_update", () -> new GameEvent(4));
}
