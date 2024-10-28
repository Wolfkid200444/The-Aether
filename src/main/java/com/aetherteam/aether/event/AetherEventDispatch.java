package com.aetherteam.aether.event;

import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;

import org.jetbrains.annotations.Nullable;

public class AetherEventDispatch {
    /**
     * @see BossFightEvent.Start
     */
    public static BossFightEvent.Start onBossFightStart(Entity entity, BossRoomTracker<?> dungeon) {
        BossFightEvent.Start event = new BossFightEvent.Start(entity, dungeon);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see BossFightEvent.Stop
     */
    public static BossFightEvent.Stop onBossFightStop(Entity entity, BossRoomTracker<?> dungeon) {
        BossFightEvent.Stop event = new BossFightEvent.Stop(entity, dungeon);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see BossFightEvent.AddPlayer
     */
    public static BossFightEvent.AddPlayer onBossFightPlayerAdd(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
        BossFightEvent.AddPlayer event = new BossFightEvent.AddPlayer(entity, dungeon, player);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see BossFightEvent.RemovePlayer
     */
    public static BossFightEvent.RemovePlayer onBossFightPlayerRemove(Entity entity, BossRoomTracker<?> dungeon, ServerPlayer player) {
        BossFightEvent.RemovePlayer event = new BossFightEvent.RemovePlayer(entity, dungeon, player);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see EggLayEvent
     */
    public static EggLayEvent onLayEgg(Entity entity, SoundEvent sound, float volume, float pitch, ItemStack item) {
        EggLayEvent event = new EggLayEvent(entity, sound, volume, pitch, item);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see PlacementBanEvent.SpawnParticles
     */
    public static PlacementBanEvent.SpawnParticles onPlacementSpawnParticles(LevelAccessor level, BlockPos pos, @Nullable Direction face, @Nullable ItemStack stack, @Nullable BlockState state) {
        PlacementBanEvent.SpawnParticles event = new PlacementBanEvent.SpawnParticles(level, pos, face, stack, state);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see PlacementBanEvent.CheckItem
     */
    public static boolean isItemPlacementBanned(LevelAccessor level, BlockPos pos, ItemStack stack) {
        PlacementBanEvent.CheckItem event = new PlacementBanEvent.CheckItem(level, pos, stack.copy());
        NeoForge.EVENT_BUS.post(event);
        return event.isBanned();
    }

    /**
     * @see PlacementBanEvent.CheckBlock
     */
    public static boolean isBlockPlacementBanned(LevelAccessor level, BlockPos pos, BlockState state) {
        PlacementBanEvent.CheckBlock event = new PlacementBanEvent.CheckBlock(level, pos, state);
        NeoForge.EVENT_BUS.post(event);
        return event.isBanned();
    }

    /**
     * @see PlacementConvertEvent
     */
    public static PlacementConvertEvent onPlacementConvert(LevelAccessor level, BlockPos pos, BlockState oldState, BlockState newState) {
        PlacementConvertEvent event = new PlacementConvertEvent(level, pos, oldState, newState);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see FreezeEvent.FreezeFromBlock
     */
    public static FreezeEvent.FreezeFromBlock onBlockFreezeFluid(LevelAccessor level, BlockPos pos, BlockPos origin, BlockState fluidState, BlockState blockState, BlockState sourceBlock) {
        FreezeEvent.FreezeFromBlock event = new FreezeEvent.FreezeFromBlock(level, pos, origin, fluidState, blockState, sourceBlock);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see FreezeEvent.FreezeFromItem
     */
    public static FreezeEvent.FreezeFromItem onItemFreezeFluid(LevelAccessor level, BlockPos pos, BlockState fluidState, BlockState blockState, ItemStack sourceItem) {
        FreezeEvent.FreezeFromItem event = new FreezeEvent.FreezeFromItem(level, pos, fluidState, blockState, sourceItem);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see ItemUseConvertEvent
     */
    public static ItemUseConvertEvent onItemUseConvert(@Nullable Player player, LevelAccessor level, BlockPos pos, @Nullable ItemStack stack, BlockState oldState, BlockState newState, RecipeType<?> recipeType) {
        ItemUseConvertEvent event = new ItemUseConvertEvent(player, level, pos, stack, oldState, newState, recipeType);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }

    /**
     * @see TriggerTrapEvent
     */
    public static boolean onTriggerTrap(Player player, Level level, BlockPos pos, BlockState state) {
        TriggerTrapEvent event = new TriggerTrapEvent(player, level, pos, state);
        NeoForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    /**
     * @see ValkyrieTeleportEvent
     */
    public static ValkyrieTeleportEvent onValkyrieTeleport(LivingEntity entity, double targetX, double targetY, double targetZ) {
        ValkyrieTeleportEvent event = new ValkyrieTeleportEvent(entity, targetX, targetY, targetZ);
        NeoForge.EVENT_BUS.post(event);
        return event;
    }
}
