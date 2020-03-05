package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Function;

public class ASign implements SignBoard {

    private String world = null;
    private Vector loc = null;
    private SignBoardClickEvent event = null;
    private Function<Player, List<String>> content = null;
    private SignVersion signVersion;

    /**
     * Create a new instance.
     * This is managed internally.
     * Instantiate {@link SignAPI} and then use {@link SignAPI#createSignA(Block)}
     *
     * @param signBlock   block.
     * @param signVersion nms support.
     */
    protected ASign(Block signBlock, SignVersion signVersion) {
        if (signBlock == null) return;
        this.world = signBlock.getWorld().getName();
        this.loc = new Vector(signBlock.getX(), signBlock.getY(), signBlock.getZ());
        this.signVersion = signVersion;
    }

    @Override
    public String getWorld() {
        return world;
    }

    @Override
    public Vector getLocation() {
        return loc;
    }

    @Override
    public boolean equals(SignBoard sign) {
        if (sign == null) return false;
        return sign.getWorld().equals(getWorld()) && loc.getX() == sign.getLocation().getX() && loc.getY() == sign.getLocation().getY() && loc.getZ() == sign.getLocation().getZ();
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

    @Override
    public void setContent(Function<Player, List<String>> content) {
        this.content = content;
    }

    @Override
    public Function<Player, List<String>> getContent() {
        return content;
    }

    @Override
    public void setClickListener(SignBoardClickEvent event) {
        this.event = event;
    }

    @Override
    public SignBoardClickEvent getClickListener() {
        return event;
    }

    @Override
    public void refresh() {
        World w = Bukkit.getWorld(world);
        if (w == null) return;
        for (Player p : w.getPlayers()) {
            signVersion.update(p, (int) loc.getX(), (int) loc.getY(), (int) loc.getZ(), getContent().apply(p));
        }
    }
}
