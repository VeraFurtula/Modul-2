package voz.service;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.validation.Valid;
import voz.dto.KartaDTOAddUpdate;
import voz.dto.KartaDTOGet;

public interface KartaService {

	public Collection<KartaDTOGet> get(
			String vozBroj, 
			String vozRang, 
			LocalDateTime vozDatumIVremePolaskaOd, LocalDateTime vozDatumIVremePolaskaDo, 
			double vozCenaKarteOd, double vozCenaKarteDo, 
			String kupac, 
			int razredOd, int razredDo);
	public void add(@Valid KartaDTOAddUpdate kartaDTO);
	public void update(@Valid KartaDTOAddUpdate kartaDTO);

}
