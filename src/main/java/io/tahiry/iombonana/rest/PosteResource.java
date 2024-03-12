package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.PosteDTO;
import io.tahiry.iombonana.service.PosteService;
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
@RequestMapping(value = "/api/postes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PosteResource {

    private final PosteService posteService;

    public PosteResource(final PosteService posteService) {
        this.posteService = posteService;
    }

    @GetMapping
    public ResponseEntity<List<PosteDTO>> getAllPostes() {
        return ResponseEntity.ok(posteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosteDTO> getPoste(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(posteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPoste(@RequestBody @Valid final PosteDTO posteDTO) {
        final Long createdId = posteService.create(posteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePoste(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PosteDTO posteDTO) {
        posteService.update(id, posteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePoste(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = posteService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        posteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
