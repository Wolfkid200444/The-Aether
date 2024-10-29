package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

/**
 * BossFightEvent is fired when an event for a boss fight occurs.<br>
 * If a method utilizes this EntityEvent as its parameter, the method will receive every child event of this class.<br>
 */
public abstract class BossFightEvent {
    private final BossRoomTracker<?> dungeon;

    private final Entity entity;

    /**
     * @param entity  The {@link Entity} triggering the boss fight.
     * @param dungeon The {@link BossRoomTracker} representing the boss's dungeon.
     */
    public BossFightEvent(Entity entity, BossRoomTracker<?> dungeon) {
        this.entity = entity;
        this.dungeon = dungeon;
    }

    public Entity getEntity() {
        return entity;
    }

    /**
     * @return The {@link BossRoomTracker} representing the boss's dungeon.
     */
    public BossRoomTracker<?> getDungeon() {
        return this.dungeon;
    }

    /**
     * BossFightEvent.Start is fired when a boss starts a fight.
     * <br>
     * This event is fired on both {@link EnvType sides}.
     */
    public static class Start extends BossFightEvent {
        public Start(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }
    }

    /**
     * BossFightEvent.Start is fired when a boss stops a fight.
     * <br>
     * This event is fired on both {@link EnvType sides}.
     */
    public static class Stop extends BossFightEvent {
        public Stop(Entity entity, BossRoomTracker<?> dungeon) {
            super(entity, dungeon);
        }
    }

    /**
     * BossFightEvent.AddPlayer is fired when a player is added to a boss fight.
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     */
    public static class AddPlayer extends BossFightEvent {
        private final ServerPlayer player;

        public AddPlayer(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
            super(entity, dungeon);
            this.player = player;
        }

        /**
         * @return The {@link ServerPlayer} belonging to the boss fight.
         */
        public ServerPlayer getPlayer() {
            return this.player;
        }
    }

    /**
     * BossFightEvent.RemovePlayer is fired when a player is removed from a boss fight.
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.<br>
     */
    public static class RemovePlayer extends BossFightEvent {
        private final ServerPlayer player;

        public RemovePlayer(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
            super(entity, dungeon);
            this.player = player;
        }

        /**
         * @return The {@link ServerPlayer} belonging to the boss fight.
         */
        public ServerPlayer getPlayer() {
            return this.player;
        }
    }

    public static final Event<StartBossFight> START_BOSS_FIGHT = EventFactory.createArrayBacked(StartBossFight.class, invokers -> event -> {
        for (var invoker : invokers) invoker.startFight(event);
    });

    public interface StartBossFight {
        void startFight(Start event);
    }

    public static final Event<StopBossFight> STOP_BOSS_FIGHT = EventFactory.createArrayBacked(StopBossFight.class, invokers -> event -> {
        for (var invoker : invokers) invoker.stopFight(event);
    });

    public interface StopBossFight {
        void stopFight(Stop event);
    }

    public static final Event<AddPlayerCallback> ADD_PLAYER = EventFactory.createArrayBacked(AddPlayerCallback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.onAdd(event);
    });

    public interface AddPlayerCallback {
        void onAdd(AddPlayer event);
    }

    public static final Event<RemovePlayerCallback> REMOVE_PLAYER = EventFactory.createArrayBacked(RemovePlayerCallback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.onRemove(event);
    });

    public interface RemovePlayerCallback {
        void onRemove(RemovePlayer event);
    }
}
