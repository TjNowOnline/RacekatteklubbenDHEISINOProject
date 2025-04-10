package com.example.racekatteklubbendheisino.presentation;

import com.example.racekatteklubbendheisino.application.RoleService;
import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.infrastructure.JdbcMemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final JdbcMemberRepository memberRepository;

    public RoleController(RoleService roleService, JdbcMemberRepository memberRepository) {
        this.roleService = roleService;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/assign")
    // Tildeler en rolle til et medlem
    public ResponseEntity<String> assignRole(@RequestParam Long memberId, @RequestParam String role) {
        boolean success = roleService.assignRoleToMember(memberId, role);
        if (success) {
            return ResponseEntity.ok("Role assigned successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to assign role.");
    }

    @PostMapping("/remove")
    // Fjerner en rolle fra et medlem
    public ResponseEntity<String> removeRole(@RequestParam Long memberId) {
        boolean success = roleService.removeRoleFromMember(memberId);
        if (success) {
            return ResponseEntity.ok("Role removed successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to remove role.");
    }

    @GetMapping("/members")
    // Henter en liste over alle medlemmer
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return ResponseEntity.ok(members);
    }
}