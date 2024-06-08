package com.ftninformatika.jwd.modul2.termin7.bioskop.repository;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Film;

public interface FilmDAO {

	public Film get(long id);
	public Collection<Film> getAll();
	public Collection<Film> get(String naziv, long zanrId, int trajanjeOd, int trajanjeDo);
	public void add(Film film);
	public void update(Film film);
	public void delete(long id);

}
