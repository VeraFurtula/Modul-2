package com.ftninformatika.jwd.modul2.termin6.bioskop.web.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ProjekcijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.ProjekcijaService;

@Controller
@RequestMapping("/projekcije")
public class ProjekcijaController {

	private final ProjekcijaService projekcijaService;
	private final FilmService filmService;

	public ProjekcijaController(ProjekcijaService projekcijaService, FilmService filmService) {
		this.projekcijaService = projekcijaService;
		this.filmService = filmService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeOd, // ako parametar nije poslat, vrednost će biti prazan String; ako je vrednost prazan String, LocalDateTime će biti null; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeDo, 
			@RequestParam(required = false, defaultValue = "0") long filmId, // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "") String tip, 
			@RequestParam(required = false, defaultValue = "0") int sala, 
			@RequestParam(required = false, defaultValue = "0") double cenaKarteOd, 
			@RequestParam(required = false, defaultValue = "0") double cenaKarteDo) {
		request.addAttribute("projekcije", projekcijaService.get(
				datumIVremeOd, datumIVremeDo, 
				filmId, 
				tip, 
				sala, 
				cenaKarteOd, cenaKarteDo));
		request.addAttribute("filmovi", filmService.getAll()); // potrebno da bi se popunio padajući meni za pretragu
		return "projekcije";
	}

	@GetMapping("/prikaz")
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("projekcija", projekcijaService.get(id));
		request.addAttribute("filmovi", filmService.getAll());
		return "projekcije-prikaz";
	}

	@GetMapping("/dodavanje")
	public String add(ModelMap request) {
		request.addAttribute("filmovi", filmService.getAll());
		return "projekcije-dodavanje";
	}
	
	@PostMapping("/dodavanje")
	public String add(@ModelAttribute ProjekcijaDTOAddUpdate projekcijaDTO) {
		projekcijaService.add(projekcijaDTO);
		return "redirect:/projekcije";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute ProjekcijaDTOAddUpdate projekcijaDTO) {	
		projekcijaService.update(projekcijaDTO);
		return "redirect:/projekcije";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		projekcijaService.delete(id);
		return "redirect:/projekcije";
	}

}
