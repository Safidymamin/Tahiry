package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.SazyDTO;
import io.tahiry.iombonana.service.SazyService;
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
@RequestMapping(value = "/api/sazies", produces = MediaType.APPLICATION_JSON_VALUE)
public class SazyResource {

    private final SazyService sazyService;

    public SazyResource(final SazyService sazyService) {
        this.sazyService = sazyService;
    }

    @GetMapping
    public ResponseEntity<List<SazyDTO>> getAllSazies() {
        return ResponseEntity.ok(sazyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SazyDTO> getSazy(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sazyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSazy(@RequestBody @Valid final SazyDTO sazyDTO) {
        final Long createdId = sazyService.create(sazyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSazy(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SazyDTO sazyDTO) {
        sazyService.update(id, sazyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSazy(@PathVariable(name = "id") final Long id) {
        sazyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
