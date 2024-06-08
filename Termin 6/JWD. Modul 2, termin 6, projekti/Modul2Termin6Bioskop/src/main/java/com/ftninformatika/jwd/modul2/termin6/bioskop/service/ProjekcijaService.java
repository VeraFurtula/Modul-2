package com.ftninformatika.jwd.modul2.termin6.bioskop.service;

import java.time.LocalDateTime;
import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ProjekcijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ProjekcijaDTOGet;

import jakarta.validation.Valid;

public interface ProjekcijaService {

	public ProjekcijaDTOGet get(long id);
	public Collection<ProjekcijaDTOGet> getAll();
	public Collection<ProjekcijaDTOGet> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			long filmId, 
			String tip, 
			int sala, 
			double cenaKarteOd, double cenaKarteDo);
	public void add(@Valid ProjekcijaDTOAddUpdate projekcijaDTO);
	public void update(@Valid ProjekcijaDTOAddUpdate projekcijaDTO);
	public void delete(long id);

}
