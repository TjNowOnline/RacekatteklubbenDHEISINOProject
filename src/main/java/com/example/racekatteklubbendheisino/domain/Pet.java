package com.example.racekatteklubbendheisino.domain;

public class Pet {
    private Long id;
    private String name;
    private String breed;
    private Member owner;
    private int age;

    public Pet(Long id, String name, String breed, Member owner, int age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
