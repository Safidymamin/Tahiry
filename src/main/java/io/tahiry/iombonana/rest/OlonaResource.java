package io.tahiry.iombonana.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.tahiry.iombonana.model.OlonaDTO;
import io.tahiry.iombonana.service.OlonaService;
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
@RequestMapping(value = "/api/olonas", produces = MediaType.APPLICATION_JSON_VALUE)
public class OlonaResource {

    private final OlonaService olonaService;

    public OlonaResource(final OlonaService olonaService) {
        this.olonaService = olonaService;
    }

    @GetMapping
    public ResponseEntity<List<OlonaDTO>> getAllOlonas() {
        return ResponseEntity.ok(olonaService.findAll());
    }

    @GetMapping("/{idOlona}")
    public ResponseEntity<OlonaDTO> getOlona(@PathVariable(name = "idOlona") final Long idOlona) {
        return ResponseEntity.ok(olonaService.get(idOlona));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOlona(@RequestBody @Valid final OlonaDTO olonaDTO) {
        final Long createdIdOlona = olonaService.create(olonaDTO);
        return new ResponseEntity<>(createdIdOlona, HttpStatus.CREATED);
    }

    @PutMapping("/{idOlona}")
    public ResponseEntity<Long> updateOlona(@PathVariable(name = "idOlona") final Long idOlona,
            @RequestBody @Valid final OlonaDTO olonaDTO) {
        olonaService.update(idOlona, olonaDTO);
        return ResponseEntity.ok(idOlona);
    }

    @DeleteMapping("/{idOlona}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOlona(@PathVariable(name = "idOlona") final Long idOlona) {
        final ReferencedWarning referencedWarning = olonaService.getReferencedWarning(idOlona);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        olonaService.delete(idOlona);
        return ResponseEntity.noContent().build();
    }

}
