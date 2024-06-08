package com.ftninformatika.jwd.modul2.termin7.dostava.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.ArtikalDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.ArtikalDTOGet;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.dostava.model.Artikal;
import com.ftninformatika.jwd.modul2.termin7.dostava.model.Restoran;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.ArtikalService;

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
	public Collection<ArtikalDTOGet> get(String naziv, String opis, double cenaOd, double cenaDo, long restoranId) {
		Collection<Artikal> artikli = dostava.getArtikli().values();

		List<Artikal> rezultat = new ArrayList<>();
		for (Artikal itArtikal: artikli) {
			if ((naziv == null || itArtikal.getNaziv().toLowerCase().contains(naziv.toLowerCase())) && 
					(opis == null || itArtikal.getOpis().toLowerCase().contains(opis.toLowerCase())) &&
					(cenaOd <= 0 || itArtikal.getCena() >= cenaOd) && 
					(cenaDo <= 0 || itArtikal.getCena() <= cenaDo) &&
					(restoranId <= 0 || itArtikal.getRestoran().getId() == restoranId)) {
				rezultat.add(itArtikal);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid ArtikalDTOAddUpdate artikalDTO) {
		Restoran restoran = dostava.getRestorani().get(artikalDTO.getRestoranId()); // pronalaženje restorana po id
		if (restoran == null) { // da li je restoran pronađen?
			throw new NoSuchElementException("Restoran nije pronađen!"); // spreči dodavanje
		}
		long id = dostava.nextArtikalId();
		Artikal artikal = mapper.map(artikalDTO, Artikal.class);
		artikal.setId(id);
		artikal.setRestoran(restoran); // povezivanje
		dostava.getArtikli().put(id, artikal);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ArtikalDTOAddUpdate artikalDTO) {
		Restoran restoran = dostava.getRestorani().get(artikalDTO.getRestoranId()); // pronalaženje restorana po id
		if (restoran == null) { // da li je restoran pronađen?
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
