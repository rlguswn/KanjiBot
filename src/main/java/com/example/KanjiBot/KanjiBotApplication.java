package com.example.KanjiBot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.KanjiBot.mapper")
public class KanjiBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanjiBotApplication.class, args);
	}

}
