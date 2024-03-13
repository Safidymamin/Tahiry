package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Famerenana;
import io.tahiry.iombonana.domain.Findramana;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FamerenanaRepository extends JpaRepository<Famerenana, Long> {

    Famerenana findFirstByTrosa(Findramana findramana);

}
