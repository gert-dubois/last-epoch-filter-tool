package com.kuansoft.le;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuansoft.le.equipment.EquipmentTypeRepository;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "lastepochfiltergenerator", variant = Lumo.DARK)
@PWA(name = "Last Epoch Filter Generator", shortName = "Last Epoch Filter Generator", offlineResources = {
        "images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EquipmentTypeRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
