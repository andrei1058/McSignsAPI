package com.andrei1058.spigot.signapi;

import org.bukkit.entity.Player;

public interface ASignEvent {

    /**
     * Handle interact event.
     *
     * @param player player.
     */
    void onInteract(Player player);
}
