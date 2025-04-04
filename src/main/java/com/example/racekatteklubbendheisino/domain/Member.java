package com.example.racekatteklubbendheisino.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Not a valid Email")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private List<Pet> pets;

    public Member(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.pets = new ArrayList<>();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
