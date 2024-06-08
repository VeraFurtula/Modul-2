package com.ftninformatika.modul2.restoran.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.modul2.restoran.web.Dostava;

@Controller
@RequestMapping("/artikli")
public class ArtikalController {
	private Dostava dostava;
	
	public ArtikalController(Dostava dostava) {
		this.dostava = dostava;
	}

	@GetMapping("") 
	public String getAll(ModelMap request) {
		request.addAttribute("artikli", dostava.getArtikli().values());
		return "artikli";
	}

	@GetMapping("/prikaz") 
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("artikal", dostava.getArtikli().get(id));
		return "artikli-prikaz";
	}
}

