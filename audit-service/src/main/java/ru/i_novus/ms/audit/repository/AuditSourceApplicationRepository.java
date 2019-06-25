package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.i_novus.ms.audit.entity.AuditSourceApplication;

import java.util.Optional;
import java.util.UUID;

public interface AuditSourceApplicationRepository extends JpaRepository<AuditSourceApplication, UUID> {

    @Query("select id from AuditSourceApplication ")
    String[] findAllSourceApplicationId();

    Optional<AuditSourceApplication> findByName(String name);
}
