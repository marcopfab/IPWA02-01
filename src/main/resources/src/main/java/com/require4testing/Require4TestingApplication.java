package com.require4testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Einstiegspunkt der Require4Testing-Anwendung.
 * Startet den eingebetteten Tomcat und initialisiert den Spring-Kontext
 * (Component-Scan über das Basispaket com.require4testing).
 */
@SpringBootApplication
public class Require4TestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(Require4TestingApplication.class, args);
    }
}
