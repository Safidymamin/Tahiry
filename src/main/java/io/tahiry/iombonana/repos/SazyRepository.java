package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Cotisation;
import io.tahiry.iombonana.domain.Sazy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SazyRepository extends JpaRepository<Sazy, Long> {

    Sazy findFirstByCoti(Cotisation cotisation);

    boolean existsByCotiId(Long id);

}
