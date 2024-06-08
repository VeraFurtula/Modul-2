package taxi.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import taxi.model.Poziv;

public interface PozivDAO {

	public Collection<Poziv> getAll();
	public Collection<Poziv> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			String ulica, 
			int brojOd, int brojDo, 
			long voziloId);
	public void add(Poziv poziv);
	public void delete(long id);

}
