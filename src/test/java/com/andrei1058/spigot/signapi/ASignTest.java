package com.andrei1058.spigot.signapi;

import be.seeseemelk.mockbukkit.Coordinate;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.util.Objects;

public class ASignTest extends RunListener {

    private SignAPI api;

    private ServerMock server;
    private Block b;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        api = new SignAPI(null);
        WorldMock w = new WorldMock();
        b = w.createBlock(new Coordinate(5, 5, 5));
    }

    @Test
    public void test() {
        api.createSignA(b).setClickListener((p, a) -> System.out.println(p.getName() + " clicked on the sign: " + a.name()));

        SignListener listener = new SignListener(api);
        PlayerMock p = new PlayerMock(server, "andrei1058");
        listener.onInteract(new PlayerInteractEvent(p, Action.RIGHT_CLICK_BLOCK, null, b, Objects.requireNonNull(b.getFace(b))));
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }
}
