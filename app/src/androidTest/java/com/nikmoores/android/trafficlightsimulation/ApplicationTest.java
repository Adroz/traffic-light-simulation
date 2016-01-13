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


    // after boot, the green light stays green for 20 seconds, whilst the other light stays red
    class TestSuite {
        public void runSuite() {
            testGreenLightStaysGreenFor20Seconds();
        }

        private void testGreenLightStaysGreenFor20Seconds() {
            // initialize the system
            // for 1-20 seconds
            // -- read the state of each of the two lights
            // -- assert that the first light stays green for 20 seconds, whilst the second light stays red
        }
    }
}