package com.nikmoores.android.trafficlightsimulation;

/**
 * Created by Nik on 13/01/2016.
 */
public class LightController implements Light.Callback {
    Light light1;
    Light light2;

    LightController() {
        initLights();
    }

    public void initLights() {
        // make one green, make sure the other is red
        light1 = new Light(this, Light.STANDARD_GREEN_TIME, Light.STANDARD_YELLOW_TIME);
        light2 = new Light(this, Light.STANDARD_GREEN_TIME, Light.STANDARD_YELLOW_TIME);
        light1.setToGreen();
    }

    /**
     * Tick the lights through their signaling states. When one light sequence reaches its end, both
     * lights remain RED for one second, before the new light sequence starts (as per the
     * conditions).
     */
    public void advanceOneSecond() {
        light1.advanceOneSecond();
        light2.advanceOneSecond();
        // wait 1 second.
    }

    @Override
    public void onRedLightReady(Light light) {
        if (light.equals(light1)) {         // If the first light is red, trigger the second
            light2.setToGreen();
        } else if (light.equals(light2)) {  // Check the same for the other light (in case there's a
            light1.setToGreen();            // potential for other lights).

            // Step light1 through a second, as light2 always triggers at the end of the tick
            // (effectively adding 1s to light1's GREEN time).
            light1.advanceOneSecond();
        }
    }

    public String getSystemStateAsString() {
        return "light1-" + light1.toString() + ", light2-" + light2.toString();
    }

    public Light getLight1() {
        return light1;
    }

    public Light getLight2() {
        return light2;
    }

    public void run() {
//        while(true){
//            //....endlessly cycle the lights
//            advanceOneSecond();
//        }
    }
}
