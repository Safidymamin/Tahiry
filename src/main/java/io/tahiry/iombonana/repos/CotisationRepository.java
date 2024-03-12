package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Cotisation;
import io.tahiry.iombonana.domain.Olona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CotisationRepository extends JpaRepository<Cotisation, Long> {

    Cotisation findFirstByOlona(Olona olona);

}
