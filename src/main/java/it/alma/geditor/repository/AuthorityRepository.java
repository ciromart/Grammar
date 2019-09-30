package it.alma.geditor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.alma.geditor.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
