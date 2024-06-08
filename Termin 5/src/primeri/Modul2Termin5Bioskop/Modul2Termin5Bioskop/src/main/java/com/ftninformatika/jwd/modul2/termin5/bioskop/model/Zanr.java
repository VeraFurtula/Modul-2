package com.ftninformatika.jwd.modul2.termin5.bioskop.model;

import java.util.Objects;

public class Zanr {

	private long id;
	private String naziv;

	public Zanr(long id, String naziv) {
		this.id = id;
		this.naziv = naziv;
	}

	public Zanr() {}

	@Override
	public String toString() {
		return "Zanr [id=" + id + ", naziv=" + naziv + "]";
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
		Zanr other = (Zanr) obj;
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

}
