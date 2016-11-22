package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;

public class CharacterInputComponent extends Component {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean space;
    private boolean shift;
    private boolean control;
    private boolean world1;
    private boolean world2;
    private boolean world3;
    private int targetX, targetY;

    public CharacterInputComponent() {
        setAllFalse();
        targetX = targetY = 0;
    }

    public void setAllFalse() {
        left = right = up = down = space = shift = control = world1 = world2 = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public boolean isShift() {
        return shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }

    public boolean isWorld1() {
        return world1;
    }

    public void setWorld1(boolean action1) {
        this.world1 = action1;
    }

    public boolean isWorld2() {
        return world2;
    }

    public void setWorld2(boolean action2) {
        this.world2 = action2;
    }

    public boolean isWorld3() {
        return world3;
    }

    public void setWorld3(boolean action3) {
        this.world3 = action3;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }
}
