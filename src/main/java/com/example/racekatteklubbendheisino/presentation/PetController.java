// src/main/java/com/example/racekatteklubbendheisino/presentation/PetController.java
package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.PetService;
import com.example.racekatteklubbendheisino.domain.Pet;
import com.example.racekatteklubbendheisino.domain.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        return "addPet";
    }

    @PostMapping("/create")
    public String createPet(@RequestParam String name, @RequestParam int age, @RequestParam String breed,
                            @AuthenticationPrincipal Member loggedInMember) {
        Pet pet = new Pet(name, age, breed);
        pet.setOwner(loggedInMember); // Set the logged-in user as the owner
        petService.savePet(pet);
        return "redirect:/pets";
    }

    @GetMapping
    public String listPets(Model model, @AuthenticationPrincipal Member loggedInMember) {
        model.addAttribute("pets", petService.findPetsByOwnerId(loggedInMember.getId()));
        return "listPets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal Member loggedInMember) {
        petService.findPetById(id).ifPresent(pet -> {
            if (pet.getOwner().getId().equals(loggedInMember.getId())) { // Check ownership
                model.addAttribute("pet", pet);
            }
        });
        return "editPet";
    }

    @PostMapping("/edit")
    public String editPet(@RequestParam Long id, @RequestParam String name, @RequestParam int age,
                          @RequestParam String breed, @AuthenticationPrincipal Member loggedInMember) {
        Pet pet = new Pet(id, name, age, breed);
        pet.setOwner(loggedInMember); // Ensure owner remains the logged-in user
        petService.updatePet(pet);
        return "redirect:/pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id, @AuthenticationPrincipal Member loggedInMember) {
        petService.findPetById(id).ifPresent(pet -> {
            if (pet.getOwner().getId().equals(loggedInMember.getId())) { // Check ownership
                petService.deletePetById(id);
            }
        });
        return "redirect:/pets";
    }
}