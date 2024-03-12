package io.tahiry.iombonana.service;

import io.tahiry.iombonana.domain.Cotisation;
import io.tahiry.iombonana.domain.Olona;
import io.tahiry.iombonana.domain.Poste;
import io.tahiry.iombonana.model.OlonaDTO;
import io.tahiry.iombonana.repos.CotisationRepository;
import io.tahiry.iombonana.repos.OlonaRepository;
import io.tahiry.iombonana.repos.PosteRepository;
import io.tahiry.iombonana.util.NotFoundException;
import io.tahiry.iombonana.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OlonaService {

    private final OlonaRepository olonaRepository;
    private final PosteRepository posteRepository;
    private final CotisationRepository cotisationRepository;

    public OlonaService(final OlonaRepository olonaRepository,
            final PosteRepository posteRepository,
            final CotisationRepository cotisationRepository) {
        this.olonaRepository = olonaRepository;
        this.posteRepository = posteRepository;
        this.cotisationRepository = cotisationRepository;
    }

    public List<OlonaDTO> findAll() {
        final List<Olona> olonas = olonaRepository.findAll(Sort.by("idOlona"));
        return olonas.stream()
                .map(olona -> mapToDTO(olona, new OlonaDTO()))
                .toList();
    }

    public OlonaDTO get(final Long idOlona) {
        return olonaRepository.findById(idOlona)
                .map(olona -> mapToDTO(olona, new OlonaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OlonaDTO olonaDTO) {
        final Olona olona = new Olona();
        mapToEntity(olonaDTO, olona);
        return olonaRepository.save(olona).getIdOlona();
    }

    public void update(final Long idOlona, final OlonaDTO olonaDTO) {
        final Olona olona = olonaRepository.findById(idOlona)
                .orElseThrow(NotFoundException::new);
        mapToEntity(olonaDTO, olona);
        olonaRepository.save(olona);
    }

    public void delete(final Long idOlona) {
        olonaRepository.deleteById(idOlona);
    }

    private OlonaDTO mapToDTO(final Olona olona, final OlonaDTO olonaDTO) {
        olonaDTO.setIdOlona(olona.getIdOlona());
        olonaDTO.setAnarana(olona.getAnarana());
        olonaDTO.setFanampiny(olona.getFanampiny());
        olonaDTO.setAdress(olona.getAdress());
        olonaDTO.setPoste(olona.getPoste() == null ? null : olona.getPoste().getId());
        return olonaDTO;
    }

    private Olona mapToEntity(final OlonaDTO olonaDTO, final Olona olona) {
        olona.setAnarana(olonaDTO.getAnarana());
        olona.setFanampiny(olonaDTO.getFanampiny());
        olona.setAdress(olonaDTO.getAdress());
        final Poste poste = olonaDTO.getPoste() == null ? null : posteRepository.findById(olonaDTO.getPoste())
                .orElseThrow(() -> new NotFoundException("poste not found"));
        olona.setPoste(poste);
        return olona;
    }

    public boolean anaranaExists(final String anarana) {
        return olonaRepository.existsByAnaranaIgnoreCase(anarana);
    }

    public boolean fanampinyExists(final String fanampiny) {
        return olonaRepository.existsByFanampinyIgnoreCase(fanampiny);
    }

    public ReferencedWarning getReferencedWarning(final Long idOlona) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Olona olona = olonaRepository.findById(idOlona)
                .orElseThrow(NotFoundException::new);
        final Cotisation olonaCotisation = cotisationRepository.findFirstByOlona(olona);
        if (olonaCotisation != null) {
            referencedWarning.setKey("olona.cotisation.olona.referenced");
            referencedWarning.addParam(olonaCotisation.getId());
            return referencedWarning;
        }
        return null;
    }

}
