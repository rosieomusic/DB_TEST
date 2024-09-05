package com.techelevator.model;

import java.util.List;

public class AnimalFavorites {
    // Join table for favorites and animals to display animal info

    private Animal animal;
    private List<Favorites> favorites;

    public AnimalFavorites(){}

    public AnimalFavorites(Animal animal, List<Favorites> favorites) {
        this.animal = animal;
        this.favorites = favorites;
    }

    public Animal getAnimal() {
        return animal;
    }

    public List<Favorites> getFavorites() {
        return favorites;
    }
}
