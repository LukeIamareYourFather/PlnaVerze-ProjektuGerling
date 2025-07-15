package com.danger.insurance.infopages.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("help")
public class HelpPageController {

	@GetMapping
	public String renderIndex() {
		return "pages/help/index";
	}
	
}