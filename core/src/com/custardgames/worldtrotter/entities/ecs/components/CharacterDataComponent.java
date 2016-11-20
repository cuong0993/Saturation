package com.custardgames.worldtrotter.entities.ecs.components;

import com.artemis.Component;

public class CharacterDataComponent extends Component {
    private boolean world1;
    private boolean world2;
    private boolean world3;

    public CharacterDataComponent() {
        setWorld1();
    }

    public boolean isWorld1() {
        return world1;
    }

    public boolean isWorld2() {
        return world2;
    }

    public boolean isWorld3() {
        return world3;
    }

    public void setWorld1() {
        world1 = true;
        world2 = false;
        world3 = false;
    }

    public void setWorld2() {
        world1 = false;
        world2 = true;
        world3 = false;
    }

    public void setWorld3() {
        world1 = false;
        world2 = true;
        world3 = false;
    }
}
