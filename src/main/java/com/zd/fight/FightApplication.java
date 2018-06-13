package com.zd.fight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FightApplication {

	public static void main(String[] args) {
		SpringApplication.run(FightApplication.class, args);
	}
}
