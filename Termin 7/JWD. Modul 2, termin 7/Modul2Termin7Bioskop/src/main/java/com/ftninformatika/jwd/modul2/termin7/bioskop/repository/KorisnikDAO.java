package com.ftninformatika.jwd.modul2.termin7.bioskop.repository;

import java.util.Collection;

import com.ftninformatika.jwd.modul2.termin7.bioskop.model.Korisnik;

public interface KorisnikDAO {

	public Korisnik get(String korisnickoIme);
	public Korisnik get(String korisnickoIme, String lozinka);
	public Collection<Korisnik> getAll();
	public Collection<Korisnik> get(String korisnickoIme, String eMail, String pol, boolean administrator);
	public void add(Korisnik korisnik);
	public void update(Korisnik korisnik);
	public void delete(String korisnickoIme);

}
