package com.custardgames.worldtrotter.events.physicalinput;


public class MouseReleasedEvent {
    private int buttonNumber;

    public MouseReleasedEvent(int buttonNumber) {
        this.setButtonNumber(buttonNumber);
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(int buttonNumber) {
        this.buttonNumber = buttonNumber;
    }
}
