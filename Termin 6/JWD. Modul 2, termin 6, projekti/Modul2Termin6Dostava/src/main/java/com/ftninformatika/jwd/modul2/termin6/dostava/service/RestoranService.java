package com.ftninformatika.jwd.modul2.termin6.dostava.service;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.RestoranAddUpdateDTO;
import com.ftninformatika.jwd.modul2.termin6.dostava.dto.RestoranDTOGet;

import jakarta.validation.Valid;

public interface RestoranService {
	public RestoranDTOGet get(long id);
	public Collection<RestoranDTOGet> getAll();
	public Collection<RestoranDTOGet> get(String naziv);
	public void add(@Valid RestoranAddUpdateDTO restoranDTO);
	public void update(@Valid RestoranAddUpdateDTO restoranDTO);
	public void delete(long id);

}
