package com.andrei1058.spigot.signapi;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class v1_8_R3 extends SignVersion {

    protected v1_8_R3() {
        super();
    }

    @Override
    protected void update(Player player, int x, int y, int z, List<String> strings) {
        if (player == null) return;
        if (strings == null) return;
        if (strings.isEmpty()) return;

        try {
            Object[] lines = new Object[Math.min(strings.size(), 4)];
            for (int f = 0; f < lines.length; f++) {
                lines[f] = getNMSClass("IChatBaseComponent$ChatSerializer")
                        .getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null,
                                "{\"text\": \"" + strings.get(f) + "\"}");
            }

            for (int f = 0; f < lines.length; f++) {
                lines[f] = getNMSClass("IChatBaseComponent$ChatSerializer")
                        .getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null,
                                "{\"text\": \"" + strings.get(f) + "\"}");
            }

            Constructor<?> s = getNMSClass("PacketPlayOutUpdateSign").getConstructor(getNMSClass("World").getDeclaredClasses()[0], getNMSClass("BlockPosition"), Array.newInstance(getNMSClass("IChatBaseComponent"), lines.length).getClass());
            Constructor<?> b = getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);
            Object ph = player.getClass().getMethod("getHandle").invoke(player);
            Object w = ph.getClass().getMethod("getWorld").invoke(ph);
            Object packet = s.newInstance(w, b.newInstance(x, y, z), lines);
            super.sendPacket(player, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void update(List<Player> players, int x, int y, int z, List<String> strings) {
        if (players == null) return;
        if (strings == null) return;
        if (players.isEmpty()) return;
        if (strings.isEmpty()) return;

        try {
            Object[] lines = new Object[Math.min(strings.size(), 4)];
            for (int f = 0; f < lines.length; f++) {
                lines[f] = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + strings.get(f) + "\"}");
            }

            Constructor<?> s = getNMSClass("PacketPlayOutUpdateSign").getConstructor(getNMSClass("World").getDeclaredClasses()[0], getNMSClass("BlockPosition"), Array.newInstance(getNMSClass("IChatBaseComponent"), lines.length).getClass());
            Constructor<?> b = getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);
            Object ph = players.get(0).getClass().getMethod("getHandle").invoke(players.get(0));
            Object w = ph.getClass().getMethod("getWorld").invoke(ph);
            Object packet = s.newInstance(w, b.newInstance(x, y, z), lines);

            for (Player p : players) {
                super.sendPacket(p, packet);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
