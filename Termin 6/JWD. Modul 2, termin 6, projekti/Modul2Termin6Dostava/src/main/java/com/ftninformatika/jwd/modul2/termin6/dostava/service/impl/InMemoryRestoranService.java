package com.ftninformatika.jwd.modul2.termin6.dostava.service.impl;

import java.time.LocalDate;
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


import com.ftninformatika.jwd.modul2.termin6.dostava.dto.RestoranAddUpdateDTO;
import com.ftninformatika.jwd.modul2.termin6.dostava.dto.RestoranDTOGet;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Artikal;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Kategorija;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Restoran;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.Dostava;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.RestoranService;
import com.informatika.jwd.modul2.termin6.dostava.dto.validation.Validation;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryRestoranService implements RestoranService {
	private final Dostava dostava;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryRestoranService(Dostava dostava) {
		this.dostava = dostava;
	}

	private RestoranDTOGet createDTO(Restoran restoran) {
		return mapper.map(restoran, RestoranDTOGet.class);
	}

	private Collection<RestoranDTOGet> createDTO(Collection<Restoran> restorani) {
		Collection<RestoranDTOGet> restoranDTOs = new ArrayList<>();
		for (Restoran itRestoran: restorani) {
			RestoranDTOGet restoranDTO = createDTO(itRestoran);
			restoranDTOs.add(restoranDTO);
		}
		return restoranDTOs;
	}

	@Override
	public RestoranDTOGet get(long id) {
		Restoran restoran = dostava.getRestorani().get(id);
		if (restoran == null) {
			throw new NoSuchElementException("Restoran nije pronađen!");
		}
		return createDTO(restoran);
	}

	@Override
	public Collection<RestoranDTOGet> getAll() {
		Collection<Restoran> restorani = dostava.getRestorani().values();
		return createDTO(restorani);
	}

	@Override
	public Collection<RestoranDTOGet> get(String naziv) {
		Collection<Restoran> restorani = dostava.getRestorani().values();

		List<Restoran> rezultat = new ArrayList<>();
		for (Restoran itRestoran: restorani) {
			if (naziv == null || itRestoran.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				rezultat.add(itRestoran);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid RestoranAddUpdateDTO restoranDTO) {
		Set<Kategorija> kategorije = new LinkedHashSet<>();
		for (long itKategorijaId: restoranDTO.getKategorijaIds()) {
			Kategorija itKategorija = dostava.getKategorije().get(itKategorijaId);
			kategorije.add(itKategorija);
		}
		if (kategorije.isEmpty()) { 
			throw new NoSuchElementException("Nije pronađena nijedna kategorija!"); // spreči dodavanje
		}
		long id = dostava.nextKategorijaId();
		Restoran restoran = mapper.map(restoranDTO, Restoran.class);
		restoran.setKategorije(kategorije); // povezivanje
		dostava.getRestorani().put(id, restoran);

	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid RestoranAddUpdateDTO restoranDTO) {
		Set<Kategorija> kategorije = new LinkedHashSet<>();
		for (long itKategorijaId: restoranDTO.getKategorijaIds()) {
			Kategorija itKategorija = dostava.getKategorije().get(itKategorijaId);
			kategorije.add(itKategorija);
		}
		if (kategorije.isEmpty()) { 
			throw new NoSuchElementException("Nije pronađena nijedna kategorija!"); // spreči dodavanje
		}
		long id = restoranDTO.getId();
		String naziv = restoranDTO.getNaziv();
		LocalDate datumOsnivanja = restoranDTO.getDatumOsnivanja();
		Restoran restoran = dostava.getRestorani().get(id);
		restoran.setNaziv(naziv);
		restoran.setDatumOsnivanja(datumOsnivanja);
		restoran.setKategorije(kategorije); // povezivanje

	}

	@Override
	public void delete(long id) {
		Iterator<Entry<Long, Artikal>> itEntryArtikal = dostava.getArtikli().entrySet().iterator();
		while (itEntryArtikal.hasNext()) {
			Artikal itArtikal = itEntryArtikal.next().getValue();
			if (itArtikal.getRestoran().getId() == id) {
				itEntryArtikal.remove();
			}
		}
		dostava.getRestorani().remove(id);

	}

}
