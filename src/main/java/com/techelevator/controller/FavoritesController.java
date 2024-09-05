package com.techelevator.controller;

import com.techelevator.dao.FavoritesDao;
import com.techelevator.model.Favorites;
import com.techelevator.model.dto.AnimalAddToFavoritesDto;
import com.techelevator.service.FavoritesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/favorites")
@CrossOrigin
@RestController


public class FavoritesController {

    private FavoritesService favoritesService;
    private FavoritesDao favoritesDao;
    public FavoritesController(FavoritesService favoritesService, FavoritesDao favoritesDao){
        this.favoritesService = favoritesService;
        this.favoritesDao = favoritesDao;
    }

    @GetMapping()
    public List<Favorites> getUserFavorites(Principal principal){


        String username = principal.getName();
        return favoritesService.getFavoritesFromUserName(username);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Favorites addNewAnimalToFavorites(@RequestBody Favorites favorites) {
        return favoritesDao.addAnimalToFavorites(favorites);
    }
    @GetMapping(path = "/{id}")
    public Favorites getFavoriteByAnimalId(@PathVariable int id) {
        Favorites favorite = favoritesDao.getFavoriteAnimalByAnimalId(id);
        if(favorite == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return favoritesDao.getFavoriteAnimalByAnimalId(id);
        }
    }

//    @PostMapping()
//    public Favorites addFavorite(@Valid @RequestBody AnimalAddToFavoritesDto newFavoriteAnimal, Principal principal) {
//
//    }


}
