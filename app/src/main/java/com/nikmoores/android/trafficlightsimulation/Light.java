package com.nikmoores.android.trafficlightsimulation;

/**
 * Created by Nik on 13/01/2016.
 */
public class Light {

    public static final int STANDARD_GREEN_TIME = 20;
    public static final int STANDARD_YELLOW_TIME = 5;

    State state;
    int greenTime;
    int yellowTime;
    int timeRemaining;

    public Light(int greenTime, int yellowTime) {
        state = State.RED;
        this.greenTime = greenTime;
        this.yellowTime = yellowTime;
        timeRemaining = 0;
    }


    public void setToGreen() {
        timeRemaining = greenTime;
        state = State.GREEN;
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
                    state = State.RED;
                }
                break;
            default:
                return;
        }
        timeRemaining--;
    }

    public State getState(){
        return state;
    }

    public String toString(){
        switch (state){
            case GREEN:
                return "green";
            case YELLOW:
                return "yellow";
            default:
                return "red";
        }
    }
}
