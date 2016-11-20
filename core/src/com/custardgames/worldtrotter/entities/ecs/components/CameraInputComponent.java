package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class CameraInputComponent extends Component {
    private Body targetBody;
    private int targetX, targetY;
    private float targetOffsetX, targetOffsetY;
    private float targetRotation;
    private boolean shake;
    private boolean up, down, left, right, zoomIn, zoomOut, reset;

    public CameraInputComponent() {
        reset = true;
    }

    public CameraInputComponent(Body targetBody) {
        setTargetBody(targetBody);
        reset = true;
    }

    public Body getTargetBody() {
        return targetBody;
    }

    public void setTargetBody(Body targetBody) {
        this.targetBody = targetBody;
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

    public float getTargetOffsetX() {
        return targetOffsetX;
    }

    public void setTargetOffsetX(float targetOffsetX) {
        this.targetOffsetX = targetOffsetX;
    }

    public float getTargetOffsetY() {
        return targetOffsetY;
    }

    public void setTargetOffsetY(float targetOffsetY) {
        this.targetOffsetY = targetOffsetY;
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

    public boolean isZoomIn() {
        return zoomIn;
    }

    public void setZoomIn(boolean zoomIn) {
        this.zoomIn = zoomIn;
    }

    public boolean isZoomOut() {
        return zoomOut;
    }

    public void setZoomOut(boolean zoomOut) {
        this.zoomOut = zoomOut;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public float getTargetRotation() {
        return targetRotation;
    }

    public void setTargetRotation(float targetRotation) {
        this.targetRotation = targetRotation;
    }

    public boolean isShake() {
        return shake;
    }

    public void setShake(boolean shake) {
        this.shake = shake;
    }


}
