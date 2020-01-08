package xyz.nasaknights.infiniterecharge.util.controller;

public enum PS4ControllerMappings {

    //TODO Verify IDs

    //buttons
    X(1),
    CIRCLE(2),
    SQUARE(3),
    TRIANGLE(4),
    RIGHT_BUMPER(6),
    LEFT_BUMPER(5),
    SHARE(7),
    OPTIONS(8),
    LEFT_JOYSTICK(9),
    RIGHT_JOYSTICK(10),

    //axes
    LEFT_X_AXIS(0),
    LEFT_Y_AXIS(1),
    LEFT_TRIGGER(2),
    RIGHT_TRIGGER(3),
    RIGHT_X_AXIS(4),
    RIGHT_Y_AXIS(5);

    private int id;
        
    PS4ControllerMappings(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

}
