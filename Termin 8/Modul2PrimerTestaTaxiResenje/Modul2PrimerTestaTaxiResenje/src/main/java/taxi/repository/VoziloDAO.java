package taxi.repository;

import java.util.Collection;

import taxi.model.Vozilo;

public interface VoziloDAO {

	public Vozilo get(long id);
	public Collection<Vozilo> getAll();

}
