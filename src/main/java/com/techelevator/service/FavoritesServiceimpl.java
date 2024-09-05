package com.techelevator.service;

import com.techelevator.dao.AnimalDao;
import com.techelevator.dao.FavoritesDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Favorites;
import com.techelevator.model.User;
import com.techelevator.model.dto.AnimalAddToFavoritesDto;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
@Component
public class FavoritesServiceimpl  implements FavoritesService{
    private UserDao userDao;
    private FavoritesDao favoritesDao;
    private AnimalDao animalDao;

    public FavoritesServiceimpl(UserDao userDao, FavoritesDao favoritesDao) {
        this.userDao = userDao;
        this.favoritesDao = favoritesDao;
    }

//    public FavoritesServiceimpl(UserDao userDao, FavoritesDao favoritesDao, AnimalDao animalDao) {
//        this.userDao = userDao;
//        this.favoritesDao = favoritesDao;
//        this.animalDao = animalDao;
//    }



    @Override
    public List<Favorites> getFavoritesFromUserName(String username) {
        List<Favorites> favorites = null;

        //List<Favorites> favorites = null;
        /*
        * Get the user id from the username from the userDAO
         */
        User user = userDao.getUserByUsername(username);
        if(user != null) {
            int userId = user.getId();
        /*
        call into the dao to get the favorites by id
         */

             favorites = favoritesDao.getFavoritesByUserId(userId);
        }
        return favorites;
    }

//    @Override
//    public Favorites addNewFavoriteAnimalFromUsername(String username, AnimalAddToFavoritesDto newFavoriteAnimal) {
//        User user = userDao.getUserByUsername(username);
//
//        return null;
//    }
}
