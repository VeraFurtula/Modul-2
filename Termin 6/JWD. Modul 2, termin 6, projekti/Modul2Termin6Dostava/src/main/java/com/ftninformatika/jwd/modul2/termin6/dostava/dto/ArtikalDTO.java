package com.ftninformatika.jwd.modul2.termin6.dostava.dto;


import java.util.Objects;

import com.informatika.jwd.modul2.termin6.dostava.dto.validation.Validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public abstract class ArtikalDTO {
	@Positive(message = "ID mora biti validan.", groups = {Validation.Update.class})
	private long id;

	@NotBlank(message = "Naziv ne sme biti prazan.", groups = {Validation.Add.class, Validation.Update.class})
	private String naziv;

	@NotBlank(message = "Opis ne sme biti prazan.", groups = {Validation.Add.class, Validation.Update.class})
	private String opis;

	@Positive(message = "Cena mora biti pozitivna.", groups = {Validation.Add.class, Validation.Update.class})
	private double cena;

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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
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
		ArtikalDTO other = (ArtikalDTO) obj;
		return id == other.id;
	}
	
	
	
}
