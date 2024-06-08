package taxi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import taxi.dto.PozivDTOAddUpdate;
import taxi.dto.PozivDTOGet;
import taxi.dto.validation.Validation;
import taxi.model.Poziv;
import taxi.model.Vozilo;
import taxi.repository.PozivDAO;
import taxi.repository.VoziloDAO;
import taxi.service.PozivService;

@Primary
@Service
@Validated
public class DatabasePozivService implements PozivService {

	private final PozivDAO pozivDAO;
	private final VoziloDAO voziloDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabasePozivService(PozivDAO pozivDAO, VoziloDAO voziloDAO) {
		this.pozivDAO = pozivDAO;
		this.voziloDAO = voziloDAO;
	}

	private PozivDTOGet createDTO(Poziv poziv) {
		return mapper.map(poziv, PozivDTOGet.class);
	}

	private Collection<PozivDTOGet> createDTO(Collection<Poziv> pozivi) {
		Collection<PozivDTOGet> pozivDTOs = new ArrayList<>();
		for (Poziv itPoziv: pozivi) {
			PozivDTOGet pozivDTO = createDTO(itPoziv);
			pozivDTOs.add(pozivDTO);
		}
		return pozivDTOs;
	}

	@Override
	public Collection<PozivDTOGet> get(LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, String ulica,
			int brojOd, int brojDo, long voziloId) {
		Collection<Poziv> pozivi = pozivDAO.get(datumIVremeOd, datumIVremeDo, ulica, brojOd, brojDo, voziloId);
		return createDTO(pozivi);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid PozivDTOAddUpdate pozivDTO) {
		Vozilo vozilo = voziloDAO.get(pozivDTO.getVoziloId());
		if (vozilo == null) {
			throw new NoSuchElementException("Vozilo nije pronađeno!");
		}
		Poziv poziv = mapper.map(pozivDTO, Poziv.class);
		poziv.setVozilo(vozilo);

		pozivDAO.add(poziv);
	}

	@Override
	public void delete(long id) {
		pozivDAO.delete(id);
	}

}
