package com.ftninformatika.jwd.modul2.termin7.bioskop.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Projekcija {

	private long id;
	private LocalDateTime datumIVreme;
	private String tip;
	private int sala;
	private double cenaKarte;

	private Film film;

	public Projekcija(long id, LocalDateTime datumIVreme, Film film, String tip, int sala, double cenaKarte) {
		this.id = id;
		this.datumIVreme = datumIVreme;
		this.film = film;
		this.tip = tip;
		this.sala = sala;
		this.cenaKarte = cenaKarte;
	}

	public Projekcija(long id, LocalDateTime datumIVreme, String tip, int sala, double cenaKarte) {
		this.id = id;
		this.datumIVreme = datumIVreme;
		this.tip = tip;
		this.sala = sala;
		this.cenaKarte = cenaKarte;
	}

	public Projekcija() {}

	@Override
	public String toString() {
		return "Projekcija [id=" + id + ", datumIVreme=" + datumIVreme + ", tip=" + tip + ", sala=" + sala
				+ ", cenaKarte=" + cenaKarte + ", film=" + film + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projekcija other = (Projekcija) obj;
		return id == other.id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDatumIVreme() {
		return datumIVreme;
	}

	public void setDatumIVreme(LocalDateTime datumIVreme) {
		this.datumIVreme = datumIVreme;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public double getCenaKarte() {
		return cenaKarte;
	}

	public void setCenaKarte(double cenaKarte) {
		this.cenaKarte = cenaKarte;
	}

}
