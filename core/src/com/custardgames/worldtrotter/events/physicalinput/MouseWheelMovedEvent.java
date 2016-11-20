package com.custardgames.worldtrotter.events.physicalinput;


public class MouseWheelMovedEvent {
    private int mouseWheelAmount;

    public MouseWheelMovedEvent(int amount) {
        this.setMouseWheelAmount(amount);
    }

    public int getMouseWheelAmount() {
        return mouseWheelAmount;
    }

    public void setMouseWheelAmount(int mouseWheelAmount) {
        this.mouseWheelAmount = mouseWheelAmount;
    }
}
