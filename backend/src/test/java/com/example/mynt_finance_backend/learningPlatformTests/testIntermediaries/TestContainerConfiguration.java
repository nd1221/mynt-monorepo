package com.example.mynt_finance_backend.learningPlatformTests.testIntermediaries;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface TestContainerConfiguration {

    @Container
    PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3");

    @BeforeAll
    static void beforeAll() {

        postgreSQLContainer.withExposedPorts(5432);
        postgreSQLContainer.start();

        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());

        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
    }

    @AfterAll
    static void afterAll() {
        if (postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }
    }
}
