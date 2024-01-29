package com.khomsi.backend.config;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(parallel = true)
@Transactional
@ActiveProfiles("test")
public class BaseIntegration {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("GridDB");

    static {
        MY_SQL_CONTAINER.start();
    }
}