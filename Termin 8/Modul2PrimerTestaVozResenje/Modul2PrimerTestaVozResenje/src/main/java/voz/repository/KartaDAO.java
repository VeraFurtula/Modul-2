package voz.repository;

import java.time.LocalDateTime;
import java.util.Collection;

import voz.model.Karta;

public interface KartaDAO {

	public Karta get(long id);
	public Collection<Karta> getAll();
	public Collection<Karta> get(
			String vozBroj, 
			String vozRang, 
			LocalDateTime vozDatumIVremePolaskaOd, LocalDateTime vozDatumIVremePolaskaDo, 
			double vozCenaKarteOd, double vozCenaKarteDo, 
			String kupac, 
			int razredOd, int razredDo);
	public void add(Karta karta);
	public void update(Karta karta);

}
