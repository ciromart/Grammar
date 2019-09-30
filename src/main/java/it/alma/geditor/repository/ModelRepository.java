package it.alma.geditor.repository;
import it.alma.geditor.domain.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Model entity.
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query(value = "select distinct model from Model model left join fetch model.users",
        countQuery = "select count(distinct model) from Model model")
    Page<Model> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct model from Model model left join fetch model.users")
    List<Model> findAllWithEagerRelationships();

    @Query("select model from Model model left join fetch model.users where model.id =:id")
    Optional<Model> findOneWithEagerRelationships(@Param("id") Long id);

}
