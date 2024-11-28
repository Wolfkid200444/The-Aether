package com.aetherteam.aetherfabric.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.EnumSet;

public class BlockEvents {

    public static final Event<NeighborUpdate> NEIGHBOR_UPDATE = EventFactory.createArrayBacked(NeighborUpdate.class, invokers -> (level, pos, state, notifiedSides, forceRedstoneUpdate, callback) -> {
        for (var invoker : invokers) invoker.onNeighborUpdate(level, pos, state, notifiedSides, forceRedstoneUpdate, callback);
    });

    public interface NeighborUpdate {
        void onNeighborUpdate(Level level, BlockPos pos, BlockState state, EnumSet<Direction> notifiedSides, boolean forceRedstoneUpdate, CancellableCallback callback);
    }
}
