package com.ftninformatika.jwd.modul2.termin6.dostava.dto;


import com.informatika.jwd.modul2.termin6.dostava.dto.validation.Validation;

import jakarta.validation.constraints.NotEmpty;

public class RestoranAddUpdateDTO extends RestoranDTO{
	@NotEmpty(message = "Bar jedna kategorija mora biti zadata.", groups = {Validation.Add.class, Validation.Update.class})
	private long[] kategorijaIds;

	public long[] getKategorijaIds() {
		return kategorijaIds;
	}

	public void setKategorijaIds(long[] kategorijaIds) {
		this.kategorijaIds = kategorijaIds;
	}
}
