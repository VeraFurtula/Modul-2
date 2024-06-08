package com.ftninformatika.jwd.modul2.termin6.bioskop.dto;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.validation.Validation;

import jakarta.validation.constraints.NotEmpty;

public class FilmDTOAddUpdate extends FilmDTO {

	@NotEmpty(message = "Bar jedan žanr mora biti zadat.", groups = {Validation.Add.class, Validation.Update.class})
	private long[] zanrIds;

	public long[] getZanrIds() {
		return zanrIds;
	}

	public void setZanrIds(long[] zanrIds) {
		this.zanrIds = zanrIds;
	}

}
