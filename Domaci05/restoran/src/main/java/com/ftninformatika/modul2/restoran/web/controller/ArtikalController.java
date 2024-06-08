package com.ftninformatika.modul2.restoran.web.controller;

import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.modul2.restoran.model.Artikal;
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

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("artikal", dostava.getArtikli().get(id));
		return "artikli-prikaz"; // forwarding na template
	}
	
	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add() {
		return "artikli-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@RequestParam String naziv, @RequestParam String opis, @RequestParam double cena) {
		long id = dostava.nextArtikalId();
		Artikal artikal = new Artikal(id, naziv, opis, cena);
		dostava.getArtikli().put(id, artikal);

		return "redirect:/artikli";
	}

	@PostMapping("/izmena")
	public String update(@RequestParam long id, 
			@RequestParam String naziv,
			@RequestParam String opis, 
			@RequestParam double cena) {
		Artikal artikal = dostava.getArtikli().get(id);
		artikal.setNaziv(naziv);
		artikal.setOpis(opis);
		artikal.setCena(cena);

		return "redirect:/artikli";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		dostava.getArtikli().remove(id);

		return "redirect:/artikli";
	}
}
