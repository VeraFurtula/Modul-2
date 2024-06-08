package com.ftninformatika.jwd.modul2.termin7.bioskop.service;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ZanrDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.ZanrDTOGet;

import jakarta.validation.Valid;

public interface ZanrService {

	public ZanrDTOGet get(long id);
	public Collection<ZanrDTOGet> getAll();
	public Collection<ZanrDTOGet> get(String naziv);
	public void add(@Valid ZanrDTOAddUpdate zanrDTO);
	public void update(@Valid ZanrDTOAddUpdate zanrDTO);
	public void delete(long id);

}
