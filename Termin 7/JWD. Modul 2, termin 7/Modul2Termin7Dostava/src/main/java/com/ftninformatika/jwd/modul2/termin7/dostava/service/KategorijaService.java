package com.ftninformatika.jwd.modul2.termin7.dostava.service;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.KategorijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.dto.KategorijaDTOGet;

import jakarta.validation.Valid;

public interface KategorijaService {

	public KategorijaDTOGet get(long id);
	public Collection<KategorijaDTOGet> getAll();
	public Collection<KategorijaDTOGet> get(String naziv);
	public void add(@Valid KategorijaDTOAddUpdate kategorijaDTO);
	public void update(@Valid KategorijaDTOAddUpdate kategorijaDTO);
	public void delete(long id);
	
}
