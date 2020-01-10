package xyz.nasaknights.infiniterecharge.util.controllers;

public enum XBoxControllerMappings {

    //TODO get real ID numbers

    //axes
    LEFT_X_AXIS(0),
    LEFT_Y_AXIS(0),
    LEFT_TRIGGER(0),
    RIGHT_TRIGGER(0),
    RIGHT_X_AXIS(0),
    RIGHT_Y_AXIS(0),

    //buttons
    A(0),
    B(0),
    X(0),
    Y(0),
    LEFT_BUMPER(0),
    RIGHT_BUMPER(0),
    BACK(0),
    START(0);

    private int id;

    XBoxControllerMappings(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
