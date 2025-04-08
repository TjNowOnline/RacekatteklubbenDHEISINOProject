package com.example.racekatteklubbendheisino.application;
import com.example.racekatteklubbendheisino.domain.Pet;
import com.example.racekatteklubbendheisino.infrastructure.JdbcPetRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final JdbcPetRepository petRepository;

    public PetService(JdbcPetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    public Optional<Pet> findPetById(Long id) {
        return Optional.ofNullable(petRepository.findByID(id));
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public void updatePet(Pet pet) {
        petRepository.update(pet);
    }


    public void deletePetById(Long id) {
        Pet pet = petRepository.findByID(id);
        if (pet != null) {
            petRepository.delete(pet);
        }
    }
    public List<Pet> findPetsByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
