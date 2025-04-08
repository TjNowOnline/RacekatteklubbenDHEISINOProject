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
    public String showCreateForm(Model model) {
        model.addAttribute("pet", new Pet()); // For form binding if needed
        return "addPet";
    }

    @PostMapping("/create")
    public String createPet(@RequestParam String name, @RequestParam int age, @RequestParam String breed,
                            @AuthenticationPrincipal Member loggedInMember) {
        Pet pet = new Pet(name, age, breed);
        pet.setOwner(loggedInMember);
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
        return petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .map(pet -> {
                    model.addAttribute("pet", pet);
                    return "editPet";
                })
                .orElse("redirect:/pets"); // Redirect if not found or not owned
    }

    @PostMapping("/edit")
    public String editPet(@RequestParam Long id, @RequestParam String name, @RequestParam int age,
                          @RequestParam String breed, @AuthenticationPrincipal Member loggedInMember) {
        return petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .map(pet -> {
                    pet.setName(name);
                    pet.setAge(age);
                    pet.setBreed(breed);
                    pet.setOwner(loggedInMember);
                    petService.updatePet(pet);
                    return "redirect:/pets";
                })
                .orElse("redirect:/pets"); // Redirect if not found or not owned
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id, @AuthenticationPrincipal Member loggedInMember) {
        petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .ifPresent(pet -> petService.deletePetById(id));
        return "redirect:/pets";
    }
}