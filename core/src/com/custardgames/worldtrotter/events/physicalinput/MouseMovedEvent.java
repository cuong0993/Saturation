package com.custardgames.worldtrotter.events.physicalinput;


public class MouseMovedEvent {
    private int targetX;
    private int targetY;

    public MouseMovedEvent(int targetX, int targetY) {
        setMouseEvent(targetX, targetY);
    }

    public int getMouseX() {
        return targetX;
    }

    public int getMouseY() {
        return targetY;
    }

    public void setMouseEvent(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
