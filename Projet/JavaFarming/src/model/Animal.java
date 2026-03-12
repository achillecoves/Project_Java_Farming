package model;

import javafx.scene.image.Image;

public abstract class Animal {
    public String nameAnimal;
    public int produceTime;
    public Image firstImage;
    public Image secondImage;
    public Image thirdImage;

    public Animal(String nameAnimal, int produceTime, String firstImagePath, String secondImagePath, String thirdImagePath) {
        this.nameAnimal = nameAnimal;
        this.produceTime = produceTime;
        this.firstImage = new Image(getClass().getResourceAsStream(firstImagePath));
        this.secondImage = new Image(getClass().getResourceAsStream(secondImagePath));
        this.thirdImage = new Image(getClass().getResourceAsStream(thirdImagePath));
    }
}