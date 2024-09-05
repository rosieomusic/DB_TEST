package com.techelevator.dao;

import com.techelevator.model.Animal;
import com.techelevator.model.Favorites;
import com.techelevator.service.AnimalPicService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcAnimalDaoTests extends BaseDaoTests{
protected static final Animal ANIMAL_1 = new Animal(1,"Dog", "Snuffles", null, "M",
        0, "S", "Pug", false, "Loves to cuddle and very friendly.", 1);
protected static final Animal ANIMAL_2 = new Animal(2,"Dog", "Whiskerella", null, "F",
        3, "S", "Pomeranian", true, "Very playful and loves to be pampered.", 2);
protected static final Animal ANIMAL_3 = new Animal(3,"Cat", "Whiskerino", null, "F",
        3, "L", "Maine Coon", true, "Playful and enjoys interacting with people.", 4);

private JdbcAnimalDao dao;
private Animal testAnimal;


@Before
    public void setup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    dao = new JdbcAnimalDao(jdbcTemplate);

    testAnimal = new Animal(0,"Dog","Cash",null,
            "M",2,"M","Lab",false,"Sweetheart, black lab mix,",1);

    }
      /* CREATE TESTS
    (test different data fields by adjusting them in the setup object)
    * Test case: 1 Animal with all valid data
                    check animal count +1
                    check animal_id is valid
                    check animal is correct when retrieved
    * Test case: 2 Animal with one or more invalid data fields
                    exception must be thrown in all cases
                    check that the animal count remains the same
    * Test case: 3 Animal with invalid animal_id ignored
                    check animal count +1
                    check animal_id is serial not value passed in
                    check animal is correct when retrieved
     */
    @Test
    public void createAnimal_returns_animal_with_id_and_expected_values(){
        //Arrange (make animal object in setup)

        //Act create Animal
        Animal newAnimal = dao.createAnimal(testAnimal);

        //Assert new id of created animal is valid > 0 Assert the passed in id is ignored
        int newAnimalId = newAnimal.getAnimalId();
        Assert.assertTrue(newAnimalId > 0);

        //Assert retrieved animal matches testAnimal
        Animal retrievedAnimal = dao.getAnimalById(newAnimalId);
        assertAnimalsMatch(newAnimal,retrievedAnimal);
    }
        /* UPDATE TESTS
    * Test case 1: animal with all data modified except id
                            rows affected 1
                            animal count stays the same
                            check data correct when retrieved again
    * Test case: 2 animal with invalid id
                            rows affected 0 or thrown exception
                            animal count stays the same
                            animal data not changed when retrieved again
    * Test case: 3 Timesheet with one or more invalid fields besides id
                            exception thrown in all cases
                            animal count stays the same
                            animal data not changed when retrieved again
     */

    @Test
    public void updateAnimal_has_expected_values_when_retrieved(){
        // Arrange get a animal to update
        Animal animalToUpdate = dao.getAnimalById(1);

        // Change the data of the animal
        animalToUpdate.setAnimalName("Barbara");
        animalToUpdate.setBreed("Pitbull");
        animalToUpdate.setFostered(true);
        // Act update animal
        dao.updateAnimalById(animalToUpdate);

        //Assert animal matches when retrieved after update
        Animal retrievedAnimal = dao.getAnimalById(1);
        assertAnimalsMatch(animalToUpdate,retrievedAnimal);
    }

    private void assertAnimalsMatch(Animal expected, Animal actual) {
        Assert.assertEquals(expected.getAnimalId(), actual.getAnimalId());
        Assert.assertEquals(expected.getType(), actual.getType());
        Assert.assertEquals(expected.getAnimalName(), actual.getAnimalName());
        Assert.assertEquals(expected.getImageUrl(), actual.getImageUrl());
        Assert.assertEquals(expected.getGender(), actual.getGender());
        Assert.assertEquals(expected.getAge(), actual.getAge());
        Assert.assertEquals(expected.getSize(), actual.getSize());
        Assert.assertEquals(expected.getBreed(), actual.getBreed());
        Assert.assertEquals(expected.isFostered(), actual.isFostered());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.getShelterId(), actual.getShelterId());
    }



}
