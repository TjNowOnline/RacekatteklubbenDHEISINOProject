package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.domain.Pet;
import com.example.racekatteklubbendheisino.infrastructure.JdbcMemberRepository;
import com.example.racekatteklubbendheisino.infrastructure.JdbcPetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final JdbcMemberRepository memberRepository;
    private final JdbcPetRepository petRepository;

    public AdminController(JdbcMemberRepository memberRepository, JdbcPetRepository petRepository) {
        this.memberRepository = memberRepository;
        this.petRepository = petRepository;
    }

    @GetMapping
    // Viser admin-dashboardet med en liste over alle medlemmer
    public String adminDashboard(Model model) {
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/dashboard";
    }

    @GetMapping("/search")
    // Søger efter medlemmer baseret på navn eller e-mail
    public String searchMembers(@RequestParam("query") String query, Model model) {
        List<Member> members = memberRepository.findByNameOrEmail(query);
        model.addAttribute("members", members);
        model.addAttribute("searchQuery", query);
        return "admin/dashboard";
    }

    @GetMapping("/members/{id}")
    // Viser detaljer om et specifikt medlem og deres kæledyr
    public String viewMemberDetails(@PathVariable Long id, Model model) {
        memberRepository.findById(id).ifPresent(member -> {
            model.addAttribute("member", member);

            List<Pet> memberPets = petRepository.findByOwnerId(id);
            model.addAttribute("pets", memberPets);
        });
        return "admin/member-details";
    }

    @GetMapping("/pets/{id}")
    // Viser detaljer om et specifikt kæledyr og dets ejer
    public String viewPetDetails(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findByID(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            Member owner = memberRepository.findById(pet.getOwner().getId()).orElse(null);
            model.addAttribute("owner", owner);
        }
        return "admin/pet-details";
    }

    @GetMapping("/pets/{id}/edit")
    // Viser formularen til at redigere et kæledyrs oplysninger
    public String showEditPetForm(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findByID(id);
        if (pet != null) {
            model.addAttribute("pet", pet);
            model.addAttribute("members", memberRepository.findAll());
        }
        return "admin/edit-pet";
    }

    @PostMapping("/pets/{id}/edit")
    // Opdaterer et kæledyrs oplysninger
    public String updatePet(@PathVariable Long id, @ModelAttribute Pet pet) {
        pet.setId(id);
        petRepository.update(pet);
        return "redirect:/admin/pets/" + id;
    }

    @PostMapping("/pets/{id}/delete")
    // Sletter et kæledyr og redirecter til ejerens side
    public String deletePet(@PathVariable Long id) {
        Pet pet = petRepository.findByID(id);
        if (pet != null) {
            Long ownerId = pet.getOwner().getId();
            petRepository.delete(pet);
            return "redirect:/admin/members/" + ownerId;
        }
        return "redirect:/admin";
    }
}