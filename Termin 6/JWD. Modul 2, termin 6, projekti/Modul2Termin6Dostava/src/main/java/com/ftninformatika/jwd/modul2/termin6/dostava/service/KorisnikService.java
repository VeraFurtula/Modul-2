package com.ftninformatika.jwd.modul2.termin6.dostava.service;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.KorisnikDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.dostava.dto.KorisnikDTOGet;

import jakarta.validation.Valid;

public interface KorisnikService {
	public KorisnikDTOGet get(String korisnickoIme);
	public Collection<KorisnikDTOGet> getAll();
	public Collection<KorisnikDTOGet> get(String korisnickoIme, String eMail, String pol, boolean administrator);
	public void add(@Valid KorisnikDTOAddUpdate korisnikDTO);
	public void update(@Valid KorisnikDTOAddUpdate korisnikDTO);
	public void delete(String korisnickoIme);
}
