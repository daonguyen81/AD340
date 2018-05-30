package com.example.daong.activitykotlin;

public class Zombie {
    public String title;
    public int year;
    public String director;
    public String image_url;
    public String description;

    public Zombie() {
    }

    public Zombie(String title, int year, String director, String image_url, String description) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.image_url = image_url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}