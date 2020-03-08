package com.andrei1058.spigot.signapi;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// Support this version name and newer
public class v1_8_R3 extends SignVersion {

    private Class<?> baseComponent = getNMSClass("IChatBaseComponent");
    private Method chatSerializer = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
    private Constructor<?> block = getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class);
    private ConcurrentHashMap<String, Object> worlds = new ConcurrentHashMap<>();
    private Constructor<?> sign = getNMSClass("PacketPlayOutUpdateSign").getDeclaredConstructors()[0];

    protected v1_8_R3() throws NoSuchMethodException {
        super();
    }

    @Override
    protected void update(Player player, int x, int y, int z, List<String> strings) {
        if (player == null) return;
        if (!player.getWorld().getBlockAt(x,y,z).getType().toString().contains("SIGN")) return;
        if (strings == null) return;
        if (strings.isEmpty()) return;

        try {
            Object lines = getLines(strings);
            Object packet = sign.newInstance(getNMSWorld(player.getWorld()), block.newInstance(x, y, z), lines);
            super.sendPacket(player, packet);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void update(List<Player> players, int x, int y, int z, List<String> strings) {
        if (players == null) return;
        if (strings == null) return;
        if (players.isEmpty()) return;
        if (!players.get(0).getWorld().getBlockAt(x,y,z).getType().toString().contains("SIGN")) return;
        if (strings.isEmpty()) return;

        try {
            Object lines = getLines(strings);
            Object packet = sign.newInstance(getNMSWorld(players.get(0).getWorld()), block.newInstance(x, y, z), lines);
            for (Player p : players) {
                super.sendPacket(p, packet);
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private Object getNMSWorld(World world) {
        if (worlds.contains(world.getName())) return worlds.get(world.getName());
        try {
            Method getHandle = world.getClass().getMethod("getHandle");
            Object nmsWorld = getHandle.invoke(world);
            Method worldto = nmsWorld.getClass().getMethod("b");
            Object toreturn = worldto.invoke(nmsWorld);
            worlds.put(world.getName(), toreturn);
            return toreturn;
        } catch(Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getLines(List<String> lines) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        Object array =  Array.newInstance(baseComponent, 4);
        for (int x = 0; x < 4; x++){
            if (lines.size() > x) {
                Array.set(array, x, chatSerializer.invoke(null, "{\"text\":\"" + lines.get(x) + "\"}"));
            } else {
                Array.set(array, x, null);
            }
        }
        return array;
    }
}
