package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Olona;
import io.tahiry.iombonana.domain.Poste;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OlonaRepository extends JpaRepository<Olona, Long> {

    Olona findFirstByPoste(Poste poste);

    boolean existsByAnaranaIgnoreCase(String anarana);

    boolean existsByFanampinyIgnoreCase(String fanampiny);

}
