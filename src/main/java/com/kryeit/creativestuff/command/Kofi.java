package com.kryeit.creativestuff.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Kofi {
    public static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();

        if (player == null) {
            Supplier<Text> message = () -> Text.of("Can't execute from console");
            source.sendFeedback(message, false);
            return 0;
        }

        player.sendMessage(Text.literal("Kofi -> https://ko-fi.com/kryeit\n\n" +
                        "Collaborator advantages:" +
                        "- Color name both on Discord and in Minecraft\n" +
                        "- Claiming advantages:\n" +
                        "    · 300CB/hour instead of 200\n" +
                        "    · 5.000.000 max CB instead of 2.000.000\n" +
                        "    · Claims expire after 365  days of inactiveness instead of 120\n" +
                        " - Won't get kicked by the AFK system when the server is full")
                .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://ko-fi.com/kryeit"))));
        return Command.SINGLE_SUCCESS;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("kofi")
                .executes(Kofi::execute)
        );
        dispatcher.register(CommandManager.literal("donate")
                .executes(Kofi::execute)
        );
    }
}
