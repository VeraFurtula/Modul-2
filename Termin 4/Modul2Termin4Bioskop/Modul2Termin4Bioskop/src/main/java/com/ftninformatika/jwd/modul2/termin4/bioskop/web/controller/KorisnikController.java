package com.ftninformatika.jwd.modul2.termin4.bioskop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin4.bioskop.web.Bioskop;

@Controller
@RequestMapping("/korisnici")
public class KorisnikController {

	private Bioskop bioskop;

	public KorisnikController(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request) {
		request.addAttribute("korisnici", bioskop.getKorisnici().values());
		return "korisnici"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request,
			@RequestParam String korisnickoIme) {
		request.addAttribute("korisnik", bioskop.getKorisnici().get(korisnickoIme));
		return "korisnici-prikaz"; // forwarding na template
	}

}
