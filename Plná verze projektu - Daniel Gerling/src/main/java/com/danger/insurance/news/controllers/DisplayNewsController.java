package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.news.models.service.NewsServiceImplementation;


@Controller
@RequestMapping("news")
public class DisplayNewsController {

	@Autowired
	private NewsServiceImplementation newsService;
	
	@GetMapping
	public String renderIndex(Model model) {
		model.addAttribute("newsList", newsService.getAll().reversed());
		
		return "pages/news/index";
	}
	
	@GetMapping("{newsId}")
	public String renderNewsArticle(@PathVariable("newsId") long newsId, Model model) {
		model.addAttribute("article", newsService.getById(newsId));
		
		return "pages/news/detail";
	}
	
}