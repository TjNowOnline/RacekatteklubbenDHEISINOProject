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

    // Tildel en rolle til et medlem
    @Transactional
    public boolean assignRoleToMember(Long memberId, String role) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return false;
        }
        member.setRole(role);
        memberRepository.update(member);
        return true;
    }
    // Fjern en rolle fra et medlem
    @Transactional
    public boolean removeRoleFromMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return false;
        }
        member.setRole(null);
        memberRepository.save(member);
        return true;
    }
}