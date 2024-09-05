package com.techelevator.model;

public class Favorites {
    private int userId;
    private int animalId;

    public Favorites(){}
    public Favorites(int userId, int animalId) {
        this.userId = userId;
        this.animalId = animalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }
}
