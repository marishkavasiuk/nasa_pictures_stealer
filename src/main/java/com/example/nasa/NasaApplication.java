package com.example.nasa;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NasaApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(NasaApplication.class, args);
	}
}
