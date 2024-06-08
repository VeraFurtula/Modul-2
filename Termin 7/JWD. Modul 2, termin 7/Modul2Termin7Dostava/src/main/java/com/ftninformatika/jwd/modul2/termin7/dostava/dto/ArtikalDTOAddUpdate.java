package com.ftninformatika.jwd.modul2.termin7.dostava.dto;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.validation.Validation;

import jakarta.validation.constraints.Positive;

public class ArtikalDTOAddUpdate extends ArtikalDTO {

	@Positive(message = "Restoran mora biti zadat.", groups = {Validation.Add.class, Validation.Update.class})
	private long restoranId;

	public long getRestoranId() {
		return restoranId;
	}

	public void setRestoranId(long restoranId) {
		this.restoranId = restoranId;
	}

}
