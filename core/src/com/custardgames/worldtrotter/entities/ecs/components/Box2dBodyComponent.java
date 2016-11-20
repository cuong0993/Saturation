package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class Box2dBodyComponent extends Component {
    private Body body;

    public Box2dBodyComponent() {

    }

    public Box2dBodyComponent(Body body) {
        setBody(body);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
