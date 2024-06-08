package com.ftninformatika.jwd.modul2.termin5.bioskop.web.controller;

import java.util.Iterator;
import java.util.Map.Entry;

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
@RequestMapping("/zanrovi")
public class ZanrController {

	private Bioskop bioskop;

	public ZanrController(Bioskop bioskop) {
		this.bioskop = bioskop;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request) {
		request.addAttribute("zanrovi", bioskop.getZanrovi().values());
		return "zanrovi"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("zanr", bioskop.getZanrovi().get(id));
		return "zanrovi-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add() {
		return "zanrovi-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@RequestParam String naziv) {
		long id = bioskop.nextZanrId();
		Zanr zanr = new Zanr(id, naziv);
		bioskop.getZanrovi().put(id, zanr);

		return "redirect:/zanrovi";
	}

	@PostMapping("/izmena")
	public String update(@RequestParam long id, 
			@RequestParam String naziv) {
		Zanr zanr = bioskop.getZanrovi().get(id);
		zanr.setNaziv(naziv);

		return "redirect:/zanrovi";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		// kaskadno brisanje
		Iterator<Entry<Long, Film>> itEntryFilm = bioskop.getFilmovi().entrySet().iterator();
		while (itEntryFilm.hasNext()) {
			Film itFilm = itEntryFilm.next().getValue();
			for (Zanr itZanr: itFilm.getZanrovi()) {
				if (itZanr.getId() == id) {
					Iterator<Entry<Long, Projekcija>> itEntryProjekcija = bioskop.getProjekcije().entrySet().iterator();
					while (itEntryProjekcija.hasNext()) {
						Projekcija itProjekcija = itEntryProjekcija.next().getValue();
						if (itProjekcija.getFilm().getId() == itFilm.getId()) {
							itEntryProjekcija.remove();
						}
					}
					itEntryFilm.remove();
				}
			}

		}
		bioskop.getZanrovi().remove(id);

		return "redirect:/zanrovi";
	}

}
