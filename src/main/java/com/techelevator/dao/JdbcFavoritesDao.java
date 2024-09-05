package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Animal;
import com.techelevator.model.AnimalFavorites;
import com.techelevator.model.Favorites;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFavoritesDao implements FavoritesDao {

    public JdbcTemplate jdbcTemplate;

    public JdbcFavoritesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Favorites> getFavoritesByUserId(int userId) {
        List<Favorites> favorites = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM favorites " +
                "WHERE user_id = ?";


        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            Favorites favorite = mapRowToFavorites(results);
            favorites.add(favorite);

        }
        return favorites;
    }

    @Override
    public Favorites addAnimalToFavorites(Favorites favorites) {
        List<Favorites>newAnimalToFavorites = new ArrayList<>();
        try {

            String sql = "INSERT INTO favorites(user_id, animal_id) " +
                    "VALUES(?,?) RETURNING animal_id;";
            int userId = jdbcTemplate.queryForObject(sql, int.class,
                    favorites.getUserId(), favorites.getAnimalId());
            newAnimalToFavorites = getFavoritesByUserId(userId);
            newAnimalToFavorites.add(favorites);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return favorites;


        }

    @Override
    public Favorites getFavoriteAnimalByAnimalId(int id) {
        Favorites favorite = null;
        String sql = "SELECT * FROM favorites WHERE animal_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
            if(results.next()) {
                favorite = mapRowToFavorites(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return favorite;
    }

//    @Override
//    public List<AnimalFavorites> getAnimalDataForFavoritesList(int userId) {
//        List<AnimalFavorites> animalFavorites = new ArrayList<>();
//
//        String sql = "SELECT favorites.user_id, favorites.animal_id, animal.name, animal.type, animal.image_url, animal.gender " +
//                "FROM favorites " +
//                "JOIN animal ON animal.animal_id = favorites.animal_id " +
//                "WHERE favorites.user_id = ?;";
//        try {
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//            Favorites favorites = mapRowToFavorites(results);
//            List<AnimalFavorites> newAnimalFavorites = getAnimalDataForFavoritesList(userId);
//            animalFavorites.add();
//        } catch
//        return null;
//    }


    private Favorites mapRowToFavorites(SqlRowSet rs) {
        Favorites favorites = new Favorites();
        favorites.setUserId(rs.getInt("user_id"));
        favorites.setAnimalId(rs.getInt("animal_id"));
        return favorites;
    }
}
