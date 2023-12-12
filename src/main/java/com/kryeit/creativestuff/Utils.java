package com.kryeit.creativestuff;

public class Utils {

    public static boolean isServerFull() {
        return MinecraftServerSupplier.getServer().getMaxPlayerCount() <= MinecraftServerSupplier.getServer().getCurrentPlayerCount();
    }
}
