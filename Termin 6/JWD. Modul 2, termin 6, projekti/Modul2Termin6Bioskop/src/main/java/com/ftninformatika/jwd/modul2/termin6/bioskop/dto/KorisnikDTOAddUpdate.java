package com.ftninformatika.jwd.modul2.termin6.bioskop.dto;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class KorisnikDTOAddUpdate extends KorisnikDTO {

	@NotBlank(message = "Lozinka ne sme biti prazna.", groups = {Validation.Add.class})
	@NotNull(message = "Lozinka nije poslata.", groups = {Validation.Update.class})
	private String lozinka;

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

}
