package com.custardgames.worldtrotter.events.physicalinput;


public class KeyPressedEvent {
    private int keyCode;

    public KeyPressedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
