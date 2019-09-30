package it.alma.geditor.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.alma.geditor.domain.CompilationLog;


/**
 * Spring Data  repository for the CompilationLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompilationLogRepository extends JpaRepository<CompilationLog, Long> {

}
