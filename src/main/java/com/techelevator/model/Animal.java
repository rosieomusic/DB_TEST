package com.techelevator.model;

public class Animal {
    private int animalId;
    private String type;
    private String animalName;
    private String imageUrl;
    private String gender;
    private int age;
    private String size;
    private String breed;
    private boolean fostered;
    private String description;
    private int shelterId;

    public Animal(){}

    public Animal(int animalId, String type, String animalName, String imageUrl,
                  String gender, int age, String size, String breed,
                  boolean fostered, String description, int shelterId) {
        this.animalId = animalId;
        this.type = type;
        this.animalName = animalName;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.age = age;
        this.size = size;
        this.breed = breed;
        this.fostered = fostered;
        this.description = description;
        this.shelterId = shelterId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String name) {
        this.animalName = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isFostered() {
        return fostered;
    }

    public void setFostered(boolean fostered) {
        this.fostered = fostered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "animalId=" + animalId +
                ", type='" + type + '\'' +
                ", animalName='" + animalName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", size='" + size + '\'' +
                ", breed='" + breed + '\'' +
                ", fostered=" + fostered +
                ", description='" + description + '\'' +
                ", shelterId=" + shelterId +
                '}';
    }
}
