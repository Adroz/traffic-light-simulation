package com.nikmoores.android.trafficlightsimulation;

/**
 * Created by Nik on 13/01/2016.
 */
public class Light {

    public static final int STANDARD_GREEN_TIME = 20;
    public static final int STANDARD_YELLOW_TIME = 5;
    public static final int STANDARD_RED_DELAY = 1;

    State state;
    int greenTime;
    int yellowTime;
    int redDelay;
    private int timeRemaining;
    Callback callback;

    public Light(Callback callback, int greenTime, int yellowTime) {
        this.callback = callback;
        state = State.RED;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
        this.redDelay = STANDARD_RED_DELAY;
        timeRemaining = -1;
    }

    public void setToGreen() {
        timeRemaining = greenTime;
        state = State.GREEN;
    }

    public boolean hasStoppedCycling() {
        return timeRemaining < 0;
    }

    // Step the light through each second.
    public void advanceOneSecond() {
        switch (state) {
            case GREEN:
                if (timeRemaining == 0) {
                    timeRemaining = yellowTime;
                    state = State.YELLOW;
                }
                break;
            case YELLOW:
                if (timeRemaining == 0) {
                    timeRemaining = redDelay;
                    state = State.RED;
                }
                break;
            default:        // RED
                if (timeRemaining == 0) {
                    callback.onRedLightReady(this);
                } else if (timeRemaining < 0) {
                    return;
                }
                break;
        }
        timeRemaining--;
    }

    public State getState() {
        return state;
    }

    public String toString() {
        switch (state) {
            case GREEN:
                return "green";
            case YELLOW:
                return "yellow";
            default:
                return "red";
        }
    }

    public interface Callback {
        /**
         * Callback for when a light has been RED for it's set delay length.
         *
         * @param light The light triggering the Callback.
         */
        void onRedLightReady(Light light);
    }
}
