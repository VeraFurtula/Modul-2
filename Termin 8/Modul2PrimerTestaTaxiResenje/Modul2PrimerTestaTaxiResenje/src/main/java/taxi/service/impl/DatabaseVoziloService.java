package taxi.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import taxi.dto.VoziloDTOGet;
import taxi.model.Vozilo;
import taxi.repository.VoziloDAO;
import taxi.service.VoziloService;

@Service
public class DatabaseVoziloService implements VoziloService {

	private final VoziloDAO voziloDAO;
	private final ModelMapper mapper = new ModelMapper();

	public DatabaseVoziloService(VoziloDAO voziloDAO) {
		this.voziloDAO = voziloDAO;
	}

	private VoziloDTOGet createDTO(Vozilo poziv) {
		return mapper.map(poziv, VoziloDTOGet.class);
	}

	private Collection<VoziloDTOGet> createDTO(Collection<Vozilo> vozila) {
		Collection<VoziloDTOGet> voziloDTOs = new ArrayList<>();
		for (Vozilo itVozilo: vozila) {
			VoziloDTOGet voziloDTO = createDTO(itVozilo);
			voziloDTOs.add(voziloDTO);
		}
		return voziloDTOs;
	}
	
	@Override
	public Collection<VoziloDTOGet> getAll() {
		Collection<Vozilo> vozila = voziloDAO.getAll();
		return createDTO(vozila);
	}

}
