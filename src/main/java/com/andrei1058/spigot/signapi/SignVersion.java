package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SignVersion {

    protected String serverVersion;

    protected SignVersion() {
        try {
            this.serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException ignored){
            // this will only happen with junit tests
        }
    }

    protected abstract void update(Player player, int x, int y, int z, List<String> strings);

    protected abstract void update(List<Player> players, int x, int y, int z, List<String> strings);


    protected Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + serverVersion + "." + name);
        } catch (ClassNotFoundException ex) {
            //Do something
        }
        return null;
    }

    protected void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            //Do something
        }
    }
}
