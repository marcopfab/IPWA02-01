# Require4Testing

Webanwendung zur Organisation manueller Anwendertests. Entstanden im Rahmen der
Fallstudie **IPWA02-01 – Programmierung von industriellen Informationssystemen
mit Java EE** (IU Internationale Hochschule).

Require4Testing unterstützt Requirements Engineers, Testfallersteller:innen und
Testmanager:innen bei der strukturierten Durchführung manueller Softwaretests:
Anforderungen werden angelegt, dazu Testfälle definiert, in Testläufen
organisiert, Testfällen und Tester:innen zugewiesen und mit einem Ergebnis
versehen.

## Technologiestack

| Schicht            | Technologie      | Version |
|--------------------|------------------|---------|
| Web-Framework      | Spring Boot      | 3.2.x   |
| Frontend-Template  | Thymeleaf        | 3.1.x   |
| Persistenz-API     | Spring Data JPA  | 3.2.x   |
| ORM / JPA-Provider | Hibernate        | 6.4.x   |
| Datenbank          | MySQL            | 8.0     |
| Build-Tool         | Maven            | 3.9.x   |
| Java               | —                | 21+     |

## Umgesetzte User Stories (Sprint 1)

- **US-1** (MUST): Anforderungen erstellen, bearbeiten, löschen
- **US-2** (MUST): Testläufe anlegen und konfigurieren
- **US-3** (MUST): Testfälle zu einer Anforderung erstellen
- **US-4** (SHOULD): Testfälle und Tester:innen einem Testlauf zuordnen
- **US-5** (SHOULD): Testfall-Ergebnisse erfassen

## Projektstruktur

```
require4testing/
├── src/main/java/com/require4testing/
│   ├── controller/   # Spring MVC @Controller-Beans
│   ├── model/        # JPA @Entity-Klassen
│   ├── repository/   # Spring Data JPA @Repository-Interfaces
│   ├── service/      # @Service-Beans (Geschäftslogik)
│   └── Require4TestingApplication.java
├── src/main/resources/
│   ├── templates/    # Thymeleaf HTML-Templates
│   └── application.properties
└── pom.xml
```

## Datenbankschema

Fünf Kerntabellen: `requirement`, `test_case` (N:1 → requirement), `test_run`,
`test_run_assignment` (N:1 → test_run, N:1 → test_case) und `test_result`
(1:1 → test_run_assignment).

## Setup & Start

**Voraussetzungen:** JDK 21+, Maven 3.9+, MySQL 8.0 (oder H2, siehe unten).

1. MySQL-Datenbank bereitstellen und Zugangsdaten in
   `src/main/resources/application.properties` eintragen
   (`spring.datasource.username` / `spring.datasource.password`).
   Hibernate legt das Schema beim ersten Start automatisch an
   (`spring.jpa.hibernate.ddl-auto=update`).

2. Anwendung starten:

   ```bash
   ./mvnw spring-boot:run
   ```

   Alternativ ohne lokalen MySQL-Server: die H2-In-Memory-Konfiguration am
   Ende von `application.properties` aktivieren (Kommentare entfernen,
   MySQL-Zeilen auskommentieren).

3. Im Browser öffnen: [http://localhost:8080/requirements](http://localhost:8080/requirements)

## URL-Übersicht

| Methode | Pfad                        | Beschreibung                          |
|---------|------------------------------|----------------------------------------|
| GET     | `/requirements`               | Liste aller Anforderungen              |
| GET     | `/requirements/new`           | Formular: neue Anforderung             |
| POST    | `/requirements/save`          | Anforderung speichern                  |
| GET     | `/testcases?reqId={id}`       | Testfälle einer Anforderung            |
| GET     | `/testcases/new?reqId={id}`   | Testfall-Formular                      |
| GET     | `/testruns`                   | Alle Testläufe                         |
| GET     | `/testruns/{id}/assign`       | Zuweisungsseite                        |
| POST    | `/results/save`               | Testergebnis speichern                 |

Vollständige Dokumentation (Architektur, ER-Modell, Use Cases) siehe
Fallstudienbericht.
