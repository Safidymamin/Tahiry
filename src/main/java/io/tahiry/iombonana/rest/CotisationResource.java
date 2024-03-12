package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.CotisationDTO;
import io.tahiry.iombonana.service.CotisationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/cotisations", produces = MediaType.APPLICATION_JSON_VALUE)
public class CotisationResource {

    private final CotisationService cotisationService;

    public CotisationResource(final CotisationService cotisationService) {
        this.cotisationService = cotisationService;
    }

    @GetMapping
    public ResponseEntity<List<CotisationDTO>> getAllCotisations() {
        return ResponseEntity.ok(cotisationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotisationDTO> getCotisation(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cotisationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCotisation(
            @RequestBody @Valid final CotisationDTO cotisationDTO) {
        final Long createdId = cotisationService.create(cotisationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCotisation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CotisationDTO cotisationDTO) {
        cotisationService.update(id, cotisationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCotisation(@PathVariable(name = "id") final Long id) {
        cotisationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
