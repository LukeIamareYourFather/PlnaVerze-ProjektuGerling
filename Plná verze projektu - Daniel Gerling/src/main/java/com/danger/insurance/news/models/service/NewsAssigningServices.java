package com.danger.insurance.news.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.mappers.NewsMapper;

@Service
public class NewsAssigningServices {
	
	@Autowired
	private NewsServiceImplementation newsService;
	
	@Autowired
	private NewsMapper newsMapper;

	public String addCreateNewsFormAttributes(Model model) {
		String formAction = "create/process";
		boolean isEditForm = false;
		assignFormVisualAttributes(FormNames.NEWS_CREATE, formAction, ButtonLabels.CREATE, isEditForm, model);
		
		if (!model.containsAttribute("newsCreateDTO")) {
			model.addAttribute("newsCreateDTO", new NewsCreateDTO());
		}
		
		return "pages/news/create";
	}
	
	public String addNewsListAttributes(Model model) {
		model.addAttribute("newsList", newsService.getAll().reversed());
		
		return "pages/news/index";
	}
	
	public String addNewsArticleAttributes(long newsId, Model model) {
		model.addAttribute("article", newsService.getById(newsId));
		
		return "pages/news/detail";
	}
	
	public String addUpdateNewsFormAttributes(long newsId, Model model) {
		String formAction = "edit/validate";
		boolean isEditForm = true;
		assignFormVisualAttributes(FormNames.NEWS_UPDATE, formAction, ButtonLabels.CHANGE, isEditForm, model);
		
		if (!model.containsAttribute("newsCreateDTO")) {
			model.addAttribute("newsCreateDTO", newsMapper.mergeToNewsCreateDTO(new NewsCreateDTO(), newsService.getById(newsId)));
		}
		
		return "pages/news/create";
	}
	
	private void assignFormVisualAttributes(FormNames formName, String formAction, ButtonLabels buttonLabel, boolean isEditForm, Model model) {
		model.addAttribute("formName", formName.getDisplayName());
		model.addAttribute("formAction", formAction);
		model.addAttribute("buttonLabel", buttonLabel.getDisplayName());
		model.addAttribute("isEditForm", isEditForm);
	}
	
}