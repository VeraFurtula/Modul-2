package com.ftninformatika.jwd.modul2.termin7.dostava.web.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.RestoranDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.KategorijaService;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.RestoranService;

@Controller
@RequestMapping("/restorani")
public class RestoranController {
  
	private final RestoranService restoranService;
	private final KategorijaService kategorijaService;

	public RestoranController(RestoranService restoranService, KategorijaService kategorijaService) {
		this.restoranService = restoranService;
		this.kategorijaService = kategorijaService;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv, 
			@RequestParam(required = false, defaultValue = "0") long kategorijaId,  // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumOsnivanjaOd, // ako parametar nije poslat, vrednost će biti prazan String; ako je vrednost prazan String, LocalDateTime će biti null; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
			@RequestParam(required = false, defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datumOsnivanjaDo) {
		request.addAttribute("restorani", restoranService.get(naziv, kategorijaId, datumOsnivanjaOd, datumOsnivanjaDo));
		request.addAttribute("kategorije", kategorijaService.getAll()); // potrebno da bi se popunio padajući meni za pretragu
		return "restorani"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("restoran", restoranService.get(id));
		request.addAttribute("kategorije", kategorijaService.getAll());
		return "restorani-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add(ModelMap request) {
		request.addAttribute("kategorije", kategorijaService.getAll());
		return "restorani-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute RestoranDTOAddUpdate restoranDTO) {
		restoranService.add(restoranDTO);
		return "redirect:/restorani";
	}

	
	@PostMapping("/izmena")
	public String update(@ModelAttribute RestoranDTOAddUpdate restoranDTO) {
		restoranService.update(restoranDTO);
		return "redirect:/restorani";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		restoranService.delete(id);
		return "redirect:/restorani";
	}

}
