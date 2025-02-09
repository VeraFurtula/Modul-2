package com.ftninformatika.jwd.modul2.termin7.dostava.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin7.dostava.dto.KategorijaDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin7.dostava.service.KategorijaService;

@Controller
@RequestMapping("/kategorije")
public class KategorijaController {

	private KategorijaService kategorijaService;

	public KategorijaController(KategorijaService kategorijaService) {
		this.kategorijaService = kategorijaService;
	}

	@GetMapping("") // bez @ResponseBody
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv) {
		request.addAttribute("kategorije", kategorijaService.get(naziv));
		return "kategorije"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("kategorija", kategorijaService.get(id));
		return "kategorije-prikaz"; // forwarding na template
	}
	@GetMapping("/dodavanje") // bez @ResponseBody
	public String add() {
		return "kategorije-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute KategorijaDTOAddUpdate kategorijaDTO) {
		kategorijaService.add(kategorijaDTO);
		return "redirect:/kategorije";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute KategorijaDTOAddUpdate kategorijaDTO) {
		kategorijaService.update(kategorijaDTO);
		return "redirect:/kategorije";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		kategorijaService.delete(id);
		return "redirect:/kategorije";
	}

}
