package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SazyDTO {

    private Long id;

    @NotNull
    private Double vola;

    @SazyCotiUnique
    private Long coti;

}
