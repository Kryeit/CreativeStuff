package com.kryeit.creativestuff;

import com.kryeit.creativestuff.command.*;
import com.kryeit.creativestuff.listener.PlayerDeath;
import com.kryeit.creativestuff.listener.ServerLogin;
import com.kryeit.creativestuff.queue.Queue;
import com.kryeit.creativestuff.queue.QueueHandler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class CreativeStuff implements DedicatedServerModInitializer {

    public static Queue queue = new Queue();
    @Override
    public void onInitializeServer() {
        registerEvents();
        registerCommands();
    }

    public void registerEvents() {
        ServerPlayConnectionEvents.INIT.register(new ServerLogin());
        ServerPlayConnectionEvents.INIT.register(new QueueHandler());
        ServerLivingEntityEvents.AFTER_DEATH.register(new PlayerDeath());
    }

    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicatedServer, commandFunction) -> {
            Discord.register(dispatcher);
            Kofi.register(dispatcher);
            Rules.register(dispatcher);
            Vote.register(dispatcher);
        });
    }
}
