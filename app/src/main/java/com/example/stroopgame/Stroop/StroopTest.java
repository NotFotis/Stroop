package com.example.stroopgame.Stroop;

import java.util.ArrayList;

public class StroopTest {
    private ArrayList<StroopData> stimuli = new ArrayList<>();

    public StroopTest() {
        stimuli.add(new StroopData("Red", "Red", true));
        stimuli.add(new StroopData("Blue", "Blue", true));
        stimuli.add(new StroopData("Green", "Green", true));
        stimuli.add(new StroopData("Yellow", "Yellow", true));
        stimuli.add(new StroopData("Red", "Blue", false));
        stimuli.add(new StroopData("Blue", "Red", false));
        stimuli.add(new StroopData("Green", "Yellow", false));
        stimuli.add(new StroopData("Yellow", "Green", false));
    }

    public static float getNumberOfCongruentStimuli() {
        return 0;
    }

    public ArrayList<StroopData> getStimuli() {
        return stimuli;
    }
}