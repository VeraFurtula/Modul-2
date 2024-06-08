package com.ftninformatika.jwd.modul2.termin4.bioskop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftninformatika.jwd.modul2.termin4.bioskop.web.Bioskop;

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

}
