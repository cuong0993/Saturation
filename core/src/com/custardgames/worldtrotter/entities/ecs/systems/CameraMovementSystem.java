package com.custardgames.worldtrotter.entities.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.custardgames.worldtrotter.entities.ecs.components.Box2dCameraComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CameraInputComponent;
import com.custardgames.worldtrotter.events.physicalinput.KeyPressedEvent;
import com.custardgames.worldtrotter.events.physicalinput.KeyReleasedEvent;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.EventListener;
import java.util.Random;

public class CameraMovementSystem extends EntityProcessingSystem implements EventListener {
    ComponentMapper<Box2dCameraComponent> cameraComponent;
    ComponentMapper<CameraInputComponent> cameraInputComponent;

    @SuppressWarnings("unchecked")
    public CameraMovementSystem() {
        super(Aspect.all(Box2dCameraComponent.class, CameraInputComponent.class));

        EventManager.get_instance().register(KeyPressedEvent.class, this);
        EventManager.get_instance().register(KeyReleasedEvent.class, this);

    }

    @Override
    protected void process(Entity e) {
        Box2dCameraComponent camera = cameraComponent.get(e);
        CameraInputComponent cameraInput = cameraInputComponent.get(e);

        float targetX = cameraInput.getTargetX();
        float targetY = cameraInput.getTargetY();

        if (cameraInput.getTargetBody() != null) {
            targetX = cameraInput.getTargetBody().getWorldCenter().x;
            targetY = cameraInput.getTargetBody().getWorldCenter().y;
        }

        camera.getCamera().position.x = camera.getCamera().position.x + (float) ((targetX - camera.getCamera().position.x) / 30) + cameraInput.getTargetOffsetX();
        camera.getCamera().position.y = camera.getCamera().position.y + (float) ((targetY - camera.getCamera().position.y) / 30) + cameraInput.getTargetOffsetY();

        if (Math.abs(camera.getRotation() - cameraInput.getTargetRotation()) < Math.toRadians(1)) {
            cameraInput.setTargetRotation((float) (new Random().nextFloat() * 2 - 1));
        } else {
            if (camera.getRotation() - cameraInput.getTargetRotation() > 0) {
                camera.getCamera().rotate((float) Math.toRadians(-0.05));
                camera.setRotation(camera.getRotation() + (float) Math.toRadians(-0.05));
            } else {
                camera.getCamera().rotate((float) Math.toRadians(0.05));
                camera.setRotation(camera.getRotation() + (float) Math.toRadians(0.05));
            }
        }

        if (cameraInput.isReset()) {
            cameraInput.setTargetOffsetX(0);
            cameraInput.setTargetOffsetY((float) (2 * 0.03125));
            camera.getCamera().position.x = targetX;
            camera.getCamera().position.y = targetY;
            camera.getCamera().zoom = 0.03125f;
            cameraInput.setReset(false);
        }

        camera.getCamera().update();
    }

    public void handleKeyPressed(KeyPressedEvent keyEvent)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            CameraInputComponent cameraInput = cameraInputComponent.get(entities.get(x));

            if (keyEvent.getKeyCode() == 152)
            {
                cameraInput.setUp(true);
            }
            else if (keyEvent.getKeyCode() == 146)
            {
                cameraInput.setDown(true);
            }

            if (keyEvent.getKeyCode() == 148)
            {
                cameraInput.setLeft(true);
            }
            else if (keyEvent.getKeyCode() == 150)
            {
                cameraInput.setRight(true);
            }

            if (keyEvent.getKeyCode() == 151)
            {
                cameraInput.setZoomIn(true);
            }
            else if (keyEvent.getKeyCode() == 153)
            {
                cameraInput.setZoomOut(true);
            }

            if (keyEvent.getKeyCode() == 149)
            {
                cameraInput.setReset(true);
            }
        }
    }

    public void handleKeyReleased(KeyReleasedEvent keyEvent)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            CameraInputComponent cameraInput = cameraInputComponent.get(entities.get(x));

            if (keyEvent.getKeyCode() == 152)
            {
                cameraInput.setUp(false);
            }
            else if (keyEvent.getKeyCode() == 146)
            {
                cameraInput.setDown(false);
            }

            if (keyEvent.getKeyCode() == 148)
            {
                cameraInput.setLeft(false);
            }
            else if (keyEvent.getKeyCode() == 150)
            {
                cameraInput.setRight(false);
            }

            if (keyEvent.getKeyCode() == 151)
            {
                cameraInput.setZoomIn(false);
            }
            else if (keyEvent.getKeyCode() == 153)
            {
                cameraInput.setZoomOut(false);
            }

            if (keyEvent.getKeyCode() == 149)
            {
                cameraInput.setReset(false);
            }
        }
    }
}
