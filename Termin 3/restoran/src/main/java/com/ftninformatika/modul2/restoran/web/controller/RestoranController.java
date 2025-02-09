package com.ftninformatika.modul2.restoran.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.ftninformatika.modul2.restoran.web.Dostava;

@Controller
@RequestMapping("/restorani")
public class RestoranController {
	private Dostava dostava;

	public RestoranController(Dostava dostava) {
		this.dostava = dostava;
	}

	@GetMapping("") 
	public String getAll(ModelMap request) {
		request.addAttribute("restorani", dostava.getRestorani().values());
		return "restorani"; // forwarding na template
	}

	@GetMapping("/prikaz") // bez @ResponseBody
	public String get(ModelMap request, 
			@RequestParam long id) {
		request.addAttribute("restoran", dostava.getRestorani().get(id));
		return "restorani-prikaz"; 
	}
}
