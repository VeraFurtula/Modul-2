package taxi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

//@Primary
@Service
@Validated
public class DatabasePozivServicePretraga implements PozivService {

	private final PozivDAO pozivDAO;
	private final VoziloDAO voziloDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabasePozivServicePretraga(PozivDAO pozivDAO, VoziloDAO voziloDAO) {
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
		Collection<Poziv> pozivi = pozivDAO.getAll();

		List<Poziv> rezultat = new ArrayList<>();
		for (Poziv itPoziv: pozivi) {
			if ((datumIVremeOd == null || itPoziv.getDatumIVreme().compareTo(datumIVremeOd) >= 0) &&
					(datumIVremeDo == null || itPoziv.getDatumIVreme().compareTo(datumIVremeDo) <= 0) &&
					(ulica == null || itPoziv.getUlica().toLowerCase().contains(ulica.toLowerCase())) && 
					(brojOd <= 0 || itPoziv.getBroj() >= brojOd) && 
					(brojDo <= 0 || itPoziv.getBroj() <= brojDo) &&
					(voziloId == 0 || itPoziv.getVozilo().getId() == voziloId)) {
				rezultat.add(itPoziv);
			}
		}
		return createDTO(rezultat);
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
