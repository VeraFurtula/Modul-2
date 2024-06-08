package taxi.service;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.validation.Valid;
import taxi.dto.PozivDTOAddUpdate;
import taxi.dto.PozivDTOGet;

public interface PozivService {

	public Collection<PozivDTOGet> get(
			LocalDateTime datumIVremeOd, LocalDateTime datumIVremeDo, 
			String ulica, 
			int brojOd, int brojDo, 
			long voziloId);
	public void add(@Valid PozivDTOAddUpdate pozivDTO);
	public void delete(long id);

}
