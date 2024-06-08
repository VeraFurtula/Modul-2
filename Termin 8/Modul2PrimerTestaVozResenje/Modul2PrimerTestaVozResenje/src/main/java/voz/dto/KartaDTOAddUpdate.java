package voz.dto;

import jakarta.validation.constraints.Positive;
import voz.dto.validation.Validation;

public class KartaDTOAddUpdate extends KartaDTO {

	@Positive(message = "Voz mora biti odabran.", groups = {Validation.Add.class})
	private long vozId;

	public long getVozId() {
		return vozId;
	}

	public void setVozId(long vozId) {
		this.vozId = vozId;
	}

}
