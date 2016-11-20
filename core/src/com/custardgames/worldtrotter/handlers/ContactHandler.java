package com.custardgames.worldtrotter.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.custardgames.worldtrotter.events.BeginContactEvent;
import com.custardgames.worldtrotter.events.EndContactEvent;
import com.custardgames.worldtrotter.managers.EventManager;

public class ContactHandler implements ContactListener {
    // red = 2
    // yellow = 4
    // blue = 6

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        EventManager.getInstance().broadcast(new BeginContactEvent(fa, fb));
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        EventManager.getInstance().broadcast(new EndContactEvent(fa, fb));
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub

    }

}
