package com.techelevator.dao;

import com.techelevator.model.Shelter;

import java.util.List;

public interface ShelterDao {

    List<Shelter> getAllShelters();
    Shelter getShelterById(int id);
    Shelter createShelter(Shelter shelter);
    Shelter updateShelterById(Shelter shelter);
    int deleteShelterById(int id);
}
