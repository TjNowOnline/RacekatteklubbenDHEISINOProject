package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Pet;

import java.util.List;

public class JdbcPetRepository implements PetRepository {


    @Override
    public List<Pet> getAll() {
        return List.of();
    }

    @Override
    public Pet findById(Long id) {
        return null;
    }

    @Override
    public void save(Pet pet) {

    }

    @Override
    public void delete(Pet pet) {

    }

    @Override
    public void update(Pet pet) {

    }
}
