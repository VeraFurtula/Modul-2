package com.ftninformatika.jwd.modul2.termin6.dostava.web.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.RestoranAddUpdateDTO;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Artikal;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Kategorija;
import com.ftninformatika.jwd.modul2.termin6.dostava.model.Restoran;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.Dostava;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.KategorijaService;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.RestoranService;

@Controller
@RequestMapping("/restorani")
public class RestoranController {
	
	private final RestoranService restoranService;
	private final KategorijaService kategorijaService;

	public RestoranController(RestoranService restoranService, KategorijaService kategorijaService) {
		this.restoranService = restoranService;
		this.kategorijaService = kategorijaService;
	}
  

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv) {
		request.addAttribute("restorani", restoranService.get(naziv));
		request.addAttribute("kategorije", kategorijaService.getAll()); // potrebno da bi se popunio padajući meni za pretragu
		return "restorani"; 
	}

	@GetMapping("/prikaz")
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("restoran", restoranService.get(id));
		request.addAttribute("kategorije", kategorijaService.getAll());
		return "restorani-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje")
	public String add(ModelMap request) {
		request.addAttribute("kategorije", kategorijaService.getAll());
		return "restorani-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute RestoranAddUpdateDTO restoranDTO) {
		restoranService.add(restoranDTO);
		return "redirect:/restorani";
	}

	
	@PostMapping("/izmena")
	public String update(@ModelAttribute RestoranAddUpdateDTO restoranDTO) {	
		restoranService.update(restoranDTO);
		return "redirect:/restorani";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		restoranService.delete(id);
		return "redirect:/restorani";
	}

}
