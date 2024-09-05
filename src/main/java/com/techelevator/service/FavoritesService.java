package com.techelevator.service;

import com.techelevator.model.Favorites;
import com.techelevator.model.dto.AnimalAddToFavoritesDto;

import java.util.List;

public interface FavoritesService {

    List<Favorites> getFavoritesFromUserName(String username);

   // Favorites addNewFavoriteAnimalFromUsername(String username, AnimalAddToFavoritesDto
                                            //   newFavoriteAnimal);


}
