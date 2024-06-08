package com.ftninformatika.jwd.modul2.termin6.bioskop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.FilmDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.FilmDTOGet;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.FilmService;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryFilmService implements FilmService {

	private final Bioskop bioskop;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryFilmService(Bioskop bioskop) {
		this.bioskop = bioskop;
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
		Film film = bioskop.getFilmovi().get(id);
		if (film == null) {
			throw new NoSuchElementException("Film nije pronađen!");
		}
		return createDTO(film);
	}

	@Override
	public Collection<FilmDTOGet> getAll() {
		Collection<Film> filmovi = bioskop.getFilmovi().values();
		return createDTO(filmovi);
	}

	@Override
	public Collection<FilmDTOGet> get(String naziv, long zanrId, int trajanjeOd, int trajanjeDo) {
		Collection<Film> filmovi = bioskop.getFilmovi().values();

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
			Zanr itZanr = bioskop.getZanrovi().get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			throw new NoSuchElementException("Nije pronađen nijedan žanr!"); // spreči dodavanje
		}
		long id = bioskop.nextFilmId();
		Film film = mapper.map(filmDTO, Film.class);
		film.setId(id);
		film.setZanrovi(zanrovi); // povezivanje
		bioskop.getFilmovi().put(id, film);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid FilmDTOAddUpdate filmDTO) {
		Set<Zanr> zanrovi = new LinkedHashSet<>();
		for (long itZanrId: filmDTO.getZanrIds()) { // pretraga žanrova po id
			Zanr itZanr = bioskop.getZanrovi().get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			throw new NoSuchElementException("Nije pronađen nijedan žanr!"); // spreči dodavanje
		}
		long id = filmDTO.getId();
		Film film = bioskop.getFilmovi().get(id);
		mapper.map(filmDTO, film);
		film.setZanrovi(zanrovi); // povezivanje
	}

	@Override
	public void delete(long id) {
		 // kaskadno brisanje
		Iterator<Entry<Long, Projekcija>> itEntryProjekcija = bioskop.getProjekcije().entrySet().iterator();
		while (itEntryProjekcija.hasNext()) {
			Projekcija itProjekcija = itEntryProjekcija.next().getValue();
			if (itProjekcija.getFilm().getId() == id) {
				itEntryProjekcija.remove();
			}
		}
		bioskop.getFilmovi().remove(id);
	}

}
