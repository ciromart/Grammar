package it.alma.geditor.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.alma.geditor.domain.LmTemplate;


/**
 * Spring Data  repository for the LmTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LmTemplateRepository extends JpaRepository<LmTemplate, Long> {

}
