package com.custardgames.worldtrotter.events.physicalinput;


public class KeyReleasedEvent {
    private int keyCode;

    public KeyReleasedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
