package com.techelevator.controller;

import com.techelevator.dao.AnimalDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Animal;
import com.techelevator.model.AnimalFavorites;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping(path = "/animals")
@PreAuthorize("isAuthenticated()")
@RestController
public class AnimalController {


    private AnimalDao animalDao;

    public AnimalController(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }
    @PreAuthorize("permitAll")
    @GetMapping(path ="")
    public List<Animal> getAllAnimals() {
        return animalDao.getAllAnimals();
    }
    @PreAuthorize("permitAll")
    @GetMapping(path = "/{id}")
    public Animal getAnimalById(@PathVariable int id) {
       Animal animal = animalDao.getAnimalById(id);
       if(animal == null) {
           throw new ResponseStatusException((HttpStatus.NOT_FOUND));
       } else {
           return animalDao.getAnimalById(id);
       }
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/filter")
    public List<Animal> getAllAnimalsFiltered(@RequestParam(value = "gender", required = false) String gender,
                                              @RequestParam(value = "type", required = false) String type,
                                              @RequestParam(value = "size", required = false) String size,
                                              @RequestParam(value = "breed", required = false) String breed) {
        return animalDao.getAllAnimalsFiltered(gender,type, size, breed);
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/cats")
    public List<Animal> getAllCats() {
        return animalDao.getAllCats();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/dogs")
    public List<Animal> getAllDogs(){
        return animalDao.getAllDogs();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "")
    public Animal create(@RequestBody Animal animal) {
        return animalDao.createAnimal(animal);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public Animal updateAnimalById(@RequestBody Animal animal, @PathVariable int id) {
        animal.setAnimalId(id);
        Animal updatedAnimal = null;
        try {
            updatedAnimal = animalDao.updateAnimalById(animal);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found");
        }
        return updatedAnimal;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteAnimalById(@PathVariable int id) {
        animalDao.deleteAnimalById(id);

    }




}
