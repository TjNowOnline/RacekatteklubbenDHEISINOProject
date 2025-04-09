package com.example.racekatteklubbendheisino;

import com.example.racekatteklubbendheisino.application.MemberService;
import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.infrastructure.CRUDRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class RacekatteklubbenDheisinoApplication {
	public static void main(String[] args) {
		SpringApplication.run(RacekatteklubbenDheisinoApplication.class, args);
	}

	@Bean
	public MemberService memberService(
			@Qualifier("jdbcMemberRepository") CRUDRepository<Member, String> memberRepository,
			PasswordEncoder passwordEncoder) {
		return new MemberService(memberRepository, passwordEncoder);
	}
}