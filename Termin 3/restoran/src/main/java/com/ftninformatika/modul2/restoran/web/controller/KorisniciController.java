package com.ftninformatika.modul2.restoran.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.modul2.restoran.web.Dostava;


@Controller
@RequestMapping("/korisnici")
public class KorisniciController {
	private Dostava dostava;
	
	public KorisniciController(Dostava dostava) {
		this.dostava = dostava;
	}

	@GetMapping("") 
	public String getAll(ModelMap request) {
		request.addAttribute("korisnici", dostava.getKorisnici().values());
		return "korisnici";
	}

	@GetMapping("/prikaz") 
	public String get(ModelMap request,
			@RequestParam String korisnickoIme) {
		request.addAttribute("korisnik", dostava.getKorisnici().get(korisnickoIme));
		return "korisnici-prikaz"; 
	}
}

