package com.example.racekatteklubbendheisino.application;

import com.example.racekatteklubbendheisino.infrastructure.CRUDRepository;
import com.example.racekatteklubbendheisino.domain.Member;
import java.util.Optional;

public class MemberService {
    private final CRUDRepository<Member, String> crudRepository;

    public MemberService(CRUDRepository<Member, String> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Optional<Member> login(String email, String password) {
        if (email == null || password == null) {
            return Optional.empty();
        }

        try {
            Member member = crudRepository.findByID(email);
            if (member != null && isPasswordValid(member, password)) {
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean isPasswordValid(Member member, String inputPassword) {
        return member.getPassword().equals(inputPassword);
    }
}