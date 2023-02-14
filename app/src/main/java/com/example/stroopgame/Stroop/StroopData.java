package com.example.stroopgame.Stroop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StroopData {
    private int stimulusSerialNumber;
    private String word;
    private String displayColor;
    private int stroopColorMatch;
    private int colorSelection;
    private int status;
    private long responseTime;
    private boolean isMatch;

    public StroopData(String word, String displayColor, boolean isMatch) {
        this.word = word;
        this.displayColor = displayColor;
        this.isMatch = isMatch;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public int getStimulusSerialNumber() {
        return stimulusSerialNumber;
    }

    public void setStimulusSerialNumber(int stimulusSerialNumber) {
        this.stimulusSerialNumber = stimulusSerialNumber;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public int getStroopColorMatch() {
        return stroopColorMatch;
    }

    public void setStroopColorMatch(int stroopColorMatch) {
        this.stroopColorMatch = stroopColorMatch;
    }

    public int getColorSelection() {
        return colorSelection;
    }

    public void setColorSelection(int colorSelection) {
        this.colorSelection = colorSelection;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime =  responseTime;
    }

    public StroopData(int stimulusSerialNumber, String word, String displayColor, int stroopColorMatch, int colorSelection, int status, int responseTime) {
        this.stimulusSerialNumber = stimulusSerialNumber;
        this.word = word;
        this.displayColor = displayColor;
        this.stroopColorMatch = stroopColorMatch;
        this.colorSelection = colorSelection;
        this.status = status;
        this.responseTime = responseTime;
    }

    // number of correct responses to the set of stimuli
    public static int getNumberOfCorrectResponses(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 1) {
                count++;
            }
        }
        return count;
    }

    // number of incorrect responses to the set of stimuli
    public static int getNumberOfIncorrectResponses(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 2) {
                count++;
            }
        }
        return count;
    }

    // number of correct responses to the set of congruent stimuli
    public static int getNumberOfCorrectResponsesCongruent(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 1 && data.getStroopColorMatch() == 1) {
                count++;
            }
        }
        return count;
    }

    // number of incorrect responses to the set of congruent stimuli
    public static int getNumberOfIncorrectResponsesCongruent(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 2 && data.getStroopColorMatch() == 1) {
                count++;
            }
        }
        return count;
    }

    // number of correct responses to the set of non-congruent stimuli
    public static int getNumberOfCorrectResponsesNonCongruent(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 1 && data.getStroopColorMatch() == 0) {
                count++;
            }
        }
        return count;
    }

    // number of incorrect responses to the set of non-congruent stimuli
    public static int getNumberOfIncorrectResponsesNonCongruent(List<StroopData> stroopDataList) {
        int count = 0;
        for (StroopData data : stroopDataList) {
            if (data.getStatus() == 2 && data.getStroopColorMatch() == 0) {
                count++;
            }
        }
        return count;
    }

    //function to calculate percentage of stimuli for each color
    public static Map<String,Float> calculatePercentageOfStimuliPerColor(List<StroopData> stimuli) {
        Map<String,Float> colorPercentages = new HashMap<>();
        int totalStimuli = stimuli.size();
        int yellowStimuli = 0, redStimuli = 0, blueStimuli = 0, greenStimuli = 0;

        for (StroopData stimulus : stimuli) {
            if (stimulus.getWord().equalsIgnoreCase("yellow")) {
                yellowStimuli++;
            } else if (stimulus.getWord().equalsIgnoreCase("red")) {
                redStimuli++;
            } else if (stimulus.getWord().equalsIgnoreCase("blue")) {
                blueStimuli++;
            } else if (stimulus.getWord().equalsIgnoreCase("green")) {
                greenStimuli++;
            }
        }

        colorPercentages.put("yellow", (float) yellowStimuli/totalStimuli * 100);
        colorPercentages.put("red", (float) redStimuli/totalStimuli * 100);
        colorPercentages.put("blue", (float) blueStimuli/totalStimuli * 100);
        colorPercentages.put("green", (float) greenStimuli/totalStimuli * 100);

        return colorPercentages;
    }

    //function to calculate percentage of correct responses for each color
    public static Map<String,Float> calculatePercentageOfCorrectResponsesPerColor(List<StroopData> stimuli) {
        Map<String,Float> colorPercentages = new HashMap<>();
        int totalStimuli = stimuli.size();
        int yellowCorrect = 0, redCorrect = 0, blueCorrect = 0, greenCorrect = 0;

        for (StroopData stimulus : stimuli) {
            if (stimulus.getWord().equalsIgnoreCase("yellow") && stimulus.getStatus() == 1) {
                yellowCorrect++;
            } else if (stimulus.getWord().equalsIgnoreCase("red") && stimulus.getStatus() == 1) {
                redCorrect++;
            } else if (stimulus.getWord().equalsIgnoreCase("blue") && stimulus.getStatus() == 1) {
                blueCorrect++;
            } else if (stimulus.getWord().equalsIgnoreCase("green") && stimulus.getStatus() == 1) {
                greenCorrect++;
            }
        }

        colorPercentages.put("yellow", (float) yellowCorrect/totalStimuli * 100);
        colorPercentages.put("red", (float) redCorrect/totalStimuli * 100);
        colorPercentages.put("blue", (float) blueCorrect/totalStimuli * 100);
        colorPercentages.put("green", (float) greenCorrect/totalStimuli * 100);

        return colorPercentages;
    }
    //function to calculate percentage of incorrect responses for each color
    public Map<String,Float> calculatePercentageOfIncorrectResponsesPerColor(List<StroopData> stimuli) {
        Map<String,Float> colorPercentages = new HashMap<>();
        int totalStimuli = stimuli.size();
        int yellowStimuli = 0, redStimuli = 0, blueStimuli = 0, greenStimuli = 0;
        int yellowIncorrect = 0, redIncorrect = 0, blueIncorrect = 0, greenIncorrect = 0;
        for(StroopData stimulus : stimuli) {
            if(stimulus.getDisplayColor().equals("yellow")) {
                yellowStimuli++;
                if(stimulus.getStatus() == 2) {
                    yellowIncorrect++;
                }
            } else if(stimulus.getDisplayColor().equals("red")) {
                redStimuli++;
                if(stimulus.getStatus() == 2) {
                    redIncorrect++;
                }
            } else if(stimulus.getDisplayColor().equals("blue")) {
                blueStimuli++;
                if(stimulus.getStatus() == 2) {
                    blueIncorrect++;
                }
            } else if(stimulus.getDisplayColor().equals("green")) {
                greenStimuli++;
                if(stimulus.getStatus() == 2) {
                    greenIncorrect++;
                }
            }
        }
        if(yellowStimuli > 0) {
            colorPercentages.put("yellow", (float)yellowIncorrect/yellowStimuli * 100);
        }
        if(redStimuli > 0) {
            colorPercentages.put("red", (float)redIncorrect/redStimuli * 100);
        }
        if(blueStimuli > 0) {
            colorPercentages.put("blue", (float)blueIncorrect/blueStimuli * 100);
        }
        if(greenStimuli > 0) {
            colorPercentages.put("green", (float)greenIncorrect/greenStimuli * 100);
        }
        return colorPercentages;
    }

    private float calculateAverageResponseTime(List<StroopData> stroopDataList) {
        int totalResponseTime = 0;
        for (StroopData data : stroopDataList) {
            totalResponseTime += data.getResponseTime();
        }
        return (float) totalResponseTime / stroopDataList.size();
    }

    //function to calculate average response time to the set of congruent stimuli
    public static float averageResponseTimeToCongruentStimuli(List<StroopData> stimuli) {
        int totalResponseTime = 0;
        int totalCongruentStimuli = 0;
        for (StroopData stimulus : stimuli) {
            if (stimulus.getStroopColorMatch() == 1) {
                totalResponseTime += stimulus.getResponseTime();
                totalCongruentStimuli++;
            }
        }
        return totalCongruentStimuli == 0 ? 0 : (float) totalResponseTime / totalCongruentStimuli;
    }

    //function to calculate average response time to the set of non-congruent stimuli
    public float averageResponseTimeToNonCongruentStimuli(List<StroopData> stimuli) {
        int totalResponseTime = 0;
        int totalNonCongruentStimuli = 0;
        for (StroopData stimulus : stimuli) {
            if (stimulus.getStroopColorMatch() == 0) {
                totalResponseTime += stimulus.getResponseTime();
                totalNonCongruentStimuli++;
            }
        }
        return totalNonCongruentStimuli == 0 ? 0 : (float) totalResponseTime / totalNonCongruentStimuli;
    }


    public void setResponse(String color) {

    }

    public void setCorrect(boolean b) {

    }


}