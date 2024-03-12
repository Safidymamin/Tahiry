package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PosteDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @PostePosteUnique
    private String poste;

}
