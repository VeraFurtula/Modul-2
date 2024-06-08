package com.ftninformatika.jwd.modul2.termin5.bioskop.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Film {

	private long id;
	private String naziv;
	private int trajanje;

	private final Set<Zanr> zanrovi = new LinkedHashSet<>();
	
	public Film(long id, String naziv, int trajanje) {
		this.id = id;
		this.naziv = naziv;
		this.trajanje = trajanje;
	}

	public Film() {}

	public Zanr getZanr(long zanrId) {
		Zanr zanr = null;
		for (Zanr itZanr: zanrovi) {
			if (itZanr.getId() == zanrId) {
				zanr = itZanr;
				break;
			}
		}
		return zanr;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", naziv=" + naziv + ", trajanje=" + trajanje + ", zanrovi=" + zanrovi + "]";
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
		Film other = (Film) obj;
		return id == other.id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(int trajanje) {
		this.trajanje = trajanje;
	}

	public Set<Zanr> getZanrovi() {
		return Collections.unmodifiableSet(zanrovi);
	}

	public void setZanrovi(Set<Zanr> zanrovi) {
		this.zanrovi.clear();
		this.zanrovi.addAll(zanrovi);
	}

	public void addZanr(Zanr zanr) {
		zanrovi.add(zanr);
	}

}
