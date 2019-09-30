package it.alma.geditor.repository;
import it.alma.geditor.domain.AppConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AppConfig entity.
 */
@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    @Query(value = "select distinct appConfig from AppConfig appConfig left join fetch appConfig.users",
        countQuery = "select count(distinct appConfig) from AppConfig appConfig")
    Page<AppConfig> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct appConfig from AppConfig appConfig left join fetch appConfig.users")
    List<AppConfig> findAllWithEagerRelationships();

    @Query("select appConfig from AppConfig appConfig left join fetch appConfig.users where appConfig.id =:id")
    Optional<AppConfig> findOneWithEagerRelationships(@Param("id") Long id);

}
