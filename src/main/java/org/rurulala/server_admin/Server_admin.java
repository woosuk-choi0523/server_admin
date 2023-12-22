package org.rurulala.server_admin;

import org.bukkit.plugin.java.JavaPlugin;

public final class Server_admin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("hehe rurulala hehe");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("hehe sad");
    }
}