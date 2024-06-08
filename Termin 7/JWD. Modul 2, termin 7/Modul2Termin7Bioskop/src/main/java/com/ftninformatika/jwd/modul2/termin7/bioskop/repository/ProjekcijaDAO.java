package com.ftninformatika.jwd.modul2.termin7.bioskop.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Projekcija;

public interface ProjekcijaDAO {

	public Projekcija get(long id);
	public Collection<Projekcija> getAll();
	public Collection<Projekcija> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			long filmId, 
			String tip, 
			int sala, 
			double cenaKarteOd, double cenaKarteDo);
	public void add(Projekcija projekcija);
	public void update(Projekcija projekcija);
	public void delete(long id);

}
