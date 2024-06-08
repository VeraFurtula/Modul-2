package taxi.web.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import taxi.dto.PozivDTOAddUpdate;
import taxi.service.PozivService;
import taxi.service.VoziloService;

@Controller
@RequestMapping("/pozivi")
public class PoziviController {

	private final PozivService pozivService;
	private final VoziloService voziloService;

	public PoziviController(PozivService pozivService, VoziloService voziloService) {
		this.pozivService = pozivService;
		this.voziloService = voziloService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeOd, 
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeDo, 
			@RequestParam(required = false, defaultValue = "") String ulica, 
			@RequestParam(required = false, defaultValue = "0") int brojOd,
			@RequestParam(required = false, defaultValue = "0") int brojDo,
			@RequestParam(required = false, defaultValue = "0") long voziloId) {
		request.addAttribute("pozivi", pozivService.get(datumIVremeOd, datumIVremeDo, ulica, brojOd, brojDo, voziloId));
		request.addAttribute("vozila", voziloService.getAll());
		return "pozivi";
	}

	@GetMapping("/dodavanje")
	public String add(ModelMap request) {
		request.addAttribute("vozila", voziloService.getAll());
		return "pozivi-dodavanje";
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute PozivDTOAddUpdate pozivDTO) {
		pozivService.add(pozivDTO);
		return "redirect:/pozivi";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		pozivService.delete(id);
		return "redirect:/pozivi";
	}

}
