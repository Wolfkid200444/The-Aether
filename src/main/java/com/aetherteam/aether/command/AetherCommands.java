package com.aetherteam.aether.command;

import com.aetherteam.aether.Aether;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class AetherCommands {
    /**
     * @see Aether#eventSetup(IEventBus)
     */
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection selection) {
        AetherTimeCommand.register(dispatcher);
        EternalDayCommand.register(dispatcher);
        PlayerCapabilityCommand.register(dispatcher);
        SunAltarWhitelistCommand.register(dispatcher);
        WorldPreviewFixCommand.register(dispatcher, selection == Commands.CommandSelection.INTEGRATED);
    }
}
