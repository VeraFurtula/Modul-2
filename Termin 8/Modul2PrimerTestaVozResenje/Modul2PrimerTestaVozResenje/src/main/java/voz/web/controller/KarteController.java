package voz.web.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import voz.dto.KartaDTOAddUpdate;
import voz.service.KartaService;
import voz.service.VozService;

@Controller
@RequestMapping("/karte")
public class KarteController {

	private final KartaService kartaService;
	private final VozService vozService;

	public KarteController(KartaService kartaService, VozService vozService) {
		this.kartaService = kartaService;
		this.vozService = vozService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String vozBroj, 
			@RequestParam(required = false, defaultValue = "") String vozRang, 
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vozDatumIVremePolaskaOd, 
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vozDatumIVremePolaskaDo, 
			@RequestParam(required = false, defaultValue = "0") double vozCenaKarteOd,
			@RequestParam(required = false, defaultValue = "0") double vozCenaKarteDo,
			@RequestParam(required = false, defaultValue = "") String kupac, 
			@RequestParam(required = false, defaultValue = "0") int razredOd,
			@RequestParam(required = false, defaultValue = "0") int razredDo) {
		request.addAttribute("karte", kartaService.get(
				vozBroj, 
				vozRang, 
				vozDatumIVremePolaskaOd, vozDatumIVremePolaskaDo, 
				vozCenaKarteOd, vozCenaKarteDo, 
				kupac, 
				razredOd, razredDo));
		request.addAttribute("vozovi", vozService.getAll());
		return "karte";
	}

	@GetMapping("/dodavanje")
	public String add(ModelMap request) {
		request.addAttribute("vozovi", vozService.getAll());
		return "karte-dodavanje";
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute KartaDTOAddUpdate kartaDTO) {
		kartaService.add(kartaDTO);
		return "redirect:/karte";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute KartaDTOAddUpdate kartaDTO) {
		kartaService.update(kartaDTO);
		return "redirect:/karte";
	}

}
