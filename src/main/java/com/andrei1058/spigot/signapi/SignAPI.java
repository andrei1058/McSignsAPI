package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SignAPI {

    private LinkedList<SignBoard> signs = new LinkedList<>();
    protected static SignVersion signVersion;

    /**
     * Initialize the signs lib.
     * This will register a listener on your plugin so it will handle {@link SignBoardClickEvent}.
     */
    public SignAPI(Plugin client) {
        if (client != null) {
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
     * @return new {@link ASign} instance.
     */
    public ASign createSignA(Block block) {
        ASign a = new ASign(block);
        signs.add(a);
        return a;
    }

    /**
     * Unmodifiable signs list.
     *
     * @return unmodifiable signs list.
     */
    public List<SignBoard> getSigns() {
        return Collections.unmodifiableList(signs);
    }

    /**
     * Add a sign to the list.
     *
     * @param sign - sign to be added.
     * @return {@code true} (as specified by {@link Collection#add(Object)})
     */
    public boolean addSign(SignBoard sign) {
        return signs.add(sign);
    }

    /**
     * Remove a sign from the list.
     *
     * @param sign - sign to be removed.
     * @return {@code true} (as specified by {@link Collection#remove(Object)})
     */
    public boolean removeSign(SignBoard sign) {
        return signs.remove(sign);
    }

    protected SignVersion getSignVersion() {
        return signVersion;
    }
}
