package com.ftninformatika.jwd.modul2.termin6.dostava.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.ArtikalDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.ArtikalService;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.RestoranService;

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
			@RequestParam(required = false, defaultValue = "") String naziv) {
		request.addAttribute("kategorije", artikalService.get(naziv));
		return "artikli"; 
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("artikal", artikalService.get(id));
		request.addAttribute("restorani", restoranService.getAll());
		return "artikli-prikaz";
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add(ModelMap request) {
		request.addAttribute("restorani", restoranService.getAll());
		return "artikli-dodavanje";
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
