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
        testBasicLightControllerFunctions();
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

    /**
     * Test the basic controller system. Lights initialize correctly (one green, one red), then
     * one light cycles while the other stays red. Both lights should then be red for one second
     * before the process starts again with the other light.
     */
    private void testBasicLightControllerFunctions() {
        // I should probably separate this test into helper functions to make it neater.
        LightController controller = new LightController();

        // Initialization test
        controller.initLights();
        String expected = "light1-green, light2-red";         // Make sure one is green and the other is red
        assertEquals("Light1 should be green, while light2 is red", expected, controller.getSystemStateAsString());

        for (int i = 0; i < 26; i++) {
            assertNotSame("Light1 shouldn't be RED at the " + i + " second",
                    State.RED, controller.getLight1().getState());
            assertEquals("Light2 should be RED during light1 changing state",
                    State.RED, controller.getLight2().getState());
            controller.advanceOneSecond();
        }
        // *** End of light1 cycle ***

        assertEquals("Lights should now both be RED (end of light1 cycle)",
                State.RED, controller.getLight1().getState());
        assertEquals("Lights should now both be RED (end of light1 cycle)",
                State.RED, controller.getLight2().getState());
        controller.advanceOneSecond();

        // *** Start of light2 cycle ***
        for (int i = 0; i < 26; i++) {
            assertEquals("Light1 should stay RED (light2 cycle running)",
                    State.RED, controller.getLight1().getState());
            assertNotSame("Light2 shouldn't be RED until end of cycle (" + (i + 1) + "s)",
                    State.RED, controller.getLight2().getState());
            controller.advanceOneSecond();
        }
        // *** End of light2 cycle ***

        assertEquals("Lights should now both be RED (end of light2 cycle)",
                State.RED, controller.getLight1().getState());
        assertEquals("Lights should now both be RED (end of light2 cycle)",
                State.RED, controller.getLight2().getState());
        controller.advanceOneSecond();

        assertEquals("Light1 should now be GREEN (light1 cycle started)",
                State.GREEN, controller.getLight1().getState());
        assertEquals("Light2 should stay RED  (light1 cycle running)",
                State.RED, controller.getLight2().getState());
    }
}