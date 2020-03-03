package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SignAPI {

    private LinkedList<ASign> signs = new LinkedList<>();

    /**
     * Initialize the signs lib.
     * This will register a listener on your plugin so it will handle {@link ASignEvent}.
     */
    public SignAPI(Plugin client) {
        if (client != null) {
            Bukkit.getServer().getPluginManager().registerEvents(new SignListener(this), client);
        }
    }

    /**
     * Create a sign at the target block.
     * The block must be already a sign-block.
     *
     * @return new {@link ASign} instance.
     */
    public ASign createSign(Block block) {
        ASign a = new ASign(block);
        signs.add(a);
        return a;
    }

    /**
     * Unmodifiable signs list.
     *
     * @return unmodifiable signs list.
     */
    public List<ASign> getSigns() {
        return Collections.unmodifiableList(signs);
    }

    /**
     * Add a sign to the list.
     *
     * @param sign - sign to be added.
     * @return {@code true} (as specified by {@link Collection#add(Object)})
     */
    public boolean addSign(ASign sign) {
        return signs.add(sign);
    }

    /**
     * Remove a sign from the list.
     *
     * @param sign - sign to be removed.
     * @return {@code true} (as specified by {@link Collection#remove(Object)})
     */
    public boolean removeSign(ASign sign) {
        return signs.remove(sign);
    }
}
