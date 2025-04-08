package com.example.racekatteklubbendheisino.application;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.infrastructure.CRUDRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final CRUDRepository<Member, String> crudRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(CRUDRepository<Member, String> crudRepository, PasswordEncoder passwordEncoder) {
        this.crudRepository = crudRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(Member member) {
        if (crudRepository.findByID(member.getEmail()) == null) {
            member.setPassword(passwordEncoder.encode(member.getPassword())); // Encode password
            crudRepository.save(member);
            return true;
        }
        return false;
    }
}