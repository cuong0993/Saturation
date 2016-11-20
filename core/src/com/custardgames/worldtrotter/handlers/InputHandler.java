package com.custardgames.worldtrotter.handlers;

import com.badlogic.gdx.InputAdapter;
import com.custardgames.worldtrotter.events.physicalinput.KeyPressedEvent;
import com.custardgames.worldtrotter.events.physicalinput.KeyReleasedEvent;
import com.custardgames.worldtrotter.events.physicalinput.MousePressedEvent;
import com.custardgames.worldtrotter.events.physicalinput.MouseReleasedEvent;
import com.custardgames.worldtrotter.events.physicalinput.MouseWheelMovedEvent;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.ArrayList;
import java.util.List;

public class InputHandler extends InputAdapter {

    private List<String> kcInput;

    private int mouseX, mouseY, mouseWheelRotation;
    private boolean mouseLeft, mouseRight, mouseMiddle;

    public InputHandler() {
        init();
    }

    public void init() {
        kcInput = new ArrayList<String>();
        mouseX = mouseY = mouseWheelRotation = 0;
        mouseLeft = mouseRight = mouseMiddle = false;
    }

    public void releaseAll() {
        init();
    }

    public List<String> getKbInput() {
        return kcInput;
    }

    public boolean checkKbInput(String keyCode) {
        for (int inputIndex = 0; inputIndex < kcInput.size(); inputIndex++) {
            if (kcInput.get(inputIndex) != null) {
                if (kcInput.get(inputIndex).equals(keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean getMouseLeft() {
        return mouseLeft;
    }

    public boolean getMouseRight() {
        return mouseRight;
    }

    public boolean getMouseMiddle() {
        return mouseMiddle;
    }

    public int getWheelRotation() {
        int oldMouseWheelRotation = mouseWheelRotation;
        mouseWheelRotation = 0;
        return oldMouseWheelRotation;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!kcInput.contains(keycode)) {
            kcInput.add(keycode + "");
//			System.out.println("Adding: " + keycode);
        }
        EventManager.getInstance().broadcast(new KeyPressedEvent(keycode));

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (kcInput.contains(keycode + "")) {
            kcInput.remove(keycode + "");
//			System.out.println("Removing: " + keycode);
        }
        EventManager.getInstance().broadcast(new KeyReleasedEvent(keycode));

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0) {
            mouseLeft = true;
        }
        if (button == 1) {
            mouseRight = true;
        }
        if (button == 2) {
            mouseMiddle = true;
        }

        EventManager.getInstance().broadcast(new MousePressedEvent(button));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == 0) {
            mouseLeft = false;
        }
        if (button == 1) {
            mouseRight = false;
        }
        if (button == 2) {
            mouseMiddle = false;
        }

        EventManager.getInstance().broadcast(new MouseReleasedEvent(button));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseX = screenX;
        mouseY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        mouseWheelRotation = amount;
        EventManager.getInstance().broadcast(new MouseWheelMovedEvent(amount));
        return true;
    }


}
