package taxi.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import taxi.dto.validation.Validation;

abstract class PozivDTO {

	@Positive(message = "ID mora biti validan.", groups = {Validation.Update.class})
	private long id;	

	@NotNull(message = "Datum i vreme moraju biti zadati.", groups = {Validation.Add.class, Validation.Update.class})
	@PastOrPresent(message = "Poziv ne sme biti u budućnosti.", groups = {Validation.Add.class, Validation.Update.class})
	private LocalDateTime datumIVreme;

	@NotBlank(message = "Ulica ne sme biti prazna.", groups = {Validation.Add.class, Validation.Update.class})
	private String ulica;

	@Positive(message = "Broj mora biti pozitivan.", groups = {Validation.Add.class, Validation.Update.class})
	private int broj;

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

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
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
		PozivDTO other = (PozivDTO) obj;
		return id == other.id;
	}

}
