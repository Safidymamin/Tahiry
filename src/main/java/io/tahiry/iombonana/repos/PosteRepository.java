package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Poste;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PosteRepository extends JpaRepository<Poste, Long> {

    boolean existsByPosteIgnoreCase(String poste);

}
