# Require4Testing

Web-App zur Organisation manueller Anwendertests  
**Kurs:** IPWA02-01 – Programmierung von industriellen Informationssystemen mit Java EE  
**IU Internationale Hochschule** | Marco Pfab | IU14079758

## Technologiestack

| Schicht | Technologie |
|---|---|
| Web-Framework | Spring Boot 3.2 |
| Frontend | Thymeleaf + Bootstrap 5 |
| Persistenz | Spring Data JPA + Hibernate |
| Datenbank | MySQL 8.0 |
| Build | Maven 3.9 |

## Voraussetzungen

- Java 21+
- Maven 3.9+
- MySQL 8.0 (lokal installiert)

## Datenbank einrichten

```sql
CREATE DATABASE require4testing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## Konfiguration anpassen

In `src/main/resources/application.properties`:

```properties
spring.datasource.username=DEIN_MYSQL_BENUTZER
spring.datasource.password=DEIN_MYSQL_PASSWORT
```

## Starten

```bash
mvn spring-boot:run
```

Dann im Browser öffnen: **http://localhost:8080**

## Umgesetzte User Stories (Sprint 1)

| US | Rolle | Funktion |
|---|---|---|
| US-1 (MUST) | Requirements Engineer | Anforderungen erstellen/verwalten |
| US-2 (MUST) | Testmanager:in | Testläufe anlegen |
| US-3 (MUST) | Testfallersteller:in | Testfälle zu Anforderungen erstellen |
| US-4 (SHOULD) | Testmanager:in | Testfälle & Tester:in einem Testlauf zuweisen |
| US-5 (SHOULD) | Tester:in | Testfall-Ergebnis erfassen |
