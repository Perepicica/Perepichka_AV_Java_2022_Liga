package ru.internship.perepichka;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PerepichkaAvJava2022LigaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerepichkaAvJava2022LigaApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
