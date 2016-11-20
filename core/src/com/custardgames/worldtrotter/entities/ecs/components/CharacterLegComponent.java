package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.custardgames.worldtrotter.events.BeginContactEvent;
import com.custardgames.worldtrotter.events.EndContactEvent;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class CharacterLegComponent extends Component implements EventListener {
    private List<Fixture> collisions;
    private Fixture leg;

    public CharacterLegComponent() {

    }

    public CharacterLegComponent(Fixture leg) {
        this.leg = leg;
        collisions = new ArrayList<Fixture>();

        EventManager.get_instance().register(BeginContactEvent.class, this);
        EventManager.get_instance().register(EndContactEvent.class, this);
    }

    public boolean isOnGround() {
        if (!collisions.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void handleBeginContactEvent(BeginContactEvent e) {
        if (e.getFa() == leg || e.getFb() == leg) {
            if (e.getFa() != leg) {
                if (e.getFa().getUserData() == null || !e.getFa().getUserData().equals("light"))
                    collisions.add(e.getFa());
            } else {
                if (e.getFb().getUserData() == null || !e.getFb().getUserData().equals("light"))
                    collisions.add(e.getFb());
            }
        }
    }

    public void handleEndContactEvent(EndContactEvent e) {
        if (e.getFa() == leg || e.getFb() == leg) {
            if (e.getFa() != leg) {
                if (e.getFa().getUserData() == null || !e.getFa().getUserData().equals("light"))
                    collisions.remove(e.getFa());
            } else {
                if (e.getFb().getUserData() == null || !e.getFb().getUserData().equals("light"))
                    collisions.remove(e.getFb());
            }
        }
    }
}
