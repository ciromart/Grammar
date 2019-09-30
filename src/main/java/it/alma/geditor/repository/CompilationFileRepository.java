package it.alma.geditor.repository;
import it.alma.geditor.domain.CompilationFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompilationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompilationFileRepository extends JpaRepository<CompilationFile, Long> {

}
