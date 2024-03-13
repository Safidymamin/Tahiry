package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Cotisation;
import io.tahiry.iombonana.domain.Sazy;
import io.tahiry.iombonana.model.SazyDTO;
import io.tahiry.iombonana.repos.CotisationRepository;
import io.tahiry.iombonana.repos.SazyRepository;
import io.tahiry.iombonana.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SazyService {

    private final SazyRepository sazyRepository;
    private final CotisationRepository cotisationRepository;

    public SazyService(final SazyRepository sazyRepository,
            final CotisationRepository cotisationRepository) {
        this.sazyRepository = sazyRepository;
        this.cotisationRepository = cotisationRepository;
    }

    public List<SazyDTO> findAll() {
        final List<Sazy> sazies = sazyRepository.findAll(Sort.by("id"));
        return sazies.stream()
                .map(sazy -> mapToDTO(sazy, new SazyDTO()))
                .toList();
    }

    public SazyDTO get(final Long id) {
        return sazyRepository.findById(id)
                .map(sazy -> mapToDTO(sazy, new SazyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SazyDTO sazyDTO) {
        final Sazy sazy = new Sazy();
        mapToEntity(sazyDTO, sazy);
        return sazyRepository.save(sazy).getId();
    }

    public void update(final Long id, final SazyDTO sazyDTO) {
        final Sazy sazy = sazyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sazyDTO, sazy);
        sazyRepository.save(sazy);
    }

    public void delete(final Long id) {
        sazyRepository.deleteById(id);
    }

    private SazyDTO mapToDTO(final Sazy sazy, final SazyDTO sazyDTO) {
        sazyDTO.setId(sazy.getId());
        sazyDTO.setVola(sazy.getVola());
        sazyDTO.setCoti(sazy.getCoti() == null ? null : sazy.getCoti().getId());
        return sazyDTO;
    }

    private Sazy mapToEntity(final SazyDTO sazyDTO, final Sazy sazy) {
        sazy.setVola(sazyDTO.getVola());
        final Cotisation coti = sazyDTO.getCoti() == null ? null : cotisationRepository.findById(sazyDTO.getCoti())
                .orElseThrow(() -> new NotFoundException("coti not found"));
        sazy.setCoti(coti);
        return sazy;
    }

    public boolean cotiExists(final Long id) {
        return sazyRepository.existsByCotiId(id);
    }

}
