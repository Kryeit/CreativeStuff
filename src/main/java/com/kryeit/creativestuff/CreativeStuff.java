package com.kryeit.creativestuff;

import com.kryeit.creativestuff.command.*;
import com.kryeit.creativestuff.queue.Queue;
import com.kryeit.creativestuff.queue.QueueHandler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

import java.util.HashMap;
import java.util.UUID;

public class CreativeStuff implements DedicatedServerModInitializer {

    public static Queue queue = new Queue();
    public static HashMap<UUID, Long> lastActiveTime = new HashMap<>();
    @Override
    public void onInitializeServer() {
        registerEvents();
        registerCommands();
    }

    public void registerEvents() {
        ServerPlayConnectionEvents.INIT.register(new QueueHandler());
    }

    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicatedServer, commandFunction) -> {
            Discord.register(dispatcher);
            Kofi.register(dispatcher);
            Rules.register(dispatcher);
            Vote.register(dispatcher);
            Teleport.register(dispatcher);

            Creative.register(dispatcher);
            Survival.register(dispatcher);
        });
    }
}
