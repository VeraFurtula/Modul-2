package com.ftninformatika.modul2.restoran.web.controller;

//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.modul2.restoran.model.Porudzbina;
import com.ftninformatika.modul2.restoran.web.Dostava;


@Controller
@RequestMapping("/porudzbine")
public class PorudzbinaController {
	//private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	private Dostava dostava;

	public PorudzbinaController(Dostava dostava) {
		this.dostava = dostava;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "0") long restoranId,
			@RequestParam(required = false, defaultValue = "") String korisnickoIme) {
		Collection<Porudzbina> rezultat = new ArrayList<>();
		for (Porudzbina itPorudzbina: dostava.getPorudzbine().values()) {
			if ((korisnickoIme.equals("") || itPorudzbina.getKorisnik().getKorisnickoIme().equals(korisnickoIme)) && 
				(restoranId == 0 || itPorudzbina.getRestoran().getId() == restoranId)) {
				rezultat.add(itPorudzbina);
			}
		}
		request.addAttribute("porudzbine", rezultat);
		return "porudzbine";
	}



	@GetMapping("/prikaz")
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("porudzbina", dostava.getPorudzbine().get(id));
		return "porudzbine-prikaz";
	}
}