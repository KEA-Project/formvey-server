package com.kale.formvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
@EnableSwagger2
@EnableJpaAuditing
@EnableScheduling
class FormveyApplication {
	public static void main(String[] args) {
		SpringApplication.run(FormveyApplication.class, args);
	}

}
