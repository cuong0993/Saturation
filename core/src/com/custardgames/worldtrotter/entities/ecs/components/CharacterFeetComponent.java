package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.custardgames.worldtrotter.events.BeginContactEvent;
import com.custardgames.worldtrotter.events.EndContactEvent;
import com.custardgames.worldtrotter.managers.EventManager;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class CharacterFeetComponent extends Component implements EventListener {
    private List<Fixture> collisions;
    private Fixture feet;

    public CharacterFeetComponent() {

    }

    public CharacterFeetComponent(Fixture feet) {
        this.feet = feet;
        collisions = new ArrayList<Fixture>();

        EventManager.getInstance().register(BeginContactEvent.class, this);
        EventManager.getInstance().register(EndContactEvent.class, this);
    }

    public boolean isOnGround() {
        return !collisions.isEmpty();
    }

    public void handleBeginContactEvent(BeginContactEvent e) {
        if (e.getFa() == feet || e.getFb() == feet) {
            if (e.getFa() != feet) {
                if (e.getFa().getUserData() == null || !e.getFa().getUserData().equals("light"))
                    collisions.add(e.getFa());
            } else {
                if (e.getFb().getUserData() == null || !e.getFb().getUserData().equals("light"))
                    collisions.add(e.getFb());
            }
        }
    }

    public void handleEndContactEvent(EndContactEvent e) {
        if (e.getFa() == feet || e.getFb() == feet) {
            if (e.getFa() != feet) {
                if (e.getFa().getUserData() == null || !e.getFa().getUserData().equals("light"))
                    collisions.remove(e.getFa());
            } else {
                if (e.getFb().getUserData() == null || !e.getFb().getUserData().equals("light"))
                    collisions.remove(e.getFb());
            }
        }
    }
}
