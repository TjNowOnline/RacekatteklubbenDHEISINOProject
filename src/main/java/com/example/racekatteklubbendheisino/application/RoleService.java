package com.example.racekatteklubbendheisino.application;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.infrastructure.JdbcMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final JdbcMemberRepository memberRepository;

    public RoleService(JdbcMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public boolean assignRoleToMember(Long memberId, String role) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return false; // Member not found
        }
        member.setRole(role);
        memberRepository.update(member); // Use update instead of save for existing members
        return true;
    }

    @Transactional
    public boolean removeRoleFromMember(Long memberId) {
        Member member = (Member) memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return false; // Member not found
        }
        member.setRole(null); // Remove role
        memberRepository.save(member);
        return true;
    }

}