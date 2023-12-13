package com.kryeit.creativestuff.listener;

import com.kryeit.creativestuff.MinecraftServerSupplier;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class ServerLogin implements ServerPlayConnectionEvents.Init {

    @Override
    public void onPlayInit(ServerPlayNetworkHandler handler, MinecraftServer server) {
        ServerPlayerEntity player = handler.player;

        if (Objects.equals(player.getName().toString(), "Conductor")) return;

        File playerDataDirectory = new File("world/playerdata/");

        File[] playerDataFiles = playerDataDirectory.listFiles();

        if (playerDataFiles == null) return;

        for (File playerDataFile : playerDataFiles) {
            String fileName = playerDataFile.getName();
            if (!fileName.endsWith(".dat")) continue;
            UUID id = UUID.fromString(fileName.substring(0, fileName.length() - 4));
            if (player.getUuid().equals(id)) {
                // Has joined before
                return;
            }
        }

        // Has NOT joined before
        MinecraftServerSupplier.getServer().getPlayerManager().broadcast(
                Text.literal("Welcome " + player.getName().toString() + " to Kryeitive!").setStyle(Style.EMPTY.withColor(Formatting.AQUA)),
                false
        );

        player.sendMessage(Text.literal("Welcome to Kryeitive! Happy building :).").setStyle(Style.EMPTY.withColor(Formatting.AQUA)));
    }
}
