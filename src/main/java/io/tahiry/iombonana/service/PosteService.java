package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Olona;
import io.tahiry.iombonana.domain.Poste;
import io.tahiry.iombonana.model.PosteDTO;
import io.tahiry.iombonana.repos.OlonaRepository;
import io.tahiry.iombonana.repos.PosteRepository;
import io.tahiry.iombonana.util.NotFoundException;
import io.tahiry.iombonana.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PosteService {

    private final PosteRepository posteRepository;
    private final OlonaRepository olonaRepository;

    public PosteService(final PosteRepository posteRepository,
            final OlonaRepository olonaRepository) {
        this.posteRepository = posteRepository;
        this.olonaRepository = olonaRepository;
    }

    public List<PosteDTO> findAll() {
        final List<Poste> postes = posteRepository.findAll(Sort.by("id"));
        return postes.stream()
                .map(poste -> mapToDTO(poste, new PosteDTO()))
                .toList();
    }

    public PosteDTO get(final Long id) {
        return posteRepository.findById(id)
                .map(poste -> mapToDTO(poste, new PosteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PosteDTO posteDTO) {
        final Poste poste = new Poste();
        mapToEntity(posteDTO, poste);
        return posteRepository.save(poste).getId();
    }

    public void update(final Long id, final PosteDTO posteDTO) {
        final Poste poste = posteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(posteDTO, poste);
        posteRepository.save(poste);
    }

    public void delete(final Long id) {
        posteRepository.deleteById(id);
    }

    private PosteDTO mapToDTO(final Poste poste, final PosteDTO posteDTO) {
        posteDTO.setId(poste.getId());
        posteDTO.setPoste(poste.getPoste());
        return posteDTO;
    }

    private Poste mapToEntity(final PosteDTO posteDTO, final Poste poste) {
        poste.setPoste(posteDTO.getPoste());
        return poste;
    }

    public boolean posteExists(final String poste) {
        return posteRepository.existsByPosteIgnoreCase(poste);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Poste poste = posteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Olona posteOlona = olonaRepository.findFirstByPoste(poste);
        if (posteOlona != null) {
            referencedWarning.setKey("poste.olona.poste.referenced");
            referencedWarning.addParam(posteOlona.getIdOlona());
            return referencedWarning;
        }
        return null;
    }

}
