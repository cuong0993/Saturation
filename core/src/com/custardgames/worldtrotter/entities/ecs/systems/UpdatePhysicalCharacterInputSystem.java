package com.custardgames.worldtrotter.entities.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterInputComponent;
import com.custardgames.worldtrotter.events.physicalinput.KeyPressedEvent;
import com.custardgames.worldtrotter.events.physicalinput.KeyReleasedEvent;
import com.custardgames.worldtrotter.events.physicalinput.MousePressedEvent;
import com.custardgames.worldtrotter.events.physicalinput.MouseReleasedEvent;
import com.custardgames.worldtrotter.handlers.InputHandler;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.EventListener;

public class UpdatePhysicalCharacterInputSystem extends EntityProcessingSystem implements EventListener {
    private ComponentMapper<CharacterInputComponent> characterInputComponent;
    private OrthographicCamera camera;

    @SuppressWarnings("unchecked")
    public UpdatePhysicalCharacterInputSystem() {
        super(Aspect.all(CharacterInputComponent.class));

        EventManager.getInstance().register(KeyPressedEvent.class, this);
        EventManager.getInstance().register(KeyReleasedEvent.class, this);
        EventManager.getInstance().register(MousePressedEvent.class, this);
        EventManager.getInstance().register(MouseReleasedEvent.class, this);
    }

    @Override
    protected void process(Entity e) {
        InputHandler input = (InputHandler) Gdx.input.getInputProcessor();

        if (camera != null) {
            characterInputComponent.get(e).setTargetX((int) (camera.unproject(new Vector3(input.getMouseX(), input.getMouseY(), 0)).x));
            characterInputComponent.get(e).setTargetY((int) (camera.unproject(new Vector3(input.getMouseX(), input.getMouseY(), 0)).y));
        } else {
            characterInputComponent.get(e).setTargetX(input.getMouseX());
            characterInputComponent.get(e).setTargetY(input.getMouseY());
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void updateKBInput(int keyCode, boolean pressed, CharacterInputComponent component) {
        if (keyCode == 51 || keyCode == 19) {
            component.setUp(pressed);
        } else if (keyCode == 47 || keyCode == 20) {
            component.setDown(pressed);
        } else if (keyCode == 29 || keyCode == 21) {
            component.setLeft(pressed);
        } else if (keyCode == 32 || keyCode == 22) {
            component.setRight(pressed);
        } else if (keyCode == 8 || keyCode == 145) {
            component.setWorld1(pressed);
        } else if (keyCode == 9 || keyCode == 146) {
            component.setWorld2(pressed);
        } else if (keyCode == 10 || keyCode == 147) {
            component.setWorld3(pressed);
        }
    }

    public void updateMouseInput(int buttonNumber, boolean pressed, CharacterInputComponent component) {

    }

    public void handleKeyPressed(KeyPressedEvent keyEvent)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            updateKBInput(keyEvent.getKeyCode(), true, characterInputComponent.get(entities.get(x)));
        }
    }

    public void handleKeyReleased(KeyReleasedEvent keyEvent)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            updateKBInput(keyEvent.getKeyCode(), false, characterInputComponent.get(entities.get(x)));
        }
    }

    public void handleMousePressed(MousePressedEvent event)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            updateMouseInput(event.getButtonNumber(), true, characterInputComponent.get(entities.get(x)));
        }
    }

    public void handleMouseReleased(MouseReleasedEvent event)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            updateMouseInput(event.getButtonNumber(), false, characterInputComponent.get(entities.get(x)));
        }
    }
}
