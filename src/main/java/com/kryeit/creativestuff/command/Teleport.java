package com.kryeit.creativestuff.command;

import com.kryeit.creativestuff.MinecraftServerSupplier;
import com.kryeit.creativestuff.command.completions.PlayerSuggestionProvider;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Teleport {
    public static int execute(CommandContext<ServerCommandSource> context, String name) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();

        if (player == null) {
            Supplier<Text> message = () -> Text.of("Can't execute from console");
            source.sendFeedback(message, false);
            return 0;
        }

        if (name == null) {
            player.sendMessage(Text.literal("Player not found"));
            return 0;
        }

        ServerPlayerEntity target = MinecraftServerSupplier.getServer().getPlayerManager().getPlayer(name);

        if (target == null) {
            player.sendMessage(Text.literal("Player not found"));
            return 0;
        }

        player.teleport(target.getServerWorld(),
                target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch());

        return Command.SINGLE_SUCCESS;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("goto")
                .then(CommandManager.argument("name", StringArgumentType.word())
                        .suggests(PlayerSuggestionProvider.suggestOnlinePlayers())
                        .executes(context -> execute(context, StringArgumentType.getString(context, "name")))
                )
        );
    }
}
