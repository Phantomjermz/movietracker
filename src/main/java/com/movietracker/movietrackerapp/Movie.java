package com.movietracker.movietrackerapp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {
    private final StringProperty title;
    private final IntegerProperty year;
    private final DoubleProperty worldwideGross;
    private final DoubleProperty budget;

    public Movie(String title, int year, double worldwideGross, double budget) {
        this.title = new SimpleStringProperty(title);
        this.year = new SimpleIntegerProperty(year);
        this.worldwideGross = new SimpleDoubleProperty(worldwideGross);
        this.budget = new SimpleDoubleProperty(budget);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public DoubleProperty worldwideGrossProperty() {
        return worldwideGross;
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

    // Getters and setters for non-property fields (if needed)
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public double getWorldwideGross() {
        return worldwideGross.get();
    }

    public void setWorldwideGross(double worldwideGross) {
        this.worldwideGross.set(worldwideGross);
    }

    public double getBudget() {
        return budget.get();
    }

    public void setBudget(double budget) {
        this.budget.set(budget);
    }
}
