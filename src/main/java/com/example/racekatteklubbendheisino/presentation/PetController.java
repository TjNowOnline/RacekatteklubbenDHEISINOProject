// src/main/java/com/example/racekatteklubbendheisino/presentation/PetController.java
package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.PetService;
import com.example.racekatteklubbendheisino.domain.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "createPet";
    }

    @PostMapping("/create")
    public String createPet(@RequestParam String name, @RequestParam int age, @RequestParam String breed) {
        Pet pet = new Pet(name, age, breed);
        petService.savePet(pet);
        return "redirect:/pets";
    }

    @GetMapping
    public String listPets(Model model) {
        model.addAttribute("pets", petService.findAllPets());
        return "listPets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        petService.findPetById(id).ifPresent(pet -> model.addAttribute("pet", pet));
        return "editPet";
    }

    @PostMapping("/edit")
    public String editPet(@RequestParam Long id, @RequestParam String name, @RequestParam int age, @RequestParam String breed) {
        Pet pet = new Pet(id, name, age, breed);
        petService.updatePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return "redirect:/pets";
    }
}