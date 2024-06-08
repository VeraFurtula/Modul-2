package com.ftninformatika.jwd.modul2.termin6.dostava.dto;


public class RestoranDTOGet extends RestoranDTO{
	
	private KategorijaDTOGet[] kategorije;
	
	public KategorijaDTOGet[] getKategorije() {
		return kategorije;
	}

	public void setKategorije(KategorijaDTOGet[] kategorije) {
		this.kategorije = kategorije;
	}
}
