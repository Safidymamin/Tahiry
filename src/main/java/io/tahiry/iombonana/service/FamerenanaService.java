package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Famerenana;
import io.tahiry.iombonana.domain.Findramana;
import io.tahiry.iombonana.model.FamerenanaDTO;
import io.tahiry.iombonana.repos.FamerenanaRepository;
import io.tahiry.iombonana.repos.FindramanaRepository;
import io.tahiry.iombonana.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FamerenanaService {

    private final FamerenanaRepository famerenanaRepository;
    private final FindramanaRepository findramanaRepository;

    public FamerenanaService(final FamerenanaRepository famerenanaRepository,
            final FindramanaRepository findramanaRepository) {
        this.famerenanaRepository = famerenanaRepository;
        this.findramanaRepository = findramanaRepository;
    }

    public List<FamerenanaDTO> findAll() {
        final List<Famerenana> famerenanas = famerenanaRepository.findAll(Sort.by("id"));
        return famerenanas.stream()
                .map(famerenana -> mapToDTO(famerenana, new FamerenanaDTO()))
                .toList();
    }

    public FamerenanaDTO get(final Long id) {
        return famerenanaRepository.findById(id)
                .map(famerenana -> mapToDTO(famerenana, new FamerenanaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FamerenanaDTO famerenanaDTO) {
        final Famerenana famerenana = new Famerenana();
        mapToEntity(famerenanaDTO, famerenana);
        return famerenanaRepository.save(famerenana).getId();
    }

    public void update(final Long id, final FamerenanaDTO famerenanaDTO) {
        final Famerenana famerenana = famerenanaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(famerenanaDTO, famerenana);
        famerenanaRepository.save(famerenana);
    }

    public void delete(final Long id) {
        famerenanaRepository.deleteById(id);
    }

    private FamerenanaDTO mapToDTO(final Famerenana famerenana, final FamerenanaDTO famerenanaDTO) {
        famerenanaDTO.setId(famerenana.getId());
        famerenanaDTO.setDaty(famerenana.getDaty());
        famerenanaDTO.setVola(famerenana.getVola());
        famerenanaDTO.setTrosa(famerenana.getTrosa() == null ? null : famerenana.getTrosa().getId());
        return famerenanaDTO;
    }

    private Famerenana mapToEntity(final FamerenanaDTO famerenanaDTO, final Famerenana famerenana) {
        famerenana.setDaty(famerenanaDTO.getDaty());
        famerenana.setVola(famerenanaDTO.getVola());
        final Findramana trosa = famerenanaDTO.getTrosa() == null ? null : findramanaRepository.findById(famerenanaDTO.getTrosa())
                .orElseThrow(() -> new NotFoundException("trosa not found"));
        famerenana.setTrosa(trosa);
        return famerenana;
    }

}
