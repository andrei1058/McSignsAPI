package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class SignVersion {

    protected String serverVersion;

    protected SignVersion() {
        try {
            this.serverVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException ignored){
            // this will only happen with junit tests
        }
    }

    protected void update(Player player, Location location, List<String> strings){
        if (player == null) return;
        if (!location.getBlock().getType().toString().contains("SIGN")) return;
        if (strings == null) return;
        if (strings.isEmpty()) return;

        String[] lines = new String[4];

        for (int x = 0; x < 4; x++) {
            if (strings.size() > x) {
                lines[0] = strings.get(x);
            } else {
                lines[0] = "";
            }
        }

        player.sendSignChange(location, lines);
    }

    protected void update(List<Player> players, Location location, List<String> strings){
        if (players == null) return;
        if (strings == null) return;
        if (players.isEmpty()) return;
        if (!location.getBlock().getType().toString().contains("SIGN")) return;
        if (strings.isEmpty()) return;

        String[] lines = new String[4];

        for (int x = 0; x < 4; x++) {
            if (strings.size() > x) {
                lines[0] = strings.get(x);
            } else {
                lines[0] = "";
            }
        }

        for (Player p : players) {
            p.sendSignChange(location, lines);
        }
    }


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
