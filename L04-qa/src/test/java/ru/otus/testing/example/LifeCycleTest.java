package ru.otus.testing.example;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S2699"})
class LifeCycleTest {
    private static final Logger logger = LoggerFactory.getLogger(LifeCycleTest.class);

    // Подготовительные мероприятия. Метод выполнится один раз, перед всеми тестами
    @BeforeAll
    public static void globalSetUp() {
        System.out.println("@BeforeAll");
    }

    // Подготовительные мероприятия. Метод выполнится перед каждым тестом
    @BeforeEach
    public void setUp() {
        System.out.println("\n@BeforeEach. ");
        System.out.printf("Экземпляр тестового класса: %s%n", Integer.toHexString(hashCode()));
    }

    // Сам тест
    @Test
    void anyTest1() {
        System.out.println("@Test: anyTest1. ");
        System.out.printf("Экземпляр тестового класса: %s%n", Integer.toHexString(hashCode()));
    }

    // Еще тест
    @Test
    void anyTest2() {
        System.out.println("@Test: anyTest2. ");
        System.out.printf("Экземпляр тестового класса: %s%n", Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится после каждого теста
    @AfterEach
    public void tearDown() {
        System.out.println("@AfterEach. ");
        System.out.printf("Экземпляр тестового класса: %s%n", Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится один раз, после всех тестов
    @AfterAll
    public static void globalTearDown() {
        System.out.println("\n@AfterAll");
    }
}
