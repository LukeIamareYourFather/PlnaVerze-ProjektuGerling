package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.news.models.dto.mappers.NewsMapper;
import com.danger.insurance.news.models.service.NewsServiceImplementation;

@PreAuthorize("hasAnyRole('JOURNALIST', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class UpdateNewsController {
	
	@Autowired
	private NewsServiceImplementation newsService;
	
	@Autowired 
	private NewsMapper newsMapper;
	
	public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/news-pictures";
		
	@GetMapping("{newsId}/edit")
	public String renderUpdatePartyForm(@PathVariable("newsId") long newsId, Model model) {
		model.addAttribute("formName", FormNames.NEWS_UPDATE.getDisplayName());
		model.addAttribute("formAction", "edit/confirmed");
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("newsCreateDTO", newsMapper.mergeToNewsCreateDTO(new NewsCreateDTO(), newsService.getById(newsId)));
		model.addAttribute("isEditForm", true);
		
		return "pages/news/create";
	}
	
	@PostMapping("{newsId}/edit/confirmed")
	public String handeUpdatePartyFormSubmit(@PathVariable("newsId") long newsId, @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO) {
		NewsDTO originalArticleDTO = newsService.getById(newsId);
		NewsDTO editedArticleDTO = newsMapper.mergeToNewsDTO(new NewsDTO(), newsCreateDTO);
		editedArticleDTO.setNewsId(newsId);
		editedArticleDTO.setPostDate(originalArticleDTO.getPostDate());
		editedArticleDTO.setPictureUrl(originalArticleDTO.getPictureUrl());
		newsService.edit(editedArticleDTO);
		
		return "redirect:/news/" + newsId;
	}
	
}