package Trainer;

import Item.Item;
import Pokemon.Pokemon;

import java.time.LocalDate;
import java.util.ArrayList;

public class Trainer {
    private int trainerId;
    private String name, sex, hometown, description;
    private LocalDate birthDate;
    private double money;
    private ArrayList<Pokemon> lineUp, storage;
    private ArrayList<Item> items;

    public Trainer(int trainerId, String name, String sex, String hometown, String description, LocalDate birthDate){
        this.trainerId = trainerId;
        this.name = name;
        this.sex = sex;
        this.hometown = hometown;
        this.description = description;
        this.birthDate = birthDate;
        this.money = 1000000.00;
    }

    public int getTrainerId(){
        return trainerId;
    }

    public String getName(){
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getHometown(){
        return hometown;
    }

    public String getDescription() {
        return description;
    }

    public double getMoney() {
        return money;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public ArrayList<Pokemon> getLineUp() {
        return lineUp;
    }

    public ArrayList<Pokemon> getStorage() {
        return storage;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
