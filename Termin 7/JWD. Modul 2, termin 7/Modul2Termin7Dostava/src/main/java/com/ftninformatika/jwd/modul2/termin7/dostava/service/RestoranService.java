package com.ftninformatika.jwd.modul2.termin7.dostava.service;

import java.time.LocalDate;
import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.RestoranDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.RestoranDTOGet;

import jakarta.validation.Valid;

public interface RestoranService {

	public RestoranDTOGet get(long id);
	public Collection<RestoranDTOGet> getAll();
	public Collection<RestoranDTOGet> get(String naziv, long kategorijaId, LocalDate datumOsnivanjaOd, LocalDate datumOsnivanjaDo);
	public void add(@Valid RestoranDTOAddUpdate restoranDTO);
	public void update(@Valid RestoranDTOAddUpdate restoranDTO);
	public void delete(long id);
	
}
