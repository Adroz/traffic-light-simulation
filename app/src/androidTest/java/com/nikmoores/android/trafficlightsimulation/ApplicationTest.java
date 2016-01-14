package com.nikmoores.android.trafficlightsimulation;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    // Unit tests
    // upon boot, the intersection begins with one light green, one light red
    // after boot, the green light stays green for 20 seconds, whilst the other light stays red
    // after boot, the green light progresses to yellow upon the 21st second...


    public void testSuite() {
        lightProgressesThroughSequence();
    }

    /**
     * A Light object should start in the red state, and when set to green should stay green and
     * yellow for 20 and 5 seconds respectively.
     */
    private void lightProgressesThroughSequence() {
        Light light = new Light(Light.STANDARD_GREEN_TIME, Light.STANDARD_YELLOW_TIME);

        assertEquals("Light should initialize as RED", State.RED, light.getState());
        light.setToGreen();

        for (int i = 0; i < 21; i++) {
            assertEquals("Light should be GREEN at the " + i + " second", State.GREEN, light.getState());
            light.advanceOneSecond();
        }

        for (int i = 0; i < 5; i++) {
            assertEquals("Light should be YELLOW at the " + (21 + i) + " second", State.YELLOW, light.getState());
            light.advanceOneSecond();
        }

        assertEquals("Light should now be RED", State.RED, light.getState());
        light.advanceOneSecond();
        light.advanceOneSecond();
        assertEquals("Light should stay RED until commanded otherwise", State.RED, light.getState());
    }
}