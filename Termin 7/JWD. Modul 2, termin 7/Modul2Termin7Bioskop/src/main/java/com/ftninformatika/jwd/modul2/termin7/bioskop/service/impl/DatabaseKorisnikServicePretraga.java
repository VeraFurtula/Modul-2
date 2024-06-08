package com.ftninformatika.jwd.modul2.termin7.bioskop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.KorisnikDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.KorisnikDTOGet;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Korisnik;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.KorisnikDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.KorisnikService;

import jakarta.validation.Valid;

//@Primary
@Service
@Validated
public class DatabaseKorisnikServicePretraga implements KorisnikService {

	private final KorisnikDAO korisnikDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseKorisnikServicePretraga(KorisnikDAO korisnikDAO) {
		this.korisnikDAO = korisnikDAO;
	}

	private KorisnikDTOGet createDTO(Korisnik korisnik) {
		return mapper.map(korisnik, KorisnikDTOGet.class);
	}

	private Collection<KorisnikDTOGet> createDTO(Collection<Korisnik> korisnici) {
		Collection<KorisnikDTOGet> korisnikDTOs = new ArrayList<>();
		for (Korisnik itKorisnik: korisnici) {
			KorisnikDTOGet korisnikDTO = createDTO(itKorisnik);
			korisnikDTOs.add(korisnikDTO);
		}
		return korisnikDTOs;
	}

	@Override
	public KorisnikDTOGet get(String korisnickoIme) {
		Korisnik korisnik = korisnikDAO.get(korisnickoIme);
		if (korisnik == null) {
			throw new NoSuchElementException("Korisnik nije pronađen!");
		}
		return createDTO(korisnik);
	}

	@Override
	public Collection<KorisnikDTOGet> getAll() {
		Collection<Korisnik> korisnici = korisnikDAO.getAll();
		return createDTO(korisnici);
	}

	@Override
	public Collection<KorisnikDTOGet> get(String korisnickoIme, String eMail, String pol, boolean administrator) {
		Collection<Korisnik> korisnici = korisnikDAO.getAll();
		
		List<Korisnik> rezultat = new ArrayList<>();
		for (Korisnik itKorisnik: korisnici) {
			if ((korisnickoIme == null || itKorisnik.getKorisnickoIme().toLowerCase().contains(korisnickoIme.toLowerCase())) &&
					(eMail == null || itKorisnik.geteMail().toLowerCase().contains(eMail.toLowerCase())) &&
					(pol == null || pol.equals("") || itKorisnik.getPol().equals(pol)) &&
					(!administrator || itKorisnik.isAdministrator())) {
				rezultat.add(itKorisnik);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid KorisnikDTOAddUpdate korisnikDTO) {
		String korisnickoIme = korisnikDTO.getKorisnickoIme();
		if (korisnikDAO.get(korisnickoIme) != null) {
			throw new IllegalArgumentException("Korisničko ime već postoji!");
		}
		Korisnik korisnik = mapper.map(korisnikDTO, Korisnik.class);

		korisnikDAO.add(korisnik);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid KorisnikDTOAddUpdate korisnikDTO) {
		String korisnickoIme = korisnikDTO.getKorisnickoIme();

		Korisnik korisnik = korisnikDAO.get(korisnickoIme);
		if (!korisnikDTO.getLozinka().equals("")) { // lozinka se menja samo ako je unesena; zbog ovog uslova se ne koristi mapper jer bi on bezuslovno prekopirao lozinku
			korisnik.setLozinka(korisnikDTO.getLozinka());
		}
		korisnik.seteMail(korisnikDTO.geteMail());
		korisnik.setPol(korisnikDTO.getPol());
		korisnik.setAdministrator(korisnikDTO.isAdministrator());

		korisnikDAO.update(korisnik);
	}

	@Override
	public void delete(String korisnickoIme) {
		korisnikDAO.delete(korisnickoIme);
	}

}
