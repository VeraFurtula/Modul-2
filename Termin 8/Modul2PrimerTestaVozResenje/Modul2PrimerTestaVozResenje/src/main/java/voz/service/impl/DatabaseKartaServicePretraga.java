package voz.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import voz.dto.KartaDTOAddUpdate;
import voz.dto.KartaDTOGet;
import voz.dto.validation.Validation;
import voz.model.Karta;
import voz.model.Voz;
import voz.repository.KartaDAO;
import voz.repository.VozDAO;
import voz.service.KartaService;

//@Primary
@Service
@Validated
public class DatabaseKartaServicePretraga implements KartaService {

	private final KartaDAO kartaDAO;
	private final VozDAO vozDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseKartaServicePretraga(KartaDAO kartaDAO, VozDAO vozDAO) {
		this.kartaDAO = kartaDAO;
		this.vozDAO = vozDAO;
	}

	private KartaDTOGet createDTO(Karta karta) {
		return mapper.map(karta, KartaDTOGet.class);
	}

	private Collection<KartaDTOGet> createDTO(Collection<Karta> karte) {
		Collection<KartaDTOGet> kartaDTOs = new ArrayList<>();
		for (Karta itKarte: karte) {
			KartaDTOGet kartaDTO = createDTO(itKarte);
			kartaDTOs.add(kartaDTO);
		}
		return kartaDTOs;
	}

	@Override
	public Collection<KartaDTOGet> get(
			String vozBroj, 
			String vozRang, 
			LocalDateTime vozDatumIVremePolaskaOd, LocalDateTime vozDatumIVremePolaskaDo, 
			double vozCenaKarteOd, double vozCenaKarteDo, 
			String kupac, 
			int razredOd, int razredDo) {
		Collection<Karta> karte = kartaDAO.getAll();
		
		List<Karta> rezultat = new ArrayList<>();
		for (Karta itKarta: karte) {
			if ((vozBroj == null || itKarta.getVoz().getBroj().toLowerCase().contains(vozBroj.toLowerCase())) && 
					(vozRang == null || vozRang.equals("") || itKarta.getVoz().getRang().equals(vozRang)) &&
					(vozDatumIVremePolaskaOd == null || itKarta.getVoz().getDatumIVremePolaska().compareTo(vozDatumIVremePolaskaOd) >= 0) &&
					(vozDatumIVremePolaskaDo == null || itKarta.getVoz().getDatumIVremePolaska().compareTo(vozDatumIVremePolaskaDo) <= 0) &&
					(vozCenaKarteOd <= 0 || itKarta.getVoz().getCenaKarte() >= vozCenaKarteOd) && 
					(vozCenaKarteDo <= 0 || itKarta.getVoz().getCenaKarte() <= vozCenaKarteDo) &&
					(kupac == null || itKarta.getKupac().toLowerCase().contains(kupac.toLowerCase())) && 
					(razredOd <= 0 || itKarta.getRazred() >= razredOd) && 
					(razredDo <= 0 || itKarta.getRazred() <= razredDo)) {
				rezultat.add(itKarta);
			}
		}
		return createDTO(rezultat);
	}

	@Override
	@Validated(Validation.Add.class)
	public void add(@Valid KartaDTOAddUpdate kartaDTO) {
		Voz voz = vozDAO.get(kartaDTO.getVozId());
		if (voz == null) {
			throw new NoSuchElementException("Voz nije pronađen!");
		}
		Karta karta = mapper.map(kartaDTO, Karta.class);
		karta.setVoz(voz);

		kartaDAO.add(karta);
	}

	@Override
	@Validated(Validation.Update.class)
	public void update(@Valid KartaDTOAddUpdate kartaDTO) {
		long id = kartaDTO.getId();
		Karta karta = kartaDAO.get(id);
		karta.setRazred(kartaDTO.getRazred());

		kartaDAO.update(karta);
	}

}
