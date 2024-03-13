package io.tahiry.iombonana.repos;

import io.tahiry.iombonana.domain.Findramana;
import io.tahiry.iombonana.domain.Olona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FindramanaRepository extends JpaRepository<Findramana, Long> {

    Findramana findFirstByOlona(Olona olona);

}
