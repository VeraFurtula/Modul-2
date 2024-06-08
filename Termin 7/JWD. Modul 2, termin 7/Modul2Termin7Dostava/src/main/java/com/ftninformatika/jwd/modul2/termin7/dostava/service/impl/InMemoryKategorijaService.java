package com.ftninformatika.jwd.modul2.termin7.dostava.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.KategorijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.KategorijaDTOGet;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.dostava.model.Artikal;
import com.ftninformatika.jwd.modul2.termin7.dostava.model.Kategorija;
import com.ftninformatika.jwd.modul2.termin7.dostava.model.Restoran;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.KategorijaService;

import jakarta.validation.Valid;

@Service
@Validated
public class InMemoryKategorijaService implements KategorijaService {

	private final Dostava dostava;
	private final ModelMapper mapper = new ModelMapper();

	public InMemoryKategorijaService(Dostava dostava) {
		this.dostava = dostava;
	}

	private KategorijaDTOGet createDTO(Kategorija kategorija) {
		return mapper.map(kategorija, KategorijaDTOGet.class);
	}

	private Collection<KategorijaDTOGet> createDTO(Collection<Kategorija> kategorije) {
		Collection<KategorijaDTOGet> kategorijaDTOs = new ArrayList<>();
		for (Kategorija itKategorija: kategorije) {
			KategorijaDTOGet kategorijaDTO = createDTO(itKategorija);
			kategorijaDTOs.add(kategorijaDTO);
		}
		return kategorijaDTOs;
	}
	
	@Override
	public KategorijaDTOGet get(long id) {
		Kategorija kategorija = dostava.getKategorije().get(id);
		if (kategorija == null) {
			throw new NoSuchElementException("Kategorija nije pronađena!");
		}
		return createDTO(kategorija);
	}

	@Override
	public Collection<KategorijaDTOGet> getAll() {
		Collection<Kategorija> kategorije = dostava.getKategorije().values();
		return createDTO(kategorije);
	}

	@Override
	public Collection<KategorijaDTOGet> get(String naziv) {
		Collection<Kategorija> kategorije = dostava.getKategorije().values();

		List<Kategorija> rezultat = new ArrayList<>();
		for (Kategorija itKategorija: kategorije) {
			if (naziv == null || itKategorija.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
				rezultat.add(itKategorija);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid KategorijaDTOAddUpdate kategorijaDTO) {
		long id = dostava.nextKategorijaId();
		Kategorija kategorija = mapper.map(kategorijaDTO, Kategorija.class);
		kategorija.setId(id);
		dostava.getKategorije().put(id, kategorija);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid KategorijaDTOAddUpdate kategorijaDTO) {
		long id = kategorijaDTO.getId();
		Kategorija kategorija = dostava.getKategorije().get(id);
		mapper.map(kategorijaDTO, kategorija);
	}

	@Override
	public void delete(long id) {
		// kaskadno brisanje
		Iterator<Entry<Long, Restoran>> itEntryRestoran = dostava.getRestorani().entrySet().iterator();
		while (itEntryRestoran.hasNext()) {
			Restoran itRestoran = itEntryRestoran.next().getValue();
			for (Kategorija itKategorija: itRestoran.getKategorije()) {
				if (itKategorija.getId() == id) {
					Iterator<Entry<Long, Artikal>> itEntryArtikal = dostava.getArtikli().entrySet().iterator();
					while (itEntryArtikal.hasNext()) {
						Artikal itArtikal = itEntryArtikal.next().getValue();
						if (itArtikal.getRestoran().getId() == itRestoran.getId()) {
							itEntryArtikal.remove();
						}
					}
					itEntryRestoran.remove();
				}
			}
		}
		dostava.getKategorije().remove(id);
	}

}
