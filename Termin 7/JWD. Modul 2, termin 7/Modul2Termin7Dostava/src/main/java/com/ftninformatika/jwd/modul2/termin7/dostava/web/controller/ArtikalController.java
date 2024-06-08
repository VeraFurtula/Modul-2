package com.ftninformatika.jwd.modul2.termin7.dostava.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.ArtikalDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.ArtikalService;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.RestoranService;

@Controller
@RequestMapping("/artikli")
public class ArtikalController {

	private final ArtikalService artikalService;
	private final RestoranService restoranService;

	public ArtikalController(ArtikalService artikalService, RestoranService restoranService) {
		this.artikalService = artikalService;
		this.restoranService = restoranService;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv, 
			@RequestParam(required = false, defaultValue = "") String opis, 
			@RequestParam(required = false, defaultValue = "0") double cenaOd, // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "0") double cenaDo,
			@RequestParam(required = false, defaultValue = "0") long restoranId) {
		request.addAttribute("artikli", artikalService.get(naziv, opis, cenaOd, cenaDo, restoranId));
		request.addAttribute("restorani", restoranService.getAll()); // potrebno da bi se popunio padajući meni za pretragu
		return "artikli"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("artikal", artikalService.get(id));
		request.addAttribute("restorani", restoranService.getAll());
		return "artikli-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add(ModelMap request) {
		request.addAttribute("restorani", restoranService.getAll());
		return "artikli-dodavanje"; // forwarding na template
	}
	@PostMapping("/dodavanje")
	public String add(@ModelAttribute ArtikalDTOAddUpdate artikalDTO) {
		artikalService.add(artikalDTO);
		return "redirect:/artikli";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute ArtikalDTOAddUpdate artikalDTO) {
		artikalService.update(artikalDTO);
		return "redirect:/artikli";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		artikalService.delete(id);
		return "redirect:/artikli";
	}

}
