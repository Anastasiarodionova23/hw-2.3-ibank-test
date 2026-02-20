# Проект автоматизации тестирования интернет банка
## Описание проекта
Автоматизированное тестирование интернет-банка с использованием **Selenide** и **DataGenerator**.

## Технологии
- Java 11
- JUnit 5
- Selenide
- JavaFaker (генерация данных)
- REST-assured (API-запросы)
- Gradle

## Структура проекта
```

src/test/java/ru/netology/ibank/
├── data/
│   └── DataGenerator.java         # Генерация и регистрация пользователей через API
├── pages/
│   ├── LoginPage.java              # Page Object страницы входа
│   └── DashboardPage.java          # Page Object личного кабинета
└── tests/
└── LoginTest.java              # Тестовые сценарии

```

## Запуск приложения (SUT)
Перед запуском тестов необходимо запустить SUT:
```bash
  java -jar artifacts/app-ibank.jar -P:profile=test
```

Приложение будет доступно по адресу: http://localhost:9999

## Запуск тестов

```bash
# Запуск всех тестов
./gradlew clean test

# Запуск с headless режимом (для CI)
./gradlew clean test -Dselenide.headless=true

# Просмотр отчета
open build/reports/tests/test/index.html
```

## Тестовые сценарии

1. Успешный вход с активным пользователем
2. Ошибка входа для заблокированного пользователя
3. Проверка валидации полей

## CI (GitHub Actions)

При пуше в ветку master автоматически:

· Загружается SUT
· Запускаются тесты в headless-режиме
· Публикуется отчет о тестировании

## Требования к окружению

- Java 11
- Браузер Chromium 
- Порт 9999 должен быть свободен