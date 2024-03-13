package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.FindramanaDTO;
import io.tahiry.iombonana.service.FindramanaService;
import io.tahiry.iombonana.util.ReferencedException;
import io.tahiry.iombonana.util.ReferencedWarning;
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
@RequestMapping(value = "/api/findramanas", produces = MediaType.APPLICATION_JSON_VALUE)
public class FindramanaResource {

    private final FindramanaService findramanaService;

    public FindramanaResource(final FindramanaService findramanaService) {
        this.findramanaService = findramanaService;
    }

    @GetMapping
    public ResponseEntity<List<FindramanaDTO>> getAllFindramanas() {
        return ResponseEntity.ok(findramanaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindramanaDTO> getFindramana(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(findramanaService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFindramana(
            @RequestBody @Valid final FindramanaDTO findramanaDTO) {
        final Long createdId = findramanaService.create(findramanaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFindramana(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FindramanaDTO findramanaDTO) {
        findramanaService.update(id, findramanaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFindramana(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = findramanaService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        findramanaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
