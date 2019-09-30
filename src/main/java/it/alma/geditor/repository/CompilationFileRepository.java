package it.alma.geditor.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.alma.geditor.domain.CompilationFile;


/**
 * Spring Data  repository for the CompilationFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompilationFileRepository extends JpaRepository<CompilationFile, Long> {

}
