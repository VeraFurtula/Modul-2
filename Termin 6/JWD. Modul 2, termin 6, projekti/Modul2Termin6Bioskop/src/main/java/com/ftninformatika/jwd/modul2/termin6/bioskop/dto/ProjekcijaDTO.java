package com.ftninformatika.jwd.modul2.termin6.bioskop.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

abstract class ProjekcijaDTO {

	@Positive(message = "ID mora biti validan.", groups = {Validation.Update.class})
	private long id;

	@NotNull(message = "Datum i vreme moraju biti zadati.", groups = {Validation.Add.class, Validation.Update.class})
	@FutureOrPresent(message = "Datum i vreme ne smeju biti u prošlosti.", groups = {Validation.Add.class, Validation.Update.class})
	private LocalDateTime datumIVreme;

	@NotBlank(message = "Tip ne sme biti prazan.", groups = {Validation.Add.class, Validation.Update.class})
	@Pattern(regexp = "2D|3D|4D", message = "Tip mora biti 2D, 3D ili 4D.", groups = {Validation.Add.class, Validation.Update.class})
	private String tip;
	
	@Min(value = 1, message = "Sala ne sme biti manja od 1.", groups = {Validation.Add.class, Validation.Update.class})
	@Max(value = 3, message = "Sala ne sme biti veća od 3.", groups = {Validation.Add.class, Validation.Update.class})
	private int sala;

	@Positive(message = "Cena mora biti pozitivna.", groups = {Validation.Add.class, Validation.Update.class})
	private double cenaKarte;

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

	public int getSala() {
		return sala;
	}

	public void setSala(int sala) {
		this.sala = sala;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getCenaKarte() {
		return cenaKarte;
	}

	public void setCenaKarte(double cenaKarte) {
		this.cenaKarte = cenaKarte;
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
		ProjekcijaDTO other = (ProjekcijaDTO) obj;
		return id == other.id;
	}

}
