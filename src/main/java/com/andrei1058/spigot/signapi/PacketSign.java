package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public class PacketSign {

    private String world = null;
    private Vector loc = null;
    private SignClickEvent event = null;
    private Function<Player, List<String>> content = null;
    private int minX, maxX, minZ, maxZ;

    /**
     * Create a new instance.
     * This is managed internally.
     * Instantiate {@link SpigotSignAPI} and then use {@link SpigotSignAPI#createSign(Block)} (Block)}
     *
     * @param signBlock block.
     */
    protected PacketSign(Block signBlock) {
        if (signBlock == null) return;
        this.world = signBlock.getWorld().getName();
        this.loc = new Vector(signBlock.getX(), signBlock.getY(), signBlock.getZ());
        Location l1 = signBlock.getLocation().clone().add(40, 40, 40),
                l2 = signBlock.getLocation().clone().subtract(40, 40, 40);
        this.maxX = (int) Math.max(l1.getX(), l2.getX());
        this.minX = (int) Math.min(l1.getX(), l2.getX());
        this.maxZ = (int) Math.max(l1.getZ(), l2.getZ());
        this.minZ = (int) Math.min(l1.getZ(), l2.getZ());
    }

    /**
     * Get a sign world name.
     *
     * @return location.
     */
    public String getWorld() {
        return world;
    }

    /**
     * Get a sign location.
     * Use {@link PacketSign#getWorld()} to get the world.
     *
     * @return location.
     */
    public Vector getLocation() {
        return loc;
    }

    /**
     * Equals.
     *
     * @param sign sign.
     * @return true if the signs have the same location.
     */
    public boolean equals(PacketSign sign) {
        if (sign == null) return false;
        return sign.getWorld().equals(getWorld()) && loc.getBlockX() == sign.getLocation().getBlockX() && loc.getBlockY() == sign.getLocation().getBlockY() && loc.getBlockZ() == sign.getLocation().getBlockZ();
    }

    /**
     * Check if given block is this sign.
     *
     * @param block block to be checked.
     * @return true if the given block is this sign.
     */
    public boolean equals(Block block) {
        if (block == null) return false;
        if (!block.getType().toString().endsWith("SIGN")) return false;
        if (!(block.getState() instanceof Sign)) return false;
        if (world == null) return false;
        if (loc == null) return false;

        if (!block.getWorld().getName().equals(world)) return false;
        return loc.getBlockX() == block.getLocation().getBlockX() && loc.getBlockY() == block.getLocation().getBlockY() && loc.getBlockZ() == block.getLocation().getBlockZ();
    }

    /**
     * Set content provider.
     *
     * @param content content function.
     *                This function wil be applied to a player when he will request
     *                this sign lines. It is useful for language systems.
     */
    public void setContent(Function<Player, List<String>> content) {
        this.content = content;
    }

    /**
     * This function is applied when sign content is requested internally.
     *
     * @return sign content.
     */
    public Function<Player, List<String>> getContent() {
        return content;
    }

    /**
     * Set click listener.
     *
     * @param event listener.
     */
    public void setClickListener(SignClickEvent event) {
        this.event = event;
    }

    /**
     * Get click listener.
     *
     * @return click listener for that sign.
     */
    public SignClickEvent getClickListener() {
        return event;
    }

    /**
     * Refresh sign for nearby users.
     */
    public void refresh() {
        final World w = Bukkit.getWorld(world);
        if (w == null) return;
        for (Player p : w.getPlayers()) {
            if (isInRange(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) {
                SpigotSignAPI.signVersion.update(p, new Location(w, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ()), getContent().apply(p));
            }
        }
    }

    public interface SignClickEvent {

        /**
         * Handle interact event.
         *
         * @param player player.
         * @param action click action.
         */
        void onInteract(Player player, Action action);
    }

    public boolean isInRange(int x, int z){
        return maxX >= x && minX <= x && minZ <= z && maxZ >= z;
    }
}
