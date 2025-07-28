package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.ButtonLabels;
import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.infopages.data.enums.FormNames;
import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.news.models.dto.mappers.NewsMapper;
import com.danger.insurance.news.models.service.NewsServiceImplementation;
import com.danger.insurance.validation.groups.OnUpdateNews;

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
	public String renderUpdateNewsForm(@PathVariable("newsId") long newsId, Model model) {
		model.addAttribute("formName", FormNames.NEWS_UPDATE.getDisplayName());
		model.addAttribute("formAction", "edit/validate");
		model.addAttribute("buttonLabel", ButtonLabels.CHANGE.getDisplayName());
		model.addAttribute("isEditForm", true);
		
		if (!model.containsAttribute("newsCreateDTO")) {
			model.addAttribute("newsCreateDTO", newsMapper.mergeToNewsCreateDTO(new NewsCreateDTO(), newsService.getById(newsId)));
		}
		
		return "pages/news/create";
	}
	
	@PostMapping("{newsId}/edit/validate")
	public String validateUpdateNewsFormPost(@PathVariable("newsId") long newsId, @Validated(OnUpdateNews.class) @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", FlashMessages.INVALID_INPUT.getDisplayName());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newsCreateDTO", bindingResult);
			redirectAttributes.addFlashAttribute("newsCreateDTO", newsCreateDTO);
			
			return "redirect:/news/" + newsId + "/edit";
		}
		
		redirectAttributes.addFlashAttribute("newsCreateDTO", newsCreateDTO);
		
		return "redirect:/news/" + newsId + "/edit/confirmed";
	}
	
	@GetMapping("{newsId}/edit/confirmed")
	public String handleUpdateNewsFormSubmit(@PathVariable("newsId") long newsId, @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, RedirectAttributes redirectAttributes) {
		NewsDTO originalArticleDTO = newsService.getById(newsId);
		NewsDTO editedArticleDTO = newsMapper.mergeToNewsDTO(new NewsDTO(), newsCreateDTO);
		editedArticleDTO.setNewsId(newsId);
		editedArticleDTO.setPostDate(originalArticleDTO.getPostDate());
		editedArticleDTO.setPictureUrl(originalArticleDTO.getPictureUrl());
		newsService.edit(editedArticleDTO);
		redirectAttributes.addFlashAttribute("success", FlashMessages.NEWS_UPDATED.getDisplayName());
		return "redirect:/news/" + newsId;
	}
	
}