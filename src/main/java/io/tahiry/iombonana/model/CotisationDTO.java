package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CotisationDTO {

    private Long id;
    @NotNull
    private LocalDate date;
    @NotNull
    private Double vola;
    @NotNull
    private Long olona;
    @NotNull
    private String anarana;
    @NotNull
    private String fanampiny;

}
