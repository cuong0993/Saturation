package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Box2dCameraComponent extends Component {
    private OrthographicCamera camera;
    private float rotation;

    public Box2dCameraComponent() {

    }

    public Box2dCameraComponent(OrthographicCamera camera) {
        this.setCamera(camera);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
