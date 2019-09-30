package it.alma.geditor.repository;
import it.alma.geditor.domain.LmTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the LmTemplate entity.
 */
@Repository
public interface LmTemplateRepository extends JpaRepository<LmTemplate, Long> {

    @Query(value = "select distinct lmTemplate from LmTemplate lmTemplate left join fetch lmTemplate.users",
        countQuery = "select count(distinct lmTemplate) from LmTemplate lmTemplate")
    Page<LmTemplate> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct lmTemplate from LmTemplate lmTemplate left join fetch lmTemplate.users")
    List<LmTemplate> findAllWithEagerRelationships();

    @Query("select lmTemplate from LmTemplate lmTemplate left join fetch lmTemplate.users where lmTemplate.id =:id")
    Optional<LmTemplate> findOneWithEagerRelationships(@Param("id") Long id);

}
