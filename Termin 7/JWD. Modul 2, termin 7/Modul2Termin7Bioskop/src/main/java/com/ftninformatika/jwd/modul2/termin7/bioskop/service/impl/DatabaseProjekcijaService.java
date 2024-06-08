package com.ftninformatika.jwd.modul2.termin7.bioskop.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ProjekcijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ProjekcijaDTOGet;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.FilmDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ProjekcijaDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.ProjekcijaService;

import jakarta.validation.Valid;

@Primary
@Service
@Validated
public class DatabaseProjekcijaService implements ProjekcijaService {

	private final ProjekcijaDAO projekcijaDAO;
	private final FilmDAO filmDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseProjekcijaService(ProjekcijaDAO projekcijaDAO, FilmDAO filmDAO) {
		this.projekcijaDAO = projekcijaDAO;
		this.filmDAO = filmDAO;
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
		Projekcija projekcija = projekcijaDAO.get(id);
		if (projekcija == null) {
			throw new NoSuchElementException("Projekcija nije pronađena!");
		}
		return createDTO(projekcija);
	}

	@Override
	public Collection<ProjekcijaDTOGet> getAll() {
		Collection<Projekcija> projekcije = projekcijaDAO.getAll();
		return createDTO(projekcije);
	}

	@Override
	public Collection<ProjekcijaDTOGet> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			long filmId, 
			String tip,
			int sala, 
			double cenaKarteOd, double cenaKarteDo) {
		Collection<Projekcija> projekcije = projekcijaDAO.get(
				datumIVremeOd, datumIVremeDo, 
				filmId, 
				tip, 
				sala, 
				cenaKarteOd, cenaKarteDo);
		return createDTO(projekcije);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid ProjekcijaDTOAddUpdate projekcijaDTO) {
		Film film = filmDAO.get(projekcijaDTO.getFilmId()); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			throw new NoSuchElementException("Film nije pronađen!"); // spreči dodavanje
		}
		Projekcija projekcija = mapper.map(projekcijaDTO, Projekcija.class);
		projekcija.setFilm(film); // povezivanje

		projekcijaDAO.add(projekcija);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ProjekcijaDTOAddUpdate projekcijaDTO) {
		Film film = filmDAO.get(projekcijaDTO.getFilmId()); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			throw new NoSuchElementException("Film nije pronađen!"); // spreči dodavanje
		}
		Projekcija projekcija = mapper.map(projekcijaDTO, Projekcija.class);
		projekcija.setFilm(film); // povezivanje

		projekcijaDAO.update(projekcija);
	}

	@Override
	public void delete(long id) {
		projekcijaDAO.delete(id);
	}

}
