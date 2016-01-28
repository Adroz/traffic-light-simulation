package com.nikmoores.android.trafficlightsimulation;

import android.util.Log;

/**
 * Created by Nik on 13/01/2016.
 */
public class LightController implements Light.Callback {

    private static final String LOG_TAG = LightController.class.getSimpleName();

    Light light1;
    Light light2;

    private boolean running;
    private final int sleepTime = 1000;              // So that one

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
        // The ordering of the lights is dependent on which light is cycling and which isn't.
        // If light1 is waiting, step light2 first in case it triggers a change, letting light1
        // trigger next.
        // If this check isn't in place then the GREEN cycle of one light is 1 second longer.
        if (light1.hasStoppedCycling()) {
            light2.advanceOneSecond();
            light1.advanceOneSecond();
        } else {
            light1.advanceOneSecond();
            light2.advanceOneSecond();
        }
    }

    @Override
    public void onRedLightReady(Light light) {
        if (light.equals(light1)) {         // If the first light is red, trigger the second
            light2.setToGreen();
        } else if (light.equals(light2)) {  // Check the same for the other light (in case there's a
            light1.setToGreen();            // potential for other lights).
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
        running = true;
        int seconds = 0;
        Log.d(LOG_TAG, "Initial settings: " + getSystemStateAsString());
        while (running) {
            advanceOneSecond();
            seconds++;

            // wait 1 second.
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG, seconds + " : " + getSystemStateAsString());

            // FIXME - For this example, stop running after 3min
            if (seconds >= 180) {
                running = false;
            }
        }
    }
}
