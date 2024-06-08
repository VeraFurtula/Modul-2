package voz.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Karta {

	private long id;
	private LocalDateTime datumIVremeProdaje;
	private String kupac;
	private int razred;
	
	private Voz voz;

	public Karta() {}

	public Karta(long id, LocalDateTime datumIVremeProdaje, String kupac, int razred, Voz voz) {
		this.id = id;
		this.datumIVremeProdaje = datumIVremeProdaje;
		this.kupac = kupac;
		this.razred = razred;
		this.voz = voz;
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
		Karta other = (Karta) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Karta [id=" + id + ", datumIVremeProdaje=" + datumIVremeProdaje + ", kupac="
				+ kupac + ", razred=" + razred + ", voz=" + voz + "]";
	}

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

	public Voz getVoz() {
		return voz;
	}

	public void setVoz(Voz voz) {
		this.voz = voz;
	}

}
