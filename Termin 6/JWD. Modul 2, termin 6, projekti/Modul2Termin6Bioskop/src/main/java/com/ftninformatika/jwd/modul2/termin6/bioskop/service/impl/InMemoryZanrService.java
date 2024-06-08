package com.ftninformatika.jwd.modul2.termin6.bioskop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ZanrDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ZanrDTOGet;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.ZanrService;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryZanrService implements ZanrService {

	private final Bioskop bioskop;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryZanrService(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	private ZanrDTOGet createDTO(Zanr zanr) {
		return mapper.map(zanr, ZanrDTOGet.class);
	}

	private Collection<ZanrDTOGet> createDTO(Collection<Zanr> zanrovi) {
		Collection<ZanrDTOGet> zanrDTOs = new ArrayList<>();
		for (Zanr itZanr: zanrovi) {
			ZanrDTOGet zanrDTO = createDTO(itZanr);
			zanrDTOs.add(zanrDTO);
		}
		return zanrDTOs;
	}

	@Override
	public ZanrDTOGet get(long id) {
		Zanr zanr = bioskop.getZanrovi().get(id);
		if (zanr == null) {
			throw new NoSuchElementException("Žanr nije pronađen!");
		}
		return createDTO(zanr);
	}

	@Override
	public Collection<ZanrDTOGet> getAll() {
		Collection<Zanr> zanrovi = bioskop.getZanrovi().values();
		return createDTO(zanrovi);
	}

	@Override
	public Collection<ZanrDTOGet> get(String naziv) {
		Collection<Zanr> zanrovi = bioskop.getZanrovi().values();

		List<Zanr> rezultat = new ArrayList<>();
		for (Zanr itZanr: zanrovi) {
			if (naziv == null || itZanr.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				rezultat.add(itZanr);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid ZanrDTOAddUpdate zanrDTO) {
		long id = bioskop.nextZanrId();
		Zanr zanr = mapper.map(zanrDTO, Zanr.class);
		zanr.setId(id);
		bioskop.getZanrovi().put(id, zanr);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ZanrDTOAddUpdate zanrDTO) {
		long id = zanrDTO.getId();
		Zanr zanr = bioskop.getZanrovi().get(id);
		mapper.map(zanrDTO, zanr);
	}

	@Override
	public void delete(long id) {
		// kaskadno brisanje
		Iterator<Entry<Long, Film>> itEntryFilm = bioskop.getFilmovi().entrySet().iterator();
		while (itEntryFilm.hasNext()) {
			Film itFilm = itEntryFilm.next().getValue();
			for (Zanr itZanr: itFilm.getZanrovi()) {
				if (itZanr.getId() == id) {
					Iterator<Entry<Long, Projekcija>> itEntryProjekcija = bioskop.getProjekcije().entrySet().iterator();
					while (itEntryProjekcija.hasNext()) {
						Projekcija itProjekcija = itEntryProjekcija.next().getValue();
						if (itProjekcija.getFilm().getId() == itFilm.getId()) {
							itEntryProjekcija.remove();
						}
					}
					itEntryFilm.remove();
				}
			}
		}
		bioskop.getZanrovi().remove(id);
	}

}
