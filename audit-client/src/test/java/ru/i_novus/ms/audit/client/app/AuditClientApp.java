package ru.i_novus.ms.audit.client.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.i_novus.ms.audit.client.UserAccessor;
import ru.i_novus.ms.audit.client.model.User;

@SpringBootApplication
public class AuditClientApp {

    private static final String USER_ID = "785";
    private static final String USERNAME = "ekrasulina";

    @Bean
    public UserAccessor userAccessor() {
        return () -> new User(USER_ID, USERNAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuditClientApp.class, args);
    }
}
