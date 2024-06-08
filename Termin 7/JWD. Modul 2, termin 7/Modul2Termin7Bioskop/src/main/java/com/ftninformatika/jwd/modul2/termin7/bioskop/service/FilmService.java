package com.ftninformatika.jwd.modul2.termin7.bioskop.service;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.FilmDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.FilmDTOGet;

import jakarta.validation.Valid;

public interface FilmService {

	public FilmDTOGet get(long id);
	public Collection<FilmDTOGet> getAll();
	public Collection<FilmDTOGet> get(String naziv, long zanrId, int trajanjeOd, int trajanjeDo);
	public void add(@Valid FilmDTOAddUpdate filmDTO);
	public void update(@Valid FilmDTOAddUpdate filmDTO);
	public void delete(long id);

}
