package model;

import javafx.scene.image.Image;

public abstract class PlantSeed {
    public String namePlant;
    public int price;
    public int growthTime;
    public Image firstImage;
    public Image secondImage;

    public PlantSeed(String namePlant, int price, int growthTime, String firstImagePath, String secondImagePath) {
        this.namePlant = namePlant;
        this.price = price;
        this.growthTime = growthTime;
        this.firstImage = new Image(getClass().getResourceAsStream(firstImagePath));
        this.secondImage = new Image(getClass().getResourceAsStream(secondImagePath));
    }
}