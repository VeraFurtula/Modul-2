package taxi.dto;

import jakarta.validation.constraints.Positive;
import taxi.dto.validation.Validation;

public class PozivDTOAddUpdate extends PozivDTO {

	@Positive(message = "Vozilo mora biti odabrano.", groups = {Validation.Add.class, Validation.Update.class})
	private long voziloId;

	public long getVoziloId() {
		return voziloId;
	}

	public void setVoziloId(long voziloId) {
		this.voziloId = voziloId;
	}

}
