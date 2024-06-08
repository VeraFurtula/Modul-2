package com.ftninformatika.jwd.modul2.termin7.bioskop.dto;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.validation.Validation;

import jakarta.validation.constraints.Positive;

public class ProjekcijaDTOAddUpdate extends ProjekcijaDTO {

	@Positive(message = "Film mora biti zadat.", groups = {Validation.Add.class, Validation.Update.class})
	private long filmId;

	public long getFilmId() {
		return filmId;
	}

	public void setFilmId(long filmId) {
		this.filmId = filmId;
	}

}
