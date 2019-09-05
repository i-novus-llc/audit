package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditObjectEntity;

import java.util.Optional;

public interface AuditObjectRepository extends JpaRepository<AuditObjectEntity, Integer>, QuerydslPredicateExecutor<AuditObjectEntity> {

    @Query("select id from AuditObjectEntity ")
    String[] findAllObjectTypeId();

    Optional<AuditObjectEntity> findByNameAndType(String name, String type);
}
