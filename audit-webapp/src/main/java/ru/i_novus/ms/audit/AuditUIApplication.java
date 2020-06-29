package ru.i_novus.ms.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import ru.i_novus.ms.audit.web.controller.AuditN2oControllerConfiguration;
import net.n2oapp.security.admin.rest.client.AdminRestClientConfiguration;

@SpringBootApplication
@Import({AuditN2oControllerConfiguration.class, AdminRestClientConfiguration.class})
public class AuditUIApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuditUIApplication.class, args);
    }
}
