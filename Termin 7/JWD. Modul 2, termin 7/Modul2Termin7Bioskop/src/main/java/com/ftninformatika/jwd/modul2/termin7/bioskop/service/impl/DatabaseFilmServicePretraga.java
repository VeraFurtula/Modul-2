package com.ftninformatika.jwd.modul2.termin7.bioskop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.FilmDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.FilmDTOGet;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.FilmDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ZanrDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.FilmService;

import jakarta.validation.Valid;

//@Primary
@Service
@Validated
public class DatabaseFilmServicePretraga implements FilmService {

	private final FilmDAO filmDAO;
	private final ZanrDAO zanrDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseFilmServicePretraga(FilmDAO filmDAO, ZanrDAO zanrDAO) {
		this.filmDAO = filmDAO;
		this.zanrDAO = zanrDAO;
	}

	private FilmDTOGet createDTO(Film film) {
		return mapper.map(film, FilmDTOGet.class);
	}

	private Collection<FilmDTOGet> createDTO(Collection<Film> filmovi) {
		Collection<FilmDTOGet> filmDTOs = new ArrayList<>();
		for (Film itFilm: filmovi) {
			FilmDTOGet filmDTO = createDTO(itFilm);
			filmDTOs.add(filmDTO);
		}
		return filmDTOs;
	}

	@Override
	public FilmDTOGet get(long id) {
		Film film = filmDAO.get(id);
		if (film == null) {
			throw new NoSuchElementException("Film nije pronađen!");
		}
		return createDTO(film);
	}

	@Override
	public Collection<FilmDTOGet> getAll() {
		Collection<Film> filmovi = filmDAO.getAll();
		return createDTO(filmovi);
	}

	@Override
	public Collection<FilmDTOGet> get(String naziv, long zanrId, int trajanjeOd, int trajanjeDo) {
		Collection<Film> filmovi = filmDAO.getAll();

		List<Film> rezultat = new ArrayList<>();
		for (Film itFilm: filmovi) {
			if ((naziv == null || itFilm.getNaziv().toLowerCase().contains(naziv.toLowerCase())) && 
					(zanrId <= 0 || itFilm.getZanr(zanrId) != null) && 
					(trajanjeOd <= 0 || itFilm.getTrajanje() >= trajanjeOd) && 
					(trajanjeDo <= 0 || itFilm.getTrajanje() <= trajanjeDo)) {
				rezultat.add(itFilm);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid FilmDTOAddUpdate filmDTO) {
		Set<Zanr> zanrovi = new LinkedHashSet<>();
		for (long itZanrId: filmDTO.getZanrIds()) { // pretraga žanrova po id
			Zanr itZanr = zanrDAO.get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			throw new NoSuchElementException("Nije pronađen nijedan žanr!"); // spreči dodavanje
		}
		Film film = mapper.map(filmDTO, Film.class);
		film.setZanrovi(zanrovi); // povezivanje

		filmDAO.add(film);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid FilmDTOAddUpdate filmDTO) {
		Set<Zanr> zanrovi = new LinkedHashSet<>();
		for (long itZanrId: filmDTO.getZanrIds()) { // pretraga žanrova po id
			Zanr itZanr = zanrDAO.get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			throw new NoSuchElementException("Nije pronađen nijedan žanr!"); // spreči dodavanje
		}
		Film film = mapper.map(filmDTO, Film.class);
		film.setZanrovi(zanrovi); // povezivanje

		filmDAO.update(film);
	}

	@Override
	public void delete(long id) {
		filmDAO.delete(id);
	}

}
