package voz.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Voz {

	private long id;
	private String broj;
	private String rang;
	private LocalDateTime datumIVremePolaska;
	private double cenaKarte;
	private int brojMesta;

	public Voz() {}

	public Voz(long id, String broj, String rang, LocalDateTime datumIVremePolaska, double cenaKarte, int brojMesta) {
		super();
		this.id = id;
		this.broj = broj;
		this.rang = rang;
		this.datumIVremePolaska = datumIVremePolaska;
		this.cenaKarte = cenaKarte;
		this.brojMesta = brojMesta;
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
		Voz other = (Voz) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Voz [id=" + id + ", broj=" + broj + ", rang=" + rang + ", datumIVremePolaska=" + datumIVremePolaska
				+ ", cenaKarte=" + cenaKarte + ", brojMesta=" + brojMesta + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBroj() {
		return broj;
	}

	public void setBroj(String broj) {
		this.broj = broj;
	}

	public String getRang() {
		return rang;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public LocalDateTime getDatumIVremePolaska() {
		return datumIVremePolaska;
	}

	public void setDatumIVremePolaska(LocalDateTime datumIVremePolaska) {
		this.datumIVremePolaska = datumIVremePolaska;
	}

	public double getCenaKarte() {
		return cenaKarte;
	}

	public void setCenaKarte(double cenaKarte) {
		this.cenaKarte = cenaKarte;
	}

	public int getBrojMesta() {
		return brojMesta;
	}

	public void setBrojMesta(int brojMesta) {
		this.brojMesta = brojMesta;
	}

}
