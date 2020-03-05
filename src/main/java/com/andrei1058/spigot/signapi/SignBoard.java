package com.andrei1058.spigot.signapi;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public interface SignBoard extends Refreshable{

    /**
     * Get a sign world name.
     *
     * @return location.
     */
    String getWorld();

    /**
     * Get a sign location.
     * Use {@link SignBoard#getWorld()} to get the world.
     *
     * @return location.
     */
    Vector getLocation();

    /**
     * Equals.
     *
     * @return true if the signs have the same location.
     */
    boolean equals(SignBoard sign);

    /**
     * Check if the the given block is this sign.
     *
     * @param block block.
     * @return true if the given block is this sign.
     */
    boolean equals(Block block);

    /**
     * Set content provider.
     * This function wil be applied to a player when he will request
     * this sign lines. It is useful for language systems.
     */
    void setContent(Function<Player, List<String>> content);

    /**
     * This function is applied when sign content is requested internally.
     *
     * @return sign content.
     */
    Function<Player, List<String>> getContent();

    /**
     * Set click listener.
     */
    void setClickListener(SignBoardClickEvent event);

    /**
     * Get click listener.
     *
     * @return click listener for that sign.
     */
    SignBoardClickEvent getClickListener();
}
