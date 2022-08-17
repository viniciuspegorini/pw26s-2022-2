package br.edu.utfpr.estacionafacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(
        basePackageClasses = {MainServer.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
@EnableScheduling
public class MainServer extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainServer.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainServer.class, args);
    }
}