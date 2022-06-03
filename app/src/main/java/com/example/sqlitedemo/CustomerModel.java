package com.example.sqlitedemo;

import androidx.annotation.NonNull;

public class CustomerModel {
//    Required variables
    private int id;
    private String name;
    private int age;
    private boolean isActive;
//    constructor
    public CustomerModel() {
        // empty constructor
    }
//  Parameterized constructor
    public CustomerModel(int id, String name, int age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }
// setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
//    Getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isActive() {
        return isActive;
    }
//    To string method

    @NonNull
    @Override
    public String toString() {
        return "ID: " + id +
                ", Name: " + name +
                ", Age: " + age +
                ", Active=" + isActive ;
    }
}
