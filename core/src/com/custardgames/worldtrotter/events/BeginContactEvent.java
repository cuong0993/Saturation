package com.custardgames.worldtrotter.events;

import com.badlogic.gdx.physics.box2d.Fixture;

public class BeginContactEvent {
    private Fixture fa;
    private Fixture fb;

    public BeginContactEvent(Fixture fa, Fixture fb) {
        setFa(fa);
        setFb(fb);
    }

    public Fixture getFa() {
        return fa;
    }

    public void setFa(Fixture fa) {
        this.fa = fa;
    }

    public Fixture getFb() {
        return fb;
    }

    public void setFb(Fixture fb) {
        this.fb = fb;
    }


}
