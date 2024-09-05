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
public class JdbcAnimalDao implements AnimalDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcAnimalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animal;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Animal animal = mapRowToAnimal(results);
                animals.add(animal);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return animals;
    }

    @Override
    public List<Animal> getAllAnimalsFiltered(String gender, String type, String size, String breed) {
        List<Animal> filteredAnimals = new ArrayList<>();
        try {
            String sql = "SELECT * FROM animal " +
                    "WHERE (COALESCE(?, gender) ILIKE gender) " +
                    "  AND (COALESCE(?, type) ILIKE type) " +
                    "  AND (COALESCE(?, size) ILIKE size) " +
                    "  AND (COALESCE(?, breed) ILIKE breed)";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gender, type, size, breed);

            while (results.next()) {
                Animal animal = mapRowToAnimal(results);
                filteredAnimals.add(animal);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return filteredAnimals;
    }

    @Override
    public Animal getAnimalById(int id) {
        Animal animal = null;
        String sql = "SELECT * FROM animal WHERE animal_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
            if(results.next()) {
                animal = mapRowToAnimal(results);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return animal;
    }

    @Override
    public List<Animal> getAllCats() {
        List<Animal> cats = new ArrayList<>();
        String sql = "SELECT * FROM animal WHERE type ILIKE 'cat%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Animal cat = mapRowToAnimal(results);
                cats.add(cat);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return cats;
    }

    @Override
    public List<Animal> getAllDogs() {
        List<Animal> dogs = new ArrayList<>();
        String sql = "SELECT * FROM animal WHERE type ILIKE 'dog%';";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Animal dog = mapRowToAnimal(results);
                dogs.add(dog);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return dogs;
    }

    @Override
    public Animal createAnimal(Animal animal) {
        Animal newAnimal = null;
        String sql = "INSERT INTO animal(type,name,image_url,gender,age,size,breed,fostered,description,shelter_id) VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING animal_id;";
        try{
            int animalId = jdbcTemplate.queryForObject(sql, int.class, animal.getType(), animal.getAnimalName(),
                    animal.getImageUrl(), animal.getGender(), animal.getAge(), animal.getSize(), animal.getBreed(),
                    animal.isFostered(), animal.getDescription(), animal.getShelterId());
            newAnimal = getAnimalById(animalId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newAnimal;
    }

    @Override
    public Animal updateAnimalById(Animal animal) {
        Animal updatedAnimal = null;
        String sql = "UPDATE animal SET type = ?, name = ?, image_url = ?, gender = ?, " +
                     "age = ?, size = ?, breed = ?, fostered = ?, description = ?, shelter_id = ? WHERE animal_id = ?;";
        try{
            int rowsAffected = jdbcTemplate.update(sql, animal.getType(), animal.getAnimalName(),
                    animal.getImageUrl(), animal.getGender(), animal.getAge(), animal.getSize(), animal.getBreed(),
                    animal.isFostered(), animal.getDescription(), animal.getShelterId(), animal.getAnimalId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedAnimal = getAnimalById(animal.getAnimalId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedAnimal;
    }

//    @Override
//    public List<AnimalFavorites> userAnimalFavorites(int id) {
//        List<AnimalFavorites> animalFavorites = new ArrayList<>();
//        String sql = "SELECT type, name, image_url, gender " +
//                "FROM animal " +
//                "JOIN favorites ON favorites.animal_id = animal.animal_id" +
//                "WHERE favorites.user_id = ?;";
//        try{
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
//            while (results.next()) {
//                Animal animal = mapRowToAnimal(results);
//                List<Favorites> favorites = favoritesDao.getFavoritesByUserId(id);
//
//                AnimalFavorites newAnimalFavorites = new AnimalFavorites(animal,favorites);
//                animalFavorites.add(newAnimalFavorites);
//
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
//
//        return animalFavorites;
//    }
//

//    @Override
//    public Animal specialUpdate(Animal animal) {
//        Animal updatedAnimalWithUrl = null;
//        String sql = "Update animal SET image_url = 'https://dog.ceo/api/breed/pug/images/random' WHERE breed = 'Pug';";
//        try{
//            int rowsAffected = jdbcTemplate.update(sql, animal.getType(), animal.getAnimalName(),
//                    animal.getImageUrl(), animal.getGender(), animal.getAge(), animal.getSize(), animal.getBreed(),
//                    animal.isFostered(), animal.getDescription(), animal.getShelterId(), animal.getAnimalId());
//            if(rowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected one");
//
//            }
//            updatedAnimalWithUrl = getAnimalById(animal.getAnimalId());
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//        return updatedAnimalWithUrl;
//    }

    @Override
    public int deleteAnimalById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM animal WHERE animal_id = ?;";
        try{
            numberOfRows = jdbcTemplate.update(sql,id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }



    private Animal mapRowToAnimal(SqlRowSet rs) {
        Animal animal = new Animal();
        animal.setAnimalId(rs.getInt("animal_id"));
        animal.setType(rs.getString("type"));
        animal.setAnimalName(rs.getString("name"));
        if(rs.getString("image_url") != null) {
            animal.setImageUrl(rs.getString("image_url"));
        }
        animal.setGender(rs.getString("gender"));
        if(rs.getInt("age") != 0) {
            animal.setAge(rs.getInt("age"));
        }
        if(rs.getString("size") != null) {
            animal.setSize(rs.getString("size"));
        }
        if(rs.getString("breed") !=null) {
            animal.setBreed(rs.getString("breed"));
        }
        animal.setFostered(rs.getBoolean("fostered"));
        if(rs.getString("description") != null) {
            animal.setDescription(rs.getString("description"));
        }
        animal.setShelterId(rs.getInt("shelter_id"));
        return animal;
    }
}
