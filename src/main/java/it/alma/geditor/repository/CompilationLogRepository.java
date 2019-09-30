package it.alma.geditor.repository;
import it.alma.geditor.domain.CompilationLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompilationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompilationLogRepository extends JpaRepository<CompilationLog, Long> {

}
