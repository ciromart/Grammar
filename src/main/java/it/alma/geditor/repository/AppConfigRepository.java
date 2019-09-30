package it.alma.geditor.repository;
import it.alma.geditor.domain.AppConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AppConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

}
