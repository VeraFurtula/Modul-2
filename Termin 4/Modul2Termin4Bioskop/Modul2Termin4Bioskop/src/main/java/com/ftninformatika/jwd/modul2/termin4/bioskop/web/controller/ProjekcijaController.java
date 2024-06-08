package com.ftninformatika.jwd.modul2.termin4.bioskop.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin4.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin4.bioskop.web.Bioskop;

@Controller
@RequestMapping("/projekcije")
public class ProjekcijaController {

	private Bioskop bioskop;

	public ProjekcijaController(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "0") long filmId) { // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
		Collection<Projekcija> rezultat = new ArrayList<>();
		for (Projekcija itProjekcija: bioskop.getProjekcije().values()) { // pretraga projekcija po film id
			if (filmId == 0 || itProjekcija.getFilm().getId() == filmId) {
				rezultat.add(itProjekcija);
			}
		}
		request.addAttribute("projekcije", rezultat);
		return "projekcije";
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("projekcija", bioskop.getProjekcije().get(id));
		return "projekcije-prikaz";
	}

}
