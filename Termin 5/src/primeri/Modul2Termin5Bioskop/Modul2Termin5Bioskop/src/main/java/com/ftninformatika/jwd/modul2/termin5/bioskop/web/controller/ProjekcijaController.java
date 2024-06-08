package com.ftninformatika.jwd.modul2.termin5.bioskop.web.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin5.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin5.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin5.bioskop.web.Bioskop;

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
		request.addAttribute("filmovi", bioskop.getFilmovi().values());
		return "projekcije-prikaz";
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add(ModelMap request) {
		request.addAttribute("filmovi", bioskop.getFilmovi().values());
		return "projekcije-dodavanje";
	}
	
	@PostMapping("/dodavanje")
	public String add(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVreme, 
			@RequestParam long filmId, 
			@RequestParam String tip, 
			@RequestParam int sala, 
			@RequestParam double cenaKarte) {
		Film film = bioskop.getFilmovi().get(filmId); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			return "redirect:/projekcije"; // spreči dodavanje
		}

		long id = bioskop.nextProjekcijaId();
		Projekcija projekcija = new Projekcija(id, datumIVreme, film, tip, sala, cenaKarte); // povezivanje
		bioskop.getProjekcije().put(id, projekcija);

		return "redirect:/projekcije";
	}

	@PostMapping("/izmena")
	public String update(@RequestParam long id, 
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVreme, 
			@RequestParam long filmId, 
			@RequestParam String tip, 
			@RequestParam int sala, 
			@RequestParam double cenaKarte) {	
		Film film = bioskop.getFilmovi().get(filmId); // pronalaženje filma po id
		if (film == null) { // da li je film pronađen?
			return "redirect:/projekcije"; // spreči izmenu
		}

		Projekcija projekcija = bioskop.getProjekcije().get(id);
		projekcija.setDatumIVreme(datumIVreme);
		projekcija.setFilm(film); // povezivanje
		projekcija.setTip(tip);
		projekcija.setSala(sala);
		projekcija.setCenaKarte(cenaKarte);

		return "redirect:/projekcije";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		bioskop.getProjekcije().remove(id);

		return "redirect:/projekcije";
	}

}
