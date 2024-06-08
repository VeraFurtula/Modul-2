package voz.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import voz.dto.validation.Validation;

abstract class KartaDTO {

	@Positive(message = "ID mora biti validan.", groups = {Validation.Update.class})
	private long id;

	@NotNull(message = "Datum i vreme moraju biti zadati.", groups = {Validation.Add.class})
	@PastOrPresent(message = "Karta ne sme biti prodata u budućnosti.", groups = {Validation.Add.class})
	private LocalDateTime datumIVremeProdaje;

	@NotBlank(message = "Kupac ne sme biti prazan.", groups = {Validation.Add.class})
	private String kupac;

	@Min(value = 1, message = "Razred ne sme biti manji od 1.", groups = {Validation.Add.class, Validation.Update.class})
	@Max(value = 2, message = "Razred ne sme biti veći od 2.", groups = {Validation.Add.class, Validation.Update.class})
	private int razred;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getDatumIVremeProdaje() {
		return datumIVremeProdaje;
	}

	public void setDatumIVremeProdaje(LocalDateTime datumIVremeProdaje) {
		this.datumIVremeProdaje = datumIVremeProdaje;
	}

	public String getKupac() {
		return kupac;
	}

	public void setKupac(String kupac) {
		this.kupac = kupac;
	}

	public int getRazred() {
		return razred;
	}

	public void setRazred(int razred) {
		this.razred = razred;
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
		KartaDTO other = (KartaDTO) obj;
		return id == other.id;
	}

}
