package com.ftninformatika.jwd.modul2.termin6.bioskop.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Korisnik;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin6.bioskop.model.Zanr;

@Component
public class Bioskop {

	private final Map<Long, Zanr> zanrovi = new LinkedHashMap<>();
	private final Map<Long, Film> filmovi = new LinkedHashMap<>();
	private final Map<Long, Projekcija> projekcije = new LinkedHashMap<>();
	private final Map<String, Korisnik> korisnici = new LinkedHashMap<>();

	private long maxZanrId = 0;
	private long maxFilmId = 0;
	private long maxProjekcijaId = 0;
	
	public Bioskop() {
		// kreiranje žanrova
		zanrovi.put(1L, new Zanr(1L, "naučna fantastika"));
		zanrovi.put(2L, new Zanr(2L, "akcija"));
		zanrovi.put(3L, new Zanr(3L, "komedija"));
		zanrovi.put(4L, new Zanr(4L, "horor"));
		zanrovi.put(5L, new Zanr(5L, "avantura"));

		// kreiranje filmova
		filmovi.put(1L, new Film(1L, "Avengers: Endgame", 182));
		filmovi.put(2L, new Film(2L, "Life", 110));
		filmovi.put(3L, new Film(3L, "It: Chapter 2", 170));
		filmovi.put(4L, new Film(4L, "Pirates of the Caribbean: Dead Men Tell No Tales", 153));

		// povezivanje filmova i žanrova
		filmovi.get(1L).addZanr(zanrovi.get(1L));
		filmovi.get(2L).addZanr(zanrovi.get(1L));
		filmovi.get(1L).addZanr(zanrovi.get(2L));
		filmovi.get(4L).addZanr(zanrovi.get(2L));
		filmovi.get(4L).addZanr(zanrovi.get(3L));
		filmovi.get(2L).addZanr(zanrovi.get(4L));
		filmovi.get(3L).addZanr(zanrovi.get(4L));
		filmovi.get(1L).addZanr(zanrovi.get(5L));
		filmovi.get(4L).addZanr(zanrovi.get(5L));

		// kreiranje i projekcija i povezivanje projekcija i filmova
		projekcije.put(1L, new Projekcija(1L, LocalDateTime.parse("2020-06-22T20:00:00"), filmovi.get(1L), "2D", 1, 380.00));
		projekcije.put(2L, new Projekcija(2L, LocalDateTime.parse("2020-06-22T23:30:00"), filmovi.get(3L), "2D", 1, 380.00));
		projekcije.put(3L, new Projekcija(3L, LocalDateTime.parse("2020-06-22T20:00:00"), filmovi.get(1L), "3D", 2, 420.00));
		projekcije.put(4L, new Projekcija(4L, LocalDateTime.parse("2020-06-22T23:30:00"), filmovi.get(2L), "3D", 2, 420.00));
		projekcije.put(5L, new Projekcija(5L, LocalDateTime.parse("2020-06-22T20:00:00"), filmovi.get(3L), "4D", 3, 580.00));
		projekcije.put(6L, new Projekcija(6L, LocalDateTime.parse("2020-06-23T20:00:00"), filmovi.get(2L), "2D", 1, 380.00));
		projekcije.put(7L, new Projekcija(7L, LocalDateTime.parse("2020-06-23T22:00:00"), filmovi.get(4L), "2D", 1, 380.00));
		projekcije.put(8L, new Projekcija(8L, LocalDateTime.parse("2020-06-23T20:00:00"), filmovi.get(2L), "3D", 2, 420.00));
		projekcije.put(9L, new Projekcija(9L, LocalDateTime.parse("2020-06-23T22:00:00"), filmovi.get(4L), "3D", 2, 420.00));
		projekcije.put(10L, new Projekcija(10L, LocalDateTime.parse("2020-06-23T20:00:00"), filmovi.get(1L), "4D", 3, 580.00));

		maxZanrId = 5L;
		maxFilmId = 4L;
		maxProjekcijaId = 10L;
		
		// kreiranje korisnika
		korisnici.put("a", new Korisnik("a", "a", "a@a.com", "muški", true));
		korisnici.put("b", new Korisnik("b", "b", "b@b.com", "ženski", false));
		korisnici.put("c", new Korisnik("c", "c", "c@c.com", "muški", false));
	}

	public Map<Long, Zanr> getZanrovi() {
		return zanrovi;
	}
	
	public Map<Long, Film> getFilmovi() {
		return filmovi;
	}

	public Map<Long, Projekcija> getProjekcije() {
		return projekcije;
	}

	public Map<String, Korisnik> getKorisnici() {
		return korisnici;
	}
	
	public long nextZanrId() {
		maxZanrId++;
		return maxZanrId;
	}

	public long nextFilmId() {
		maxFilmId++;
		return maxFilmId;
	}

	public long nextProjekcijaId() {
		maxProjekcijaId++;
		return maxProjekcijaId;
	}

}
