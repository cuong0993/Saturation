package com.custardgames.worldtrotter.events;

public class ChangeWorldEvent {
    private int world;

    public ChangeWorldEvent(int world) {
        this.world = 1;

        if (world == 1 || world == 2 || world == 3) {
            this.world = world;
        }
    }

    public int getWorld() {
        return world;
    }

    public void setWorld(int world) {
        this.world = world;
    }
}
