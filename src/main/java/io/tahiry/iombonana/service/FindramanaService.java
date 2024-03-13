package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Famerenana;
import io.tahiry.iombonana.domain.Findramana;
import io.tahiry.iombonana.domain.Olona;
import io.tahiry.iombonana.model.FindramanaDTO;
import io.tahiry.iombonana.repos.FamerenanaRepository;
import io.tahiry.iombonana.repos.FindramanaRepository;
import io.tahiry.iombonana.repos.OlonaRepository;
import io.tahiry.iombonana.util.NotFoundException;
import io.tahiry.iombonana.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FindramanaService {

    private final FindramanaRepository findramanaRepository;
    private final OlonaRepository olonaRepository;
    private final FamerenanaRepository famerenanaRepository;

    public FindramanaService(final FindramanaRepository findramanaRepository,
            final OlonaRepository olonaRepository,
            final FamerenanaRepository famerenanaRepository) {
        this.findramanaRepository = findramanaRepository;
        this.olonaRepository = olonaRepository;
        this.famerenanaRepository = famerenanaRepository;
    }

    public List<FindramanaDTO> findAll() {
        final List<Findramana> findramanas = findramanaRepository.findAll(Sort.by("id"));
        return findramanas.stream()
                .map(findramana -> mapToDTO(findramana, new FindramanaDTO()))
                .toList();
    }

    public FindramanaDTO get(final Long id) {
        return findramanaRepository.findById(id)
                .map(findramana -> mapToDTO(findramana, new FindramanaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FindramanaDTO findramanaDTO) {
        final Findramana findramana = new Findramana();
        mapToEntity(findramanaDTO, findramana);
        return findramanaRepository.save(findramana).getId();
    }

    public void update(final Long id, final FindramanaDTO findramanaDTO) {
        final Findramana findramana = findramanaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(findramanaDTO, findramana);
        findramanaRepository.save(findramana);
    }

    public void delete(final Long id) {
        findramanaRepository.deleteById(id);
    }

    private FindramanaDTO mapToDTO(final Findramana findramana, final FindramanaDTO findramanaDTO) {
        findramanaDTO.setId(findramana.getId());
        findramanaDTO.setAntony(findramana.getAntony());
        findramanaDTO.setDaty(findramana.getDaty());
        findramanaDTO.setVola(findramana.getVola());
        findramanaDTO.setOlona(findramana.getOlona() == null ? null : findramana.getOlona().getIdOlona());
        return findramanaDTO;
    }

    private Findramana mapToEntity(final FindramanaDTO findramanaDTO, final Findramana findramana) {
        findramana.setAntony(findramanaDTO.getAntony());
        findramana.setDaty(findramanaDTO.getDaty());
        findramana.setVola(findramanaDTO.getVola());
        final Olona olona = findramanaDTO.getOlona() == null ? null : olonaRepository.findById(findramanaDTO.getOlona())
                .orElseThrow(() -> new NotFoundException("olona not found"));
        findramana.setOlona(olona);
        return findramana;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Findramana findramana = findramanaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Famerenana trosaFamerenana = famerenanaRepository.findFirstByTrosa(findramana);
        if (trosaFamerenana != null) {
            referencedWarning.setKey("findramana.famerenana.trosa.referenced");
            referencedWarning.addParam(trosaFamerenana.getId());
            return referencedWarning;
        }
        return null;
    }

}
