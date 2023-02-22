package com.example.stroopgame;

public class Result {
    private String username;
    private double totalPercent;
    private double redPercentage;
    private double bluePercentage;
    private double greenPercentage;
    private double yellowPercentage;
    private double redCorrectPercentage;
    private double blueCorrectPercentage;
    private double greenCorrectPercentage;
    private double yellowCorrectPercentage;
    private double redIncorrectPercentage;
    private double blueIncorrectPercentage;
    private double greenIncorrectPercentage;
    private double yellowIncorrectPercentage;
    private double totalAvgResponseTime;
    private double congruentAvgResponseTime;
    private double nonCongruentAvgResponseTime;
    private double stroopEffect;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(double totalPercent) {
        this.totalPercent = totalPercent;
    }

    public double getRedPercentage() {
        return redPercentage;
    }

    public void setRedPercentage(double redPercentage) {
        this.redPercentage = redPercentage;
    }

    public double getBluePercentage() {
        return bluePercentage;
    }

    public void setBluePercentage(double bluePercentage) {
        this.bluePercentage = bluePercentage;
    }

    public double getGreenPercentage() {
        return greenPercentage;
    }

    public void setGreenPercentage(double greenPercentage) {
        this.greenPercentage = greenPercentage;
    }

    public double getYellowPercentage() {
        return yellowPercentage;
    }

    public void setYellowPercentage(double yellowPercentage) {
        this.yellowPercentage = yellowPercentage;
    }

    public double getRedCorrectPercentage() {
        return redCorrectPercentage;
    }

    public void setRedCorrectPercentage(double redCorrectPercentage) {
        this.redCorrectPercentage = redCorrectPercentage;
    }

    public double getBlueCorrectPercentage() {
        return blueCorrectPercentage;
    }

    public void setBlueCorrectPercentage(double blueCorrectPercentage) {
        this.blueCorrectPercentage = blueCorrectPercentage;
    }

    public double getGreenCorrectPercentage() {
        return greenCorrectPercentage;
    }

    public void setGreenCorrectPercentage(double greenCorrectPercentage) {
        this.greenCorrectPercentage = greenCorrectPercentage;
    }

    public double getYellowCorrectPercentage() {
        return yellowCorrectPercentage;
    }

    public void setYellowCorrectPercentage(double yellowCorrectPercentage) {
        this.yellowCorrectPercentage = yellowCorrectPercentage;
    }

    public double getRedIncorrectPercentage() {
        return redIncorrectPercentage;
    }

    public void setRedIncorrectPercentage(double redIncorrectPercentage) {
        this.redIncorrectPercentage = redIncorrectPercentage;
    }

    public double getBlueIncorrectPercentage() {
        return blueIncorrectPercentage;
    }

    public void setBlueIncorrectPercentage(double blueIncorrectPercentage) {
        this.blueIncorrectPercentage = blueIncorrectPercentage;
    }

    public double getGreenIncorrectPercentage() {
        return greenIncorrectPercentage;
    }

    public void setGreenIncorrectPercentage(double greenIncorrectPercentage) {
        this.greenIncorrectPercentage = greenIncorrectPercentage;
    }

    public double getYellowIncorrectPercentage() {
        return yellowIncorrectPercentage;
    }

    public void setYellowIncorrectPercentage(double yellowIncorrectPercentage) {
        this.yellowIncorrectPercentage = yellowIncorrectPercentage;
    }

    public double getTotalAvgResponseTime() {
        return totalAvgResponseTime;
    }

    public void setTotalAvgResponseTime(double totalAvgResponseTime) {
        this.totalAvgResponseTime = totalAvgResponseTime;
    }

    public double getCongruentAvgResponseTime() {
        return congruentAvgResponseTime;
    }

    public void setCongruentAvgResponseTime(double congruentAvgResponseTime) {
        this.congruentAvgResponseTime = congruentAvgResponseTime;
    }

    public double getNonCongruentAvgResponseTime() {
        return nonCongruentAvgResponseTime;
    }

    public void setNonCongruentAvgResponseTime(double nonCongruentAvgResponseTime) {
        this.nonCongruentAvgResponseTime = nonCongruentAvgResponseTime;
    }

    public double getStroopEffect() {
        return stroopEffect;
    }

    public void setStroopEffect(double stroopEffect) {
        this.stroopEffect = stroopEffect;
    }

    public Result(String username, double totalPercent, double redPercentage, double bluePercentage,
                   double greenPercentage, double yellowPercentage, double redCorrectPercentage,
                   double blueCorrectPercentage, double greenCorrectPercentage, double yellowCorrectPercentage,
                   double redIncorrectPercentage, double blueIncorrectPercentage, double greenIncorrectPercentage,
                   double yellowIncorrectPercentage, double totalAvgResponseTime, double congruentAvgResponseTime,
                   double nonCongruentAvgResponseTime, double stroopEffect) {
        this.username = username;
        this.totalPercent = totalPercent;
        this.redPercentage = redPercentage;
        this.bluePercentage = bluePercentage;
        this.greenPercentage = greenPercentage;
        this.yellowPercentage = yellowPercentage;
        this.redCorrectPercentage = redCorrectPercentage;
        this.blueCorrectPercentage = blueCorrectPercentage;
        this.greenCorrectPercentage = greenCorrectPercentage;
        this.yellowCorrectPercentage = yellowCorrectPercentage;
        this.redIncorrectPercentage = redIncorrectPercentage;
        this.blueIncorrectPercentage = blueIncorrectPercentage;
        this.greenIncorrectPercentage = greenIncorrectPercentage;
        this.yellowIncorrectPercentage = yellowIncorrectPercentage;
        this.totalAvgResponseTime = totalAvgResponseTime;
        this.congruentAvgResponseTime = congruentAvgResponseTime;
        this.nonCongruentAvgResponseTime = nonCongruentAvgResponseTime;
        this.stroopEffect = stroopEffect;
    }
}
