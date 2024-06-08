package voz.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import voz.dto.VozDTOGet;
import voz.model.Voz;
import voz.repository.VozDAO;
import voz.service.VozService;

@Service
public class DatabaseVozService implements VozService {

	private final VozDAO vozDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseVozService(VozDAO vozDAO) {
		this.vozDAO = vozDAO;
	}

	private VozDTOGet createDTO(Voz poziv) {
		return mapper.map(poziv, VozDTOGet.class);
	}

	private Collection<VozDTOGet> createDTO(Collection<Voz> vozovi) {
		Collection<VozDTOGet> vozDTOs = new ArrayList<>();
		for (Voz itVoz: vozovi) {
			VozDTOGet vozDTO = createDTO(itVoz);
			vozDTOs.add(vozDTO);
		}
		return vozDTOs;
	}
	
	@Override
	public Collection<VozDTOGet> getAll() {
		Collection<Voz> vozovi = vozDAO.getAll();
		return createDTO(vozovi);
	}

}
