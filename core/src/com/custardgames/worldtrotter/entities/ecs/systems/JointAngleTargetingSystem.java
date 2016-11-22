package com.custardgames.worldtrotter.entities.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.custardgames.worldtrotter.entities.ecs.components.Box2dRevoluteJointComponent;

public class JointAngleTargetingSystem extends EntityProcessingSystem {
    private ComponentMapper<Box2dRevoluteJointComponent> jointComponents;

    @SuppressWarnings("unchecked")
    public JointAngleTargetingSystem() {
        super(Aspect.all(Box2dRevoluteJointComponent.class));
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void process(Entity e) {
        Box2dRevoluteJointComponent jointComponent = jointComponents.get(e);
        RevoluteJoint joint = jointComponent.getJoint();

        if (joint.getJointAngle() > 0.01) {
            joint.setMotorSpeed((float) -10000);
            joint.setMaxMotorTorque(10000000);
        } else if (joint.getJointAngle() < -0.01) {
            joint.setMotorSpeed((float) 10000);
            joint.setMaxMotorTorque(10000000);
        } else {
            joint.setMotorSpeed(0);
            joint.setMaxMotorTorque(0.1f);
        }

    }
}
