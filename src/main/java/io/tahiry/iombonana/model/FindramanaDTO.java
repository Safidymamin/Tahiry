package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FindramanaDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String antony;

    @NotNull
    private LocalDate daty;

    @NotNull
    private Double vola;

    private Long olona;

}
