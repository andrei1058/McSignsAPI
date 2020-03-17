package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SpigotSignAPI {

    private LinkedList<PacketSign> signs = new LinkedList<>();
    protected static SignVersion signVersion;
    protected Plugin client = null;

    /**
     * Initialize the signs lib.
     * This will register a listener on your plugin so it will handle {@link com.andrei1058.spigot.signapi.PacketSign.SignClickEvent}.
     */
    public SpigotSignAPI(Plugin client) {
        if (client != null) {
            this.client = client;
            Bukkit.getServer().getPluginManager().registerEvents(new SignListener(this), client);
        }
        if (signVersion == null) {
            try {
                signVersion = new v1_8_R3();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a sign at the target block.
     * The block must be already a sign-block.
     *
     * @return new {@link PacketSign} instance.
     */
    public PacketSign createSign(Block block) {
        PacketSign a = new PacketSign(block);
        signs.add(a);
        return a;
    }

    /**
     * Unmodifiable signs list.
     *
     * @return unmodifiable signs list.
     */
    public List<PacketSign> getSigns() {
        return Collections.unmodifiableList(signs);
    }

    /**
     * Add a sign to the list.
     *
     * @param sign - sign to be added.
     * @return {@code true} (as specified by {@link Collection#add(Object)})
     */
    public boolean addSign(PacketSign sign) {
        return signs.add(sign);
    }

    /**
     * Remove a sign from the list.
     *
     * @param sign - sign to be removed.
     * @return {@code true} (as specified by {@link Collection#remove(Object)})
     */
    public boolean removeSign(PacketSign sign) {
        return signs.remove(sign);
    }

    protected SignVersion getSignVersion() {
        return signVersion;
    }
}
