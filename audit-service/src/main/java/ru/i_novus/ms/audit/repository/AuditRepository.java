package ru.i_novus.ms.audit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.i_novus.ms.audit.entity.AuditEntity;

import java.util.UUID;

public interface AuditRepository extends JpaRepository<AuditEntity, UUID>, QuerydslPredicateExecutor<AuditEntity> {

//    @Query(value = "select audit from AuditEntity audit " +
//            "where audit.auditObjectNames.id in :objectNameIds and" +
//            " audit.auditObjectTypes.id in :objectTypeIds and" +
//            " audit.auditObjectId like concat('%',:auditObjectId,'%') and"+
//            " audit.auditSourceApplication.id in :sourceApplicationIds and" +
//            " audit.eventDate between :minDate and :maxDate and" +
//            " audit.username like concat('%',:auditObjectId,'%')",
//    countQuery = "select count(audit) from AuditEntity audit " +
//            "where audit.auditObjectNames.id in :objectNameIds and" +
//            " audit.auditObjectTypes.id in :objectTypeIds and" +
//            " audit.auditObjectId like concat('%',:auditObjectId,'%') and"+
//            " audit.auditSourceApplication.id in :sourceApplicationIds and" +
//            " audit.eventDate between :minDate and :maxDate and" +
//            " audit.username like concat('%',:auditObjectId,'%')")
//    Page<AuditEntity> findWithPagination(Pageable pageable,
//                                      @Param("objectNameIds") String[] objectNameIds,
//                                      @Param("objectTypeIds") String[] objectTypeIds,
//                                      @Param("auditObjectId") String auditObjectId,
//                                      @Param("sourceApplicationIds") String[] sourceApplicationIds,
//                                      @Param("minDate") LocalDateTime minDate,
//                                      @Param("maxDate") LocalDateTime maxDate,
//                                      @Param("username") String username);


}
