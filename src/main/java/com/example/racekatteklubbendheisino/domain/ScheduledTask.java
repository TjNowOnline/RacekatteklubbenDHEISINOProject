package com.example.racekatteklubbendheisino.domain;

import com.example.racekatteklubbendheisino.infrastructure.JdbcMemberRepository;
import com.example.racekatteklubbendheisino.infrastructure.JdbcPetRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private final JdbcPetRepository petRepository;
    private final JdbcMemberRepository memberRepository;

    public ScheduledTask(JdbcPetRepository petRepository, JdbcMemberRepository memberRepository) {
        this.petRepository = petRepository;
        this.memberRepository = memberRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldPets() {
        petRepository.deleteOldPets();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldMembers() {
        memberRepository.deleteOldMembers();
    }
}