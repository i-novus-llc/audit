package ru.i_novus.ms.audit;

import net.n2oapp.security.admin.rest.client.AdminRestClientConfiguration;
import net.n2oapp.security.admin.web.AdminWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AdminWebConfiguration.class, AdminRestClientConfiguration.class})
public class AuditUIApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuditUIApplication.class, args);
    }
}
