package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

public class ASign {

    private String world = null;
    private Vector loc = null;
    private ASignEvent event = null;

    /**
     * Create a new instance.
     * This is managed internally.
     * Instantiate {@link SignAPI} and then use {@link SignAPI#createSign(Block)}
     *
     * @param signBlock block.
     */
    protected ASign(Block signBlock) {
        if (signBlock == null) return;
        this.world = signBlock.getWorld().getName();
        this.loc = new Vector(signBlock.getX(), signBlock.getY(), signBlock.getZ());
    }

    /**
     * Get the sign event handler.
     *
     * @return handler.
     */
    @Nullable
    public ASignEvent getEvent() {
        return event;
    }

    /**
     * Set the event handler for this sign.
     *
     * @param event target event.
     */
    public void setEvent(ASignEvent event) {
        this.event = event;
    }

    /**
     * Refresh a sign lines.
     *
     * @param lines lines to be applied.
     * @return true if refreshed.
     */
    public boolean refresh(String... lines) {
        World w = Bukkit.getWorld(world);
        if (w == null) return false;
        Block b = w.getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        if (!equals(b)) return false;
        Sign s = (Sign) b.getState();
        for (int x = 0; x < lines.length; x++) {
            if (x == 4) break;
            s.setLine(x, lines[x]);
        }
        return s.update(true);
    }

    /**
     * Set lines.
     * This will call {@link ASign#refresh(String...)}
     *
     * @return instance.
     */
    public ASign setLines(String... lines) {
        refresh(lines);
        return this;
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
        return loc.getX() == block.getLocation().getX() && loc.getY() == block.getLocation().getY() && loc.getZ() == block.getLocation().getZ();
    }
}
