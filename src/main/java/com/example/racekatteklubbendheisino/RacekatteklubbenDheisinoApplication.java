package com.example.racekatteklubbendheisino;

import com.example.racekatteklubbendheisino.application.MemberService;
import com.example.racekatteklubbendheisino.infrastructure.CRUDRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RacekatteklubbenDheisinoApplication {
	public static void main(String[] args) {
		SpringApplication.run(RacekatteklubbenDheisinoApplication.class, args);
	}

	@Bean
	public MemberService memberService(CRUDRepository crudRepository) {
		return new MemberService(crudRepository);
	}


}

