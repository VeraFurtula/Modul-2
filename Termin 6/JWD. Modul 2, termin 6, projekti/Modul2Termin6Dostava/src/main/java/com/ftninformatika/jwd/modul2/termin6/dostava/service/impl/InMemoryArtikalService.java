package com.ftninformatika.jwd.modul2.termin6.dostava.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.ArtikalDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.dostava.dto.ArtikalDTOGet;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Artikal;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Restoran;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.ArtikalService;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.Dostava;
import com.informatika.jwd.modul2.termin6.dostava.dto.validation.Validation;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryArtikalService implements ArtikalService {

	private final Dostava dostava;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryArtikalService(Dostava dostava) {
		this.dostava = dostava;
	}

	private ArtikalDTOGet createDTO(Artikal artikal) {
		return mapper.map(artikal, ArtikalDTOGet.class);
	}
	
	private Collection<ArtikalDTOGet> createDTO(Collection<Artikal> artikli) {
		Collection<ArtikalDTOGet> artikalDTOs = new ArrayList<>();
		for (Artikal itArtikal: artikli) {
			ArtikalDTOGet artikalDTO = createDTO(itArtikal);
			artikalDTOs.add(artikalDTO);
		}
		return artikalDTOs;
	}
	
	@Override
	public ArtikalDTOGet get(long id) {
		Artikal artikal = dostava.getArtikli().get(id);
		if (artikal == null) {
			throw new NoSuchElementException("Artikal nije pronađen!");
		}
		return createDTO(artikal);
	}

	@Override
	public Collection<ArtikalDTOGet> getAll() {
		Collection<Artikal> artikli = dostava.getArtikli().values();
		return createDTO(artikli);
	}

	@Override
	public Collection<ArtikalDTOGet> get(String naziv) {
		Collection<Artikal> artikli = dostava.getArtikli().values();

		List<Artikal> rezultat = new ArrayList<>();
		for (Artikal itArtikal: artikli) {
			if (naziv == null || itArtikal.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				rezultat.add(itArtikal);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid ArtikalDTOAddUpdate artikalDTO) {
		Restoran restoran = dostava.getRestorani().get(artikalDTO.getRestoranId()); // pronalaženje filma po id
		if (restoran == null) { // da li je film pronađen?
			throw new NoSuchElementException("Restoran nije pronađen!"); // spreči dodavanje
		}
		long id = dostava.nextArtikalId();
		Artikal artikal = mapper.map(artikalDTO, Artikal.class);
		artikal.setId(id);
		artikal.setRestoran(restoran);; // povezivanje
		dostava.getArtikli().put(id, artikal);

	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ArtikalDTOAddUpdate artikalDTO) {
		Restoran restoran = dostava.getRestorani().get(artikalDTO.getRestoranId());
		if (restoran == null) {
			throw new NoSuchElementException("Restoran nije pronađen!"); // spreči dodavanje
		}
		long id = artikalDTO.getId();
		Artikal artikal = dostava.getArtikli().get(id);
		mapper.map(artikalDTO, artikal);
		artikal.setRestoran(restoran); // povezivanje

	}

	@Override
	public void delete(long id) {
		dostava.getArtikli().remove(id);

	}



}
