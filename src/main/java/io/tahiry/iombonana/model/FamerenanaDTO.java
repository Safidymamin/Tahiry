package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FamerenanaDTO {

    private Long id;

    @NotNull
    private LocalDate daty;

    @NotNull
    private Double vola;

    private Long trosa;

}
