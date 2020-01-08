package xyz.nasaknights.infiniterecharge.util.controller;

public enum LogitechControllerMappings {

    //TODO Verify IDs

    //buttons
    X(1),
    A(2),
    B(3),
    Y(4),
    LEFT_BUMPER(5),
    RIGHT_BUMPER(6),
    LEFT_TRIGGER(7),
    RIGHT_TRIGGER(8),
    BACK(9),
    START(10),
    LEFT_JOYSTICK(11),
    RIGHT_JOYSTICK(12),

    //axes
    LEFT_X_AXIS(0),
    LEFT_Y_AXIS(1),
    RIGHT_X_AXIS(2),
    RIGHT_Y_AXIS(3);

    private int id;

    LogitechControllerMappings(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }
}