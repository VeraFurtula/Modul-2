package com.ftninformatika.jwd.modul2.termin6.dostava.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin6.dostava.dto.KorisnikDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.dostava.service.KorisnikService;

@Controller
@RequestMapping("/korisnici")
public class KorisnikController {

	private final KorisnikService korisnikService;

	public KorisnikController(KorisnikService korisnikService) {
		this.korisnikService = korisnikService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String korisnickoIme, 
			@RequestParam(required = false, defaultValue = "") String eMail, 
			@RequestParam(required = false, defaultValue = "") String pol, 
			@RequestParam(required = false, defaultValue = "false") boolean administrator) { // ne-check-irani checkbox ne šalje parametar
		request.addAttribute("korisnici", korisnikService.get(korisnickoIme, eMail, pol, administrator));
		return "korisnici"; // forwarding na template
	}

	@GetMapping("/prikaz")
	public String get(ModelMap request,
			@RequestParam String korisnickoIme) {
		request.addAttribute("korisnik", korisnikService.get(korisnickoIme));
		return "korisnici-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje")
	public String add() {
		return "korisnici-dodavanje"; // forwarding na template
	}
	
	@PostMapping("/dodavanje")
	public String add(@ModelAttribute KorisnikDTOAddUpdate korisnikDTO) {
		korisnikService.add(korisnikDTO);
		return "redirect:/korisnici";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute KorisnikDTOAddUpdate korisnikDTO) {
		korisnikService.update(korisnikDTO);
		return "redirect:/korisnici";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam String korisnickoIme) {
		korisnikService.delete(korisnickoIme);
		return "redirect:/korisnici";
	}
}
