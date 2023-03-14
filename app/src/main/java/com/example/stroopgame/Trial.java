package com.example.stroopgame;

public class Trial {
    private int sequenceNumber;
    private String word;
    private String displayColor;
    private boolean isColorMatch;
    private int colorChoice;
    private int status;
    private long responseTime;
    private long startTime ;

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public void setColorMatch(boolean colorMatch) {
        isColorMatch = colorMatch;
    }

    public void setColorChoice(int colorChoice) {
        this.colorChoice = colorChoice;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public Trial(int sequenceNumber, String word, String displayColor, boolean isColorMatch, int colorChoice, int status, long responseTime) {
        this.sequenceNumber = sequenceNumber;
        this.word = word;
        this.displayColor = displayColor;
        this.isColorMatch = isColorMatch;
        this.colorChoice = colorChoice;
        this.status = status;
        this.responseTime = responseTime;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public String getWord() {
        return word;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public boolean isColorMatch() {
        return isColorMatch;
    }

    public int getColorChoice() {
        return colorChoice;
    }

    public int getStatus() {
        return status;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


}
