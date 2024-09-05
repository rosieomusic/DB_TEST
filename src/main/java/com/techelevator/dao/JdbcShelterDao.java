package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Shelter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcShelterDao implements ShelterDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcShelterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Shelter> getAllShelters() {
        List<Shelter> shelters = new ArrayList<>();
        String sql = "SELECT * FROM shelter;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Shelter shelter = mapRowToShelter(results);
                shelters.add(shelter);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return shelters;
    }

    @Override
    public Shelter getShelterById(int id) {
        Shelter shelter = null;
        String sql = "SELECT * FROM shelter WHERE shelter_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
            if(results.next()) {
                shelter = mapRowToShelter(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return shelter;
    }

    @Override
    public Shelter createShelter(Shelter shelter) {
        Shelter newShelter = null;
        String sql = "INSERT INTO shelter(name,address,city,state,zip,email,phone_number) " +
                "VALUES(?,?,?,?,?,?,?) RETURNING shelter_id;";
        try{
            int shelterId = jdbcTemplate.queryForObject(sql, int.class, shelter.getShelterName(),
                    shelter.getAddress(), shelter.getCity(), shelter.getState(), shelter.getZip(),
                    shelter.getEmail(), shelter.getPhoneNumber());
            newShelter = getShelterById(shelterId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newShelter;
    }

    @Override
    public Shelter updateShelterById(Shelter shelter) {
        Shelter updatedShelter = null;
        String sql = "UPDATE shelter SET name = ?, address = ?, " +
                "city = ?, state = ?, zip = ?, email = ?, phone_number = ? WHERE shelter_id =?;";
        try{
            int rowsAffected = jdbcTemplate.update(sql,shelter.getShelterName(), shelter.getAddress(),
                    shelter.getCity(), shelter.getState(), shelter.getZip(), shelter.getEmail(), shelter.getPhoneNumber(),
                    shelter.getShelterId());
            if(rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedShelter = getShelterById(shelter.getShelterId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedShelter;
    }

    @Override
    public int deleteShelterById(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM shelter WHERE shelter_id = ?;";
        try{
            numberOfRows = jdbcTemplate.update(sql,id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Shelter mapRowToShelter(SqlRowSet rs) {
        Shelter shelter = new Shelter();
        shelter.setShelterId(rs.getInt("shelter_id"));
        shelter.setShelterName(rs.getString("name"));
        shelter.setAddress(rs.getString("address"));
        shelter.setCity(rs.getString("city"));
        shelter.setState(rs.getString("state"));
        shelter.setZip(rs.getString("zip"));
        shelter.setEmail(rs.getString("email"));
        if(rs.getString("phone_number") != null) {
            shelter.setPhoneNumber(rs.getString("phone_number"));
        }
        return shelter;
    }
}
