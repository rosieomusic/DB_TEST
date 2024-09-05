package com.techelevator.dao;

import com.techelevator.model.Animal;
import com.techelevator.model.AnimalFavorites;

import java.util.List;

public interface AnimalDao {

    List<Animal> getAllAnimals();

    List<Animal> getAllAnimalsFiltered(String gender, String type, String size, String breed);

    Animal getAnimalById(int id);

    List<Animal> getAllCats();

    List<Animal> getAllDogs();

    Animal createAnimal(Animal animal);

    Animal updateAnimalById(Animal animal);

   // List<AnimalFavorites> userAnimalFavorites(int id);
    int deleteAnimalById(int id);

}
