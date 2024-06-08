package com.ftninformatika.jwd.modul2.termin6.bioskop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin6.bioskop.dto.ZanrDTOAddUpdate;
import com.ftninformatika.jwd.modul2.termin6.bioskop.service.ZanrService;

@Controller
@RequestMapping("/zanrovi")
public class ZanrController {

	private final ZanrService zanrService;

	public ZanrController(ZanrService zanrService) {
		this.zanrService = zanrService;
	}

	@GetMapping("")
	public String getAll(ModelMap request, 
			@RequestParam(required = false, defaultValue = "") String naziv) {
		request.addAttribute("zanrovi", zanrService.get(naziv));
		return "zanrovi"; // forwarding na template
	}

	@GetMapping("/prikaz")
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("zanr", zanrService.get(id));
		return "zanrovi-prikaz"; // forwarding na template
	}

	@GetMapping("/dodavanje")
	public String add() {
		return "zanrovi-dodavanje"; // forwarding na template
	}

	@PostMapping("/dodavanje")
	public String add(@ModelAttribute ZanrDTOAddUpdate zanrDTO) {
		zanrService.add(zanrDTO);
		return "redirect:/zanrovi";
	}

	@PostMapping("/izmena")
	public String update(@ModelAttribute ZanrDTOAddUpdate zanrDTO) {
		zanrService.update(zanrDTO);
		return "redirect:/zanrovi";
	}

	@PostMapping("/brisanje")
	public String delete(@RequestParam long id) {
		zanrService.delete(id);
		return "redirect:/zanrovi";
	}

}
