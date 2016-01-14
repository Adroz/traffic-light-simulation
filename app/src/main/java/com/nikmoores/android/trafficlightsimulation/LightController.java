package com.nikmoores.android.trafficlightsimulation;

/**
 * Created by Nik on 13/01/2016.
 */
public class LightController {
    Light light1;
    Light light2;

    public static final int STANDARD_RED_TIME = 1;
    public boolean light1Running;
    public int redCounter = 0;

    public void initLights() {
        // make one green, make sure the other is red
        light1 = new Light(Light.STANDARD_GREEN_TIME, Light.STANDARD_YELLOW_TIME);
        light2 = new Light(Light.STANDARD_GREEN_TIME, Light.STANDARD_YELLOW_TIME);
        light1.setToGreen();
        light1Running = true;
    }

    /**
     * Tick the lights through their signaling states. When one light sequence reaches its end, both
     * lights remain RED for one second, before the new light sequence starts (as per the
     * conditions).
     */
    public void advanceOneSecond() {
        light1.advanceOneSecond();
        light2.advanceOneSecond();
        // Check which light is currently running through its sequence
        if(light1Running){
            if(light1.getState() == State.RED){
                // wait one more second before switching lights.
                if (redCounter == STANDARD_RED_TIME) {
                    redCounter = 0;
                    light2.setToGreen();
                    light1Running = false;
                } else {
                    redCounter++;
                }
            }
        }else{
            if(light2.getState() == State.RED){
                // wait one more second before switching lights.
                if (redCounter == STANDARD_RED_TIME) {
                    redCounter = 0;
                    light1.setToGreen();
                    light1Running = true;
                } else {
                    redCounter++;
                }
            }
        }
        // wait 1 second.

        // Add some kind of error check here in case both lights are a combination of GREEN and YELLOW??
    }

    public String getSystemStateAsString() {
        return "light1-" + light1.toString() + ", light2-" + light2.toString();
    }

    public Light getLight1(){
        return light1;
    }

    public Light getLight2(){
        return light2;
    }

    public void run() {
//        while(true){
//            //....endlessly cycle the lights
//            advanceOneSecond();
//        }
    }
}
