package com.andrei1058.spigot.signapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class SignListener implements Listener {

    private SpigotSignAPI api;

    /**
     * Register signs listener.
     *
     * @param api instance.
     */
    protected SignListener(SpigotSignAPI api) {
        this.api = api;
    }

    /**
     * Listen event.
     *
     * @param e listened event.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        if (e == null) return;
        if (e.getClickedBlock() == null) return;
        if (!e.getClickedBlock().getType().toString().contains("SIGN")) return;
        if (!(e.getClickedBlock().getState() instanceof Sign)) return;

        for (PacketSign a : api.getSigns()) {
            if (a.equals(e.getClickedBlock())) {
                if (a.getClickListener() != null) {
                    a.getClickListener().onInteract(e.getPlayer(), e.getAction());
                }
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (e.getTo() == null) return;
        if (e.getFrom().getChunk().getX() != e.getTo().getChunk().getX() || e.getFrom().getChunk().getZ() != e.getTo().getChunk().getZ()) {
            final World world = e.getPlayer().getWorld();
            for (PacketSign a : api.getSigns()) {
                if (a.getWorld().equals(e.getPlayer().getWorld().getName())) {
                    if (a.isInRange(e.getTo().getBlockX(), e.getTo().getBlockZ())) {
                        api.getSignVersion().update(e.getPlayer(), new Location(world, a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()), a.getContent().apply(e.getPlayer()));
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(PlayerRespawnEvent e) {
        if (e == null) return;
        final World world = e.getPlayer().getWorld();
        Bukkit.getScheduler().runTaskLaterAsynchronously(api.client, () -> {
            for (PacketSign a : api.getSigns()) {
                if (a.getWorld().equals(e.getPlayer().getWorld().getName())) {
                    if (a.isInRange(e.getRespawnLocation().getBlockX(), e.getRespawnLocation().getBlockZ())) {
                        api.getSignVersion().update(e.getPlayer(), new Location(world, a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()), a.getContent().apply(e.getPlayer()));
                    }
                }
            }
        }, api.getDelay());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTP(PlayerTeleportEvent e) {
        if (e == null) return;
        if (e.isCancelled()) return;
        if (e.getTo() == null) return;
        final World world = e.getPlayer().getWorld();
        Bukkit.getScheduler().runTaskLater(api.client, () -> {
            for (PacketSign a : api.getSigns()) {
                if (a.getWorld().equals(e.getPlayer().getWorld().getName())) {
                    if (a.isInRange(e.getTo().getBlockX(), e.getTo().getBlockZ())) {
                        api.getSignVersion().update(e.getPlayer(), new Location(world, a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()), a.getContent().apply(e.getPlayer()));
                    }
                }
            }
        }, api.getDelay());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        if (e == null) return;
        final World world = e.getPlayer().getWorld();
        Bukkit.getScheduler().runTaskLaterAsynchronously(api.client, () -> {
            for (PacketSign a : api.getSigns()) {
                if (a.getWorld().equals(e.getPlayer().getWorld().getName())) {
                    if (a.isInRange(e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockZ())) {
                        api.getSignVersion().update(e.getPlayer(), new Location(world, a.getLocation().getBlockX(), a.getLocation().getBlockY(), a.getLocation().getBlockZ()), a.getContent().apply(e.getPlayer()));
                    }
                }
            }
        }, api.getDelay());
    }
}
