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

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
public class MvcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MvcApplication.class, args);
        initTestData(context);
    }

    private static void initTestData(ConfigurableApplicationContext context) {
        CreatorRepoImpl creatorRepo = context.getBean(CreatorRepoImpl.class);

        // Добавляем 10 тестовых пользователей
        List<Creator> testUsers = List.of(
                createTestUser(1L, "user1", "password1", "John", "Doe"),
                createTestUser(2L, "user2", "password2", "Jane", "Smith"),
                createTestUser(3L, "user3", "password3", "Bob", "Johnson"),
                createTestUser(4L, "user4", "password4", "Alice", "Williams"),
                createTestUser(5L, "user5", "password5", "Mike", "Brown"),
                createTestUser(6L, "user6", "password6", "Sarah", "Davis"),
                createTestUser(7L, "user7", "password7", "Tom", "Miller"),
                createTestUser(8L, "user8", "password8", "Emily", "Wilson"),
                createTestUser(9L, "user9", "password9", "David", "Taylor"),
                createTestUser(10L, "user10", "password10", "Lisa", "Anderson")
        );

        testUsers.forEach(creator -> creatorRepo.create(creator));
    }

    private static Creator createTestUser(Long id, String login, String password, String firstname, String lastname) {
        return Creator.builder()
                .id(id)
                .login(login)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .build();
    }
}
