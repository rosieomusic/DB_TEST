package com.techelevator.dao;

import com.techelevator.model.AnimalFavorites;
import com.techelevator.model.Favorites;

import java.util.List;

public interface FavoritesDao {

    List<Favorites> getFavoritesByUserId(int userId);

    Favorites addAnimalToFavorites(Favorites favorites);

    Favorites getFavoriteAnimalByAnimalId(int id);


}
