package com.custardgames.worldtrotter.events.physicalinput;


public class MousePressedEvent {
    private int buttonNumber;

    public MousePressedEvent(int buttonNumber) {
        this.setButtonNumber(buttonNumber);
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public void setButtonNumber(int buttonNumber) {
        this.buttonNumber = buttonNumber;
    }


}
