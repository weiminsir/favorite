package com.favorite.wick.annotation;

/**
 * Created by Weimin on 5/30/2016.
 */
public class ReflectClass {

    public String name;
    private int age;
    private String gender;

    public ReflectClass(int age, String name) {

        this.age = age;
        this.name = name;

    }

    public ReflectClass(String name) {
        this.name = name;
    }

    public ReflectClass(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return "WICK";
    }

}
