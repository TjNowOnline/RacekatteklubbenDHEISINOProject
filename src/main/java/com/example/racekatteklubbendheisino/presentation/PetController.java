package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.PetService;
import com.example.racekatteklubbendheisino.domain.Pet;
import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.infrastructure.Mailsystem;
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
    public String showCreateForm(Model model, @AuthenticationPrincipal Member loggedInMember) {
        model.addAttribute("pet", new Pet());
        if (loggedInMember != null) {
            model.addAttribute("username", loggedInMember.getName());
        }
        return "addPet";
    }

    @PostMapping("/create")
    public String createPet(@RequestParam String name, @RequestParam int age, @RequestParam String breed,
                            @RequestParam(value = "photoUrl", required = false) String photoUrl,
                            @AuthenticationPrincipal Member loggedInMember) throws Exception {
        Pet pet = new Pet(name, age, breed);
        pet.setOwner(loggedInMember);
        pet.setPhotoUrl(photoUrl);
        petService.savePet(pet);
        String subject = "New Pet Added! :D";
        String messageBody = String.format("Hello " + loggedInMember.getName() + " your pet " + pet.getName() + " whose breed is " + pet.getBreed() + " and age is " + pet.getAge() + "has been added to the database.");
        Mailsystem.MailSystem.sendmail(loggedInMember.getEmail(), subject, messageBody);
        return "redirect:/pets";
    }

    @GetMapping
    public String listPets(Model model, @AuthenticationPrincipal Member loggedInMember) {
        if (loggedInMember != null) {
            model.addAttribute("pets", petService.findPetsByOwnerId(loggedInMember.getId()));
            model.addAttribute("username", loggedInMember.getName());
        }
        return "listPets";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, @AuthenticationPrincipal Member loggedInMember) {
        return petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .map(pet -> {
                    model.addAttribute("pet", pet);
                    model.addAttribute("username", loggedInMember.getName());
                    return "editPet";
                })
                .orElse("redirect:/pets");
    }

    @PostMapping("/edit")
    public String editPet(@RequestParam Long id, @RequestParam String name, @RequestParam int age,
                          @RequestParam String breed, @RequestParam(value = "photoUrl", required = false) String photoUrl,
                          @AuthenticationPrincipal Member loggedInMember) {
        return petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .map(pet -> {
                    pet.setName(name);
                    pet.setAge(age);
                    pet.setBreed(breed);
                    pet.setOwner(loggedInMember);
                    pet.setPhotoUrl(photoUrl);
                    petService.updatePet(pet);
                    return "redirect:/pets";
                })
                .orElse("redirect:/pets");
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id, @AuthenticationPrincipal Member loggedInMember) {
        petService.findPetById(id)
                .filter(pet -> pet.getOwner().getId().equals(loggedInMember.getId()))
                .ifPresent(pet -> petService.deletePetById(id));
        return "redirect:/pets";
    }
}