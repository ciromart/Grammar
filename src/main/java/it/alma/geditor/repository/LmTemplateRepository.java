package it.alma.geditor.repository;
import it.alma.geditor.domain.LmTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LmTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LmTemplateRepository extends JpaRepository<LmTemplate, Long> {

}
