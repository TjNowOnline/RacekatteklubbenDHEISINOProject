package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Pet;
import com.example.racekatteklubbendheisino.domain.Show;

import java.util.List;

public interface PetRepository {
    List<Pet> getAll();
    Pet findById(Long id);
    void save(Pet pet);
    void delete(Pet pet);
    void update(Pet pet);
}
