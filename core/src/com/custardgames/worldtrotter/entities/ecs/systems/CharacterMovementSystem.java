package com.custardgames.worldtrotter.entities.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.custardgames.worldtrotter.entities.ecs.components.Box2dBodyComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterDataComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterFeetComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterInputComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterLegComponent;
import com.custardgames.worldtrotter.events.BeginContactEvent;
import com.custardgames.worldtrotter.events.ChangeWorldEvent;
import com.custardgames.worldtrotter.events.NextLevelEvent;
import com.custardgames.worldtrotter.events.PlayerDiedEvent;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.EventListener;

public class CharacterMovementSystem extends EntityProcessingSystem implements EventListener {
    ComponentMapper<Box2dBodyComponent> bodyComponents;
    ComponentMapper<CharacterInputComponent> characterInputComponents;
    ComponentMapper<CharacterFeetComponent> characterFeetComponents;
    ComponentMapper<CharacterLegComponent> characterLegComponents;
    ComponentMapper<CharacterDataComponent> characterDataComponents;

    @SuppressWarnings("unchecked")
    public CharacterMovementSystem() {
        super(Aspect.all(Box2dBodyComponent.class, CharacterInputComponent.class, CharacterFeetComponent.class, CharacterLegComponent.class, CharacterDataComponent.class));
        EventManager.getInstance().register(BeginContactEvent.class, this);
    }

    @Override
    protected void process(Entity e) {
        Body body = bodyComponents.get(e).getBody();
        CharacterInputComponent characterInput = characterInputComponents.get(e);
        CharacterFeetComponent characterFeetComponent = characterFeetComponents.get(e);
        CharacterLegComponent characterLegComponent = characterLegComponents.get(e);
        CharacterDataComponent characterDataComponent = characterDataComponents.get(e);

        if (characterInput.isWorld1()) {
            characterDataComponent.setWorld1();
            changeFilter(19, body);
            EventManager.getInstance().broadcast(new ChangeWorldEvent(1));
        } else if (characterInput.isWorld2()) {
            characterDataComponent.setWorld2();
            changeFilter(21, body);
            EventManager.getInstance().broadcast(new ChangeWorldEvent(2));
        } else if (characterInput.isWorld3()) {
            characterDataComponent.setWorld3();
            changeFilter(25, body);
            EventManager.getInstance().broadcast(new ChangeWorldEvent(3));
        }

        double force = 100;

        if (characterFeetComponent.isOnGround() && !characterLegComponent.isOnGround()) {
            if (characterInput.isUp() && body.getLinearVelocity().y < 15) {
                body.applyForceToCenter((int) (0), (int) (1500), true);
            }
        }

        if (characterInput.isLeft() && !characterLegComponent.isOnGround()) {
            if (body.getLinearVelocity().x > 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
            if (body.getLinearVelocity().x > -10) {
                body.applyForceToCenter((int) (-1 * force), (int) (0), true);
            }
        } else if (characterInput.isRight() && !characterLegComponent.isOnGround()) {
            if (body.getLinearVelocity().x < 0) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
            if (body.getLinearVelocity().x < 10) {
                body.applyForceToCenter((int) (1 * force), (int) (0), true);
            }
        } else if (characterFeetComponent.isOnGround()) {
            body.setLinearVelocity((float) (body.getLinearVelocity().x * 0.75), body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity((float) (body.getLinearVelocity().x * 0.95), body.getLinearVelocity().y);
        }

        if (Math.abs(body.getLinearVelocity().x) < 0.01) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    private void changeFilter(int filterBits, Body body) {
        for (Fixture f : body.getFixtureList()) {
            Filter filter = f.getFilterData();
            filter.maskBits = (short) filterBits;
            f.setFilterData(filter);
        }
    }

    public void handleBeginContactEvent(BeginContactEvent e)
    {
        ImmutableBag<Entity> entities = getEntities();
        for (int x = 0; x < entities.size(); x++)
        {
            Body body = bodyComponents.get(entities.get(x)).getBody();
            if (e.getFa().getBody() == body || e.getFb().getBody() == body)
            {
                if (e.getFa().getBody() == body)
                {
                    if (e.getFb().getBody().getUserData().equals("spikes"))
                    {
                        EventManager.getInstance().broadcast(new PlayerDiedEvent());
                    }
                    else if (e.getFb().getBody().getUserData().equals("nextlevel"))
                    {
                        EventManager.getInstance().broadcast(new NextLevelEvent());
                    }
                }
                else
                {
                    if (e.getFa().getBody().getUserData().equals("spikes"))
                    {
                        EventManager.getInstance().broadcast(new PlayerDiedEvent());
                    }
                    else if (e.getFa().getBody().getUserData().equals("nextlevel"))
                    {
                        EventManager.getInstance().broadcast(new NextLevelEvent());
                    }
                }
            }
        }
    }
}
