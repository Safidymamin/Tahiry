package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Cotisation;
import io.tahiry.iombonana.domain.Olona;
import io.tahiry.iombonana.domain.Sazy;
import io.tahiry.iombonana.model.CotisationDTO;
import io.tahiry.iombonana.repos.CotisationRepository;
import io.tahiry.iombonana.repos.OlonaRepository;
import io.tahiry.iombonana.repos.SazyRepository;
import io.tahiry.iombonana.util.NotFoundException;
import io.tahiry.iombonana.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CotisationService {

    private final CotisationRepository cotisationRepository;
    private final OlonaRepository olonaRepository;
    private final SazyRepository sazyRepository;

    public CotisationService(final CotisationRepository cotisationRepository,
            final OlonaRepository olonaRepository, final SazyRepository sazyRepository) {
        this.cotisationRepository = cotisationRepository;
        this.olonaRepository = olonaRepository;
        this.sazyRepository = sazyRepository;
    }

    public List<CotisationDTO> findAll() {
        final List<Cotisation> cotisations = cotisationRepository.findAll(Sort.by("id"));
        return cotisations.stream()
                .map(cotisation -> mapToDTO(cotisation, new CotisationDTO()))
                .toList();
    }

    public CotisationDTO get(final Long id) {
        return cotisationRepository.findById(id)
                .map(cotisation -> mapToDTO(cotisation, new CotisationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CotisationDTO cotisationDTO) {
        final Cotisation cotisation = new Cotisation();
        mapToEntity(cotisationDTO, cotisation);
        return cotisationRepository.save(cotisation).getId();
    }

    public void update(final Long id, final CotisationDTO cotisationDTO) {
        final Cotisation cotisation = cotisationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cotisationDTO, cotisation);
        cotisationRepository.save(cotisation);
    }

    public void delete(final Long id) {
        cotisationRepository.deleteById(id);
    }

    private CotisationDTO mapToDTO(final Cotisation cotisation, final CotisationDTO cotisationDTO) {
        cotisationDTO.setId(cotisation.getId());
        cotisationDTO.setDate(cotisation.getDate());
        cotisationDTO.setVola(cotisation.getVola());
        cotisationDTO.setOlona(cotisation.getOlona() == null ? null : cotisation.getOlona().getIdOlona());
        cotisationDTO.setAnarana(cotisation.getOlona() == null ? null : cotisation.getOlona().getAnarana());
        cotisationDTO.setFanampiny(cotisation.getOlona()==null ? null : cotisation.getOlona().getFanampiny());
        return cotisationDTO;
    }

    private Cotisation mapToEntity(final CotisationDTO cotisationDTO, final Cotisation cotisation) {
        cotisation.setDate(cotisationDTO.getDate());
        cotisation.setVola(cotisationDTO.getVola());
        final Olona olona = cotisationDTO.getOlona() == null ? null : olonaRepository.findById(cotisationDTO.getOlona())
                .orElseThrow(() -> new NotFoundException("olona not found"));
        cotisation.setOlona(olona);
        return cotisation;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Cotisation cotisation = cotisationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Sazy cotiSazy = sazyRepository.findFirstByCoti(cotisation);
        if (cotiSazy != null) {
            referencedWarning.setKey("cotisation.sazy.coti.referenced");
            referencedWarning.addParam(cotiSazy.getId());
            return referencedWarning;
        }
        return null;
    }

}
