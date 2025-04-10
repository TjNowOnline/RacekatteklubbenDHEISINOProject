package com.example.racekatteklubbendheisino.domain;

import jakarta.validation.constraints.NotBlank;

public class Pet {
    private Long memberId;
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Breed cannot be blank")
    private String breed;
    private Member owner;
    private int age;
    private String photoUrl;

    public Pet() {}

    public Pet(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    public Pet(Long id, String name, int age, String breed) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
    }

    public Pet(Long id, String name, String breed, Member owner, int age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.age = age;
    }

    public Pet(Long id, String name, String breed, Member owner, int age, String photoUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.owner = owner;
        this.age = age;
        this.photoUrl = photoUrl;
    }

    public void setMemberId(long memberId) {
        if (this.owner == null) {
            this.owner = new Member();
        }
        this.owner.setId(memberId);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    public Member getOwner() { return owner; }
    public void setOwner(Member owner) { this.owner = owner; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
}