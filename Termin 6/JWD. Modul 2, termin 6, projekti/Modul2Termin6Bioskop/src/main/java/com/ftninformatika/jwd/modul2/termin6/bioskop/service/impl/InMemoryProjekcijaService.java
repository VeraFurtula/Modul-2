package com.ftninformatika.jwd.modul2.termin6.bioskop.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ProjekcijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ProjekcijaDTOGet;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.ProjekcijaService;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryProjekcijaService implements ProjekcijaService {

	private final Bioskop bioskop;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryProjekcijaService(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	private ProjekcijaDTOGet createDTO(Projekcija projekcija) {
		return mapper.map(projekcija, ProjekcijaDTOGet.class);
	}

	private Collection<ProjekcijaDTOGet> createDTO(Collection<Projekcija> projekcije) {
		Collection<ProjekcijaDTOGet> projekcijaDTOs = new ArrayList<>();
		for (Projekcija itProjekcija: projekcije) {
			ProjekcijaDTOGet projekcijaDTO = createDTO(itProjekcija);
			projekcijaDTOs.add(projekcijaDTO);
		}
		return projekcijaDTOs;
	}
	
	@Override
	public ProjekcijaDTOGet get(long id) {
		Projekcija projekcija = bioskop.getProjekcije().get(id);
		if (projekcija == null) {
			throw new NoSuchElementException("Projekcija nije pronađena!");
		}
		return createDTO(projekcija);
	}

	@Override
	public Collection<ProjekcijaDTOGet> getAll() {
		Collection<Projekcija> projekcije = bioskop.getProjekcije().values();
		return createDTO(projekcije);
	}

	@Override
	public Collection<ProjekcijaDTOGet> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			long filmId, 
			String tip,
			int sala, 
			double cenaKarteOd, double cenaKarteDo) {
		Collection<Projekcija> projekcije = bioskop.getProjekcije().values();

		List<Projekcija> rezultat = new ArrayList<>();
		for (Projekcija itProjekcija: projekcije) {
			if ((datumIVremeOd == null || itProjekcija.getDatumIVreme().compareTo(datumIVremeOd) >= 0) && 
					(datumIVremeDo == null || itProjekcija.getDatumIVreme().compareTo(datumIVremeDo) <= 0) && 
					(filmId == 0 || itProjekcija.getFilm().getId() == filmId) && 
					(tip == null || tip.equals("") || itProjekcija.getTip().equals(tip)) && 
					(sala <= 0 || sala > 3 || itProjekcija.getSala() == sala) && 
					(cenaKarteOd <= 0 || itProjekcija.getCenaKarte() >= cenaKarteOd) && 
					(cenaKarteDo <= 0 || itProjekcija.getCenaKarte() <= cenaKarteDo)) {
				rezultat.add(itProjekcija);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid ProjekcijaDTOAddUpdate projekcijaDTO) {
		Film film = bioskop.getFilmovi().get(projekcijaDTO.getFilmId()); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			throw new NoSuchElementException("Film nije pronađen!"); // spreči dodavanje
		}
		long id = bioskop.nextProjekcijaId();
		Projekcija projekcija = mapper.map(projekcijaDTO, Projekcija.class);
		projekcija.setId(id);
		projekcija.setFilm(film); // povezivanje
		bioskop.getProjekcije().put(id, projekcija);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ProjekcijaDTOAddUpdate projekcijaDTO) {
		Film film = bioskop.getFilmovi().get(projekcijaDTO.getFilmId()); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			throw new NoSuchElementException("Film nije pronađen!"); // spreči dodavanje
		}
		long id = projekcijaDTO.getId();
		Projekcija projekcija = bioskop.getProjekcije().get(id);
		mapper.map(projekcijaDTO, projekcija);
		projekcija.setFilm(film); // povezivanje
	}

	@Override
	public void delete(long id) {
		bioskop.getProjekcije().remove(id);
	}

}
