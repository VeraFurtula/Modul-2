package com.ftninformatika.jwd.modul2.termin7.bioskop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin7.bioskop.dto.FilmDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.FilmService;
import com.ftninformatika.jwd.modul2.termin7.bioskop.service.ZanrService;

@Controller
@RequestMapping("/filmovi")
public class FilmController {

	private final FilmService filmService;
	private final ZanrService zanrService;

	public FilmController(FilmService filmService, ZanrService zanrService) {
		this.filmService = filmService;
		this.zanrService = zanrService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv, 
			@RequestParam(required = false, defaultValue = "0") long zanrId, // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "0") int trajanjeOd, 
			@RequestParam(required = false, defaultValue = "0") int trajanjeDo) {
		request.addAttribute("filmovi", filmService.get(naziv, zanrId, trajanjeOd, trajanjeDo));
		request.addAttribute("zanrovi", zanrService.getAll()); // potrebno da bi se popunio padajući meni za pretragu
		return "filmovi"; // forwarding na template
	}

	@GetMapping("/prikaz")
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("film", filmService.get(id));
		request.addAttribute("zanrovi", zanrService.getAll());
		return "filmovi-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje")
	public String add(ModelMap request) {
		request.addAttribute("zanrovi", zanrService.getAll());
		return "filmovi-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute FilmDTOAddUpdate filmDTO) {
		filmService.add(filmDTO);
		return "redirect:/filmovi";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute FilmDTOAddUpdate filmDTO) {	
		filmService.update(filmDTO);
		return "redirect:/filmovi";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		filmService.delete(id);
		return "redirect:/filmovi";
	}

}
