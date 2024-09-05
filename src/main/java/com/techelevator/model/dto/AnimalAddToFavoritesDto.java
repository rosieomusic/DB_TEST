package com.techelevator.model.dto;

public class AnimalAddToFavoritesDto {
    private String type;
    private String animalName;
    private String imageUrl;

    public AnimalAddToFavoritesDto(String type, String animalName, String imageUrl) {
        this.type = type;
        this.animalName = animalName;
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
