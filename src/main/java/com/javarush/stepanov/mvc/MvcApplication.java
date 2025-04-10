package com.javarush.stepanov.mvc;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.service.CreatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MvcApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(MvcApplication.class, args);
    }

}
