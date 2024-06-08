package com.ftninformatika.jwd.modul2.termin5.bioskop.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin5.bioskop.model.Film;
import com.ftninformatika.jwd.modul2.termin5.bioskop.model.Projekcija;
import com.ftninformatika.jwd.modul2.termin5.bioskop.model.Zanr;
import com.ftninformatika.jwd.modul2.termin5.bioskop.web.Bioskop;

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
		request.addAttribute("zanrovi", bioskop.getZanrovi().values());
		return "filmovi-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add(ModelMap request) {
		request.addAttribute("zanrovi", bioskop.getZanrovi().values());
		return "filmovi-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(
			@RequestParam String naziv, 
			@RequestParam int trajanje, 
			@RequestParam long[] zanrIds) {
		Set<Zanr> zanrovi = new LinkedHashSet<>();
		for (long itZanrId: zanrIds) { // pretraga žanrova po id
			Zanr itZanr = bioskop.getZanrovi().get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			return "redirect:/filmovi"; // spreči dodavanje
		}
		
		long id = bioskop.nextFilmId();
		Film film = new Film(id, naziv, trajanje);
		film.setZanrovi(zanrovi); // povezivanje
		bioskop.getFilmovi().put(id, film);
		
		return "redirect:/filmovi";
	}

	@PostMapping("/izmena")
	public String update(@RequestParam long id, 
			@RequestParam String naziv, 
			@RequestParam int trajanje, 
			@RequestParam long[] zanrIds) {
		Set<Zanr> zanrovi = new LinkedHashSet<>();
		for (long itZanrId: zanrIds) { // pretraga žanrova po id
			Zanr itZanr = bioskop.getZanrovi().get(itZanrId);
			zanrovi.add(itZanr);
		}
		if (zanrovi.isEmpty()) { // da li je bar jedan žanr pronađen?
			return "redirect:/filmovi"; // spreči izmenu
		}

		Film film = bioskop.getFilmovi().get(id);
		film.setNaziv(naziv);
		film.setTrajanje(trajanje);
		film.setZanrovi(zanrovi); // povezivanje

		return "redirect:/filmovi";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		 // kaskadno brisanje
		Iterator<Entry<Long, Projekcija>> itEntryProjekcija = bioskop.getProjekcije().entrySet().iterator();
		while (itEntryProjekcija.hasNext()) {
			Projekcija itProjekcija = itEntryProjekcija.next().getValue();
			if (itProjekcija.getFilm().getId() == id) {
				itEntryProjekcija.remove();
			}
		}
		bioskop.getFilmovi().remove(id);

		return "redirect:/filmovi";
	}

}
