package com.ftninformatika.jwd.modul2.termin4.bioskop.web.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin4.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin4.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin4.bioskop.web.Bioskop;

@Controller
@RequestMapping("/filmovi")
public class FilmController {

	private Bioskop bioskop;

	public FilmController(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "0") long zanrId) { // ako parametar nije poslat, vrednost će biti "0"; ako je parametar poslat, a vrednost ne može da se konvertuje u traženi tip, nastaće izuzetak
		Collection<Film> rezultat = new ArrayList<>();
		for (Film itFilm: bioskop.getFilmovi().values()) { // pretraga filmova po žanr id
			for (Zanr itZanr: itFilm.getZanrovi()) {
				if (zanrId == 0 || itZanr.getId() == zanrId) {
					rezultat.add(itFilm);
					break; // prekid samo unutrašnje petlje
				}
			}
		}
		request.addAttribute("filmovi", rezultat);
		return "filmovi"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("film", bioskop.getFilmovi().get(id));
		return "filmovi-prikaz"; // forwarding na template
	}

}
