package com.javarush.stepanov.mvc;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.repository.impl.CreatorRepoImpl;
import com.javarush.stepanov.mvc.service.CreatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class MvcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MvcApplication.class, args);
    }

}
