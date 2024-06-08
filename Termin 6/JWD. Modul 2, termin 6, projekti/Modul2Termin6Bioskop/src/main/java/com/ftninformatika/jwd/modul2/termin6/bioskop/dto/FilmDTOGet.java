package com.ftninformatika.jwd.modul2.termin6.bioskop.dto;

public class FilmDTOGet extends FilmDTO {

	private ZanrDTOGet[] zanrovi;
	
	public ZanrDTOGet[] getZanrovi() {
		return zanrovi;
	}

	public void setZanrovi(ZanrDTOGet[] zanrovi) {
		this.zanrovi = zanrovi;
	}

}
