package com.ftninformatika.jwd.modul2.termin7.dostava.dto;

import java.util.Objects;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.validation.Validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

abstract class KategorijaDTO {

	@Positive(message = "ID mora biti validan.", groups = {Validation.Update.class})
	private long id;

	@NotBlank(message = "Naziv ne sme biti prazan.", groups = {Validation.Add.class, Validation.Update.class})
	private String naziv;

	public KategorijaDTO() {}

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
		KategorijaDTO other = (KategorijaDTO) obj;
		return id == other.id;
	}

}
