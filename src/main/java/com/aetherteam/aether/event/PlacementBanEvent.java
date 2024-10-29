package com.aetherteam.aether.event;

import com.aetherteam.aether.fabric.events.ICancellable;
import com.google.common.base.Preconditions;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

/**
 * PlacementBanEvent is fired when an event involving placement banning occurs.<br>
 * If a method utilizes this {@link Event} as its parameter, the method will receive every child event of this class.<br>
 * <br>
 */
public abstract class PlacementBanEvent {
    /**
     * PlacementBanEvent.CheckItem is fired after an item that can be banned is used, but before its placement has been prevented.
     * <br>
     * This event is fired on both {@link EnvType sides}.
     */
    public static class CheckItem extends PlacementBanEvent {
        private boolean banned = true;
        private final LevelAccessor level;
        private final BlockPos pos;
        private final ItemStack itemStack;

        /**
         * @param level     The {@link LevelAccessor} that the ban occurs in.
         * @param pos       The {@link BlockPos} the ban occurs at.
         * @param itemStack The {@link ItemStack} to be banned.
         */
        public CheckItem(LevelAccessor level, BlockPos pos, ItemStack itemStack) {
            this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
            this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
            this.itemStack = Preconditions.checkNotNull(itemStack, "Null itemStack in PlacementBanEvent");
        }

        /**
         * @return The {@link LevelAccessor} that the ban occurs in.
         */
        public LevelAccessor getLevel() {
            return this.level;
        }

        /**
         * @return The {@link BlockPos} the ban occurs at.
         */
        public BlockPos getPos() {
            return this.pos;
        }

        /**
         * @return The {@link ItemStack} to be banned.
         */
        public ItemStack getItemStack() {
            return this.itemStack;
        }

        /**
         * @return Whether the item placement is banned, as a {@link Boolean}.
         */
        public boolean isBanned() {
            return this.banned;
        }

        /**
         * Sets whether the item placement is banned.
         *
         * @param banned The ban value as a {@link Boolean}.
         */
        public void setBanned(boolean banned) {
            this.banned = banned;
        }
    }

    /**
     * PlacementBanEvent.CheckItem is fired after a block that can be banned is placed, but before its placement has been prevented.
     * <br>
     * This event is only fired on the {@link EnvType#SERVER} side.
     */
    public static class CheckBlock extends PlacementBanEvent {
        private boolean banned = true;
        private final LevelAccessor level;
        private final BlockPos pos;
        private final BlockState blockState;

        /**
         * @param level      The {@link LevelAccessor} that the ban occurs in.
         * @param pos        The {@link BlockPos} the ban occurs at.
         * @param blockState The {@link BlockState} to be banned.
         */
        public CheckBlock(LevelAccessor level, BlockPos pos, BlockState blockState) {
            this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
            this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
            this.blockState = Preconditions.checkNotNull(blockState, "Null blockState in PlacementBanEvent");
        }

        /**
         * @return The {@link LevelAccessor} that the ban occurs in.
         */
        public LevelAccessor getLevel() {
            return this.level;
        }

        /**
         * @return The {@link BlockPos} the ban occurs at.
         */
        public BlockPos getPos() {
            return this.pos;
        }

        /**
         * @return The {@link BlockState} to be banned.
         */
        public BlockState getBlockState() {
            return this.blockState;
        }

        /**
         * @return Whether the block placement is banned, as a {@link Boolean}.
         */
        public boolean isBanned() {
            return this.banned;
        }

        /**
         * Sets whether the block placement is banned.
         *
         * @param banned The ban value as a {@link Boolean}.
         */
        public void setBanned(boolean banned) {
            this.banned = banned;
        }
    }

    /**
     * PlacementBanEvent.SpawnParticles is fired after a placement ban has occurred.
     * <br>
     * This event is {@link ICancellable}.<br>
     * If the event is not canceled, the particles will spawn.
     * <br>
     * This event is fired on both {@link EnvType sides}.<br>
     * <br>
     * If this event is canceled, the particles will not be spawned.
     */
    public static class SpawnParticles extends PlacementBanEvent implements ICancellable {
        private final LevelAccessor level;
        private final BlockPos pos;
        @Nullable
        private final Direction face;
        @Nullable
        private final ItemStack itemStack;
        @Nullable
        private final BlockState blockState;

        /**
         * @param level The {@link LevelAccessor} to spawn the particles in.
         * @param pos   The {@link BlockPos} to spawn the particles at.
         * @param face  The {@link Direction} of the face the particles are spawning from.
         * @param stack The {@link ItemStack} being banned.
         * @param state The {@link BlockState} being banned.
         */
        public SpawnParticles(LevelAccessor level, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
            this.level = Preconditions.checkNotNull(level, "Null world in PlacementBanEvent");
            this.pos = Preconditions.checkNotNull(pos, "Null position in PlacementBanEvent");
            this.face = face;
            this.itemStack = stack;
            this.blockState = state;
        }

        /**
         * @return The {@link LevelAccessor} to spawn the particles in.
         */
        public LevelAccessor getLevel() {
            return this.level;
        }

        /**
         * @return The {@link BlockPos} to spawn the particles at.
         */
        public BlockPos getPos() {
            return this.pos;
        }

        /**
         * This method is {@link Nullable}. It is marked null for block placement bans.
         *
         * @return The {@link Direction} of the face the particles are spawning from.
         */
        @Nullable
        public Direction getFace() {
            return this.face;
        }

        /**
         * This method is {@link Nullable}. It is marked null for block placement bans.
         *
         * @return The {@link ItemStack} being banned.
         */
        @Nullable
        public ItemStack getItemStack() {
            return this.itemStack;
        }

        /**
         * This method is {@link Nullable}. It is marked null for item placement bans.
         *
         * @return The {@link BlockState} being banned.
         */
        @Nullable
        public BlockState getBlockState() {
            return this.blockState;
        }

        private boolean isCancelled = false;

        public boolean isCanceled() {
            return this.isCancelled;
        }

        public void setCanceled(boolean value) {
            this.isCancelled = value;
        }
    }

    //--

    public static final Event<ItemPlacementBan> ITEM_PLACEMENT_BAN = EventFactory.createArrayBacked(ItemPlacementBan.class, invokers -> event -> {
        for (var invoker : invokers) invoker.checkIfBanned(event);
    });

    public interface ItemPlacementBan {
        void checkIfBanned(CheckItem event);
    }

    public static final Event<BlockPlacementBan> BLOCK_PLACEMENT_BAN = EventFactory.createArrayBacked(BlockPlacementBan.class, invokers -> event -> {
        for (var invoker : invokers) invoker.checkIfBanned(event);
    });

    public interface BlockPlacementBan {
        void checkIfBanned(CheckBlock event);
    }

    public static final Event<SpawnParticlesCallback> SPAWN_PARTICLES = EventFactory.createArrayBacked(SpawnParticlesCallback.class, invokers -> event -> {
        for (var invoker : invokers) invoker.attemptSpawnParticles(event);
    });

    public interface SpawnParticlesCallback {
        void attemptSpawnParticles(SpawnParticles event);
    }
}
