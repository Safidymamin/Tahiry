package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.FamerenanaDTO;
import io.tahiry.iombonana.service.FamerenanaService;
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
@RequestMapping(value = "/api/famerenanas", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamerenanaResource {

    private final FamerenanaService famerenanaService;

    public FamerenanaResource(final FamerenanaService famerenanaService) {
        this.famerenanaService = famerenanaService;
    }

    @GetMapping
    public ResponseEntity<List<FamerenanaDTO>> getAllFamerenanas() {
        return ResponseEntity.ok(famerenanaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamerenanaDTO> getFamerenana(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(famerenanaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFamerenana(
            @RequestBody @Valid final FamerenanaDTO famerenanaDTO) {
        final Long createdId = famerenanaService.create(famerenanaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFamerenana(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FamerenanaDTO famerenanaDTO) {
        famerenanaService.update(id, famerenanaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFamerenana(@PathVariable(name = "id") final Long id) {
        famerenanaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
