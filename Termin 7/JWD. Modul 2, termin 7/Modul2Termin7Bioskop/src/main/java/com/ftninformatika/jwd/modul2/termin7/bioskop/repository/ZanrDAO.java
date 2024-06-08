package com.ftninformatika.jwd.modul2.termin7.bioskop.repository;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Zanr;

public interface ZanrDAO {

	public Zanr get(long id);
	public Collection<Zanr> getAll();
	public Collection<Zanr> get(String naziv);
	public void add(Zanr zanr);
	public void update(Zanr zanr);
	public void delete(long id);

}
