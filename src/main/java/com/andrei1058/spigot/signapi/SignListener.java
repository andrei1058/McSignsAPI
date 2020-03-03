package com.andrei1058.spigot.signapi;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    private SignAPI api;

    /**
     * Register signs listener.
     *
     * @param api instance.
     */
    protected SignListener(SignAPI api) {
        this.api = api;
    }

    /**
     * Listen event.
     *
     * @param e listened event.
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e == null) return;
        if (e.getClickedBlock() == null) return;
        if (!e.getClickedBlock().getType().toString().contains("SIGN")) return;
        if (!(e.getClickedBlock().getState() instanceof Sign)) return;

        for (ASign a : api.getSigns()) {
            if (a.equals(e.getClickedBlock())) {
                e.setCancelled(true);
                if (a.getEvent() != null) {
                    a.getEvent().onInteract(e.getPlayer());
                }
                return;
            }
        }
    }
}
