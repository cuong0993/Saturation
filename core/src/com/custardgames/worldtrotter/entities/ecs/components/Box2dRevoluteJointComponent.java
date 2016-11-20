package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

public class Box2dRevoluteJointComponent extends Component {
    private RevoluteJoint joint;
    private double targetAngle;

    public Box2dRevoluteJointComponent() {
    }

    public Box2dRevoluteJointComponent(RevoluteJoint joint) {
        this.joint = joint;
    }

    public RevoluteJoint getJoint() {
        return joint;
    }

    public void setJoint(RevoluteJoint joint) {
        this.joint = joint;
    }

    public double getTargetAngle() {
        return targetAngle;
    }

    public void setTargetAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }
}
