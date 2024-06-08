package com.ftninformatika.modul2.restoran.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.modul2.restoran.web.Dostava;


@Controller
@RequestMapping("/kategorije")
public class KategorijaController {
	private Dostava dostava;
	
	public KategorijaController(Dostava dostava) {
		this.dostava = dostava;
	}

	@GetMapping("") 
	public String getAll(ModelMap request) {
		request.addAttribute("kategorije", dostava.getKategorije().values());
		return "kategorije";
	}

	@GetMapping("/prikaz") 
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("kategorija", dostava.getKategorije().get(id));
		return "kategorije-prikaz";
	}
}

