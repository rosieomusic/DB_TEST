package com.techelevator.controller;

import com.techelevator.dao.ShelterDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Shelter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping(path = "/shelters")
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RestController
public class ShelterController {
    private ShelterDao shelterDao;

    public ShelterController(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }
    @PreAuthorize("permitAll")
    @GetMapping()
    public List<Shelter> getAllShelters(){
        return shelterDao.getAllShelters();
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/{id}")
    public Shelter getShelterById(@PathVariable int id) {
        Shelter shelter = shelterDao.getShelterById(id);
        if(shelter == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return shelterDao.getShelterById(id);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public Shelter createShelter(@RequestBody Shelter shelter) {
        return shelterDao.createShelter(shelter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public Shelter updateShelterById(@RequestBody Shelter shelter, @PathVariable int id) {
        shelter.setShelterId(id);
        Shelter updatedShelter = null;
        try{
            updatedShelter = shelterDao.updateShelterById(shelter);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelter not found");
        }
        return updatedShelter;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteAnimalById(@PathVariable int id) {
        shelterDao.deleteShelterById(id);
    }
}
