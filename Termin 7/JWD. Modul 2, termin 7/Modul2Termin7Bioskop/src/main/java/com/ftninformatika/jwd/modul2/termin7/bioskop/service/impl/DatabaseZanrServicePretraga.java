package com.ftninformatika.jwd.modul2.termin7.bioskop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ZanrDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ZanrDTOGet;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.validation.Validation;
import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin7.bioskop.repository.ZanrDAO;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.ZanrService;

import jakarta.validation.Valid;

//@Primary
@Service
@Validated
public class DatabaseZanrServicePretraga implements ZanrService {

	private final ZanrDAO zanrDAO;
	private final ModelMapper mapper = new ModelMapper();	

	public DatabaseZanrServicePretraga(ZanrDAO zanrDAO) {
		this.zanrDAO = zanrDAO;
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
		Zanr zanr = zanrDAO.get(id);
		if (zanr == null) {
			throw new NoSuchElementException("Žanr nije pronađen!");
		}
		return createDTO(zanr);
	}

	@Override
	public Collection<ZanrDTOGet> getAll() {
		Collection<Zanr> zanrovi = zanrDAO.getAll();
		return createDTO(zanrovi);
	}

	@Override
	public Collection<ZanrDTOGet> get(String naziv) {
		Collection<Zanr> zanrovi = zanrDAO.getAll();

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
		Zanr zanr = mapper.map(zanrDTO, Zanr.class);
		zanrDAO.add(zanr);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid ZanrDTOAddUpdate zanrDTO) {
		Zanr zanr = mapper.map(zanrDTO, Zanr.class);
		zanrDAO.update(zanr);
	}

	@Override
	public void delete(long id) {
		zanrDAO.delete(id);
	}

}
