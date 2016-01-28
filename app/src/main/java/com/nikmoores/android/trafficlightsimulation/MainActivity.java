package com.nikmoores.android.trafficlightsimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
tim@plangrid.com

The lighting-control job began as a 12-week university externship.

Queueable service -- command queue.
From UI to DB, you can send complex messages down the stack to the hardware level.

Example:
Variability in how they're sent.

Configuration of properties: Addresses and groups -- max of 64 lights -- brightness levels, etc.
Commands: E.g, toggling, fading, etc.  Some commands may have delays (timing is an issue).

There's some industry standard to follow.

"Scene" is a preset brightness setting for a group of lights.
*/

/*
Simulation of a simple traffic light at a 4-way intersection.
East-west road with two lanes and a north-south road with two lanes.
East-west light and the north-south light.

Controller for these two lights.
Cycle one light from green->yellow->red, then the other light.

Green: 20 seconds
Yellow: 5 seconds
Red: as long as need be, but both lights should be red for 1 second as a safety/clearing interval.

No right turn signal, no pedestrian crosswalk, no weight detectors to see if cars are sitting there, no difference (currently) in the times between east-west light and north-south light.
*/

/*
System components:
two lights (in an array, say)
Light object of some sort? (class instance?)
-- state

Who turns the lights on and off?

1) Light1 -> Light2
Light2 -> Light1
two right turn signals are simultaneous     // Method 1) not suitable for more complex lights (direction arrows etc).

2) something entirely external (a LightController) could force state changes
a) based on rules/timers that it owns
b) based on listening to the lights posting state-change events

Nik chose 2b) "business rules owned by each light"

Rules/verification:
*** times must match lines 34-36
*** Can't have more than one light being green at the same time. ***
*** You can have more than one light being red at the same time. ***
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Run the controller off the UI thread
        new Thread(new Runnable() {
            public void run() {
                LightController controller = new LightController();
                controller.run();
            }
        }).start();
    }
}