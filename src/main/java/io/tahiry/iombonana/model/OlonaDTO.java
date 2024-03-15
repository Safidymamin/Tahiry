package io.tahiry.iombonana.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OlonaDTO {

    private Long idOlona;

    @NotNull
    @Size(max = 255)
    @OlonaAnaranaUnique
    private String anarana;

    @NotNull
    @Size(max = 255)
    @OlonaFanampinyUnique
    private String fanampiny;

    @NotNull
    @Size(max = 255)
    private String adress;

    @NotNull
    private Long poste_id;

    @NotNull
    private String poste;

}
