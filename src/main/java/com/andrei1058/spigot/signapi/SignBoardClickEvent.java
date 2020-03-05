package com.andrei1058.spigot.signapi;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

public interface SignBoardClickEvent {

    /**
     * Handle interact event.
     *
     * @param player player.
     * @param action click action.
     */
    void onInteract(Player player, Action action);
}
