package taxi.model;

import java.util.Objects;

public class Vozilo {

	private long id;
	private String broj;
	private String vozac;

	public Vozilo() {}
	
	public Vozilo(long id, String broj, String vozac) {
		this.id = id;
		this.broj = broj;
		this.vozac = vozac;
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
		Vozilo other = (Vozilo) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Vozilo [id=" + id + ", broj=" + broj + ", vozac=" + vozac + "]";
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

	public String getVozac() {
		return vozac;
	}

	public void setVozac(String vozac) {
		this.vozac = vozac;
	}

}
