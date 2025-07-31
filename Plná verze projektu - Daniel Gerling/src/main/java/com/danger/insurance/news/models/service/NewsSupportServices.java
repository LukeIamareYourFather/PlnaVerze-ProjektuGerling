package com.danger.insurance.news.models.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.news.models.dto.NewsCreateDTO;

@Service
public class NewsSupportServices {

	public void assignFailRedirectAttributes(NewsCreateDTO newsCreateDTO, FlashMessages errorMessage, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("error", errorMessage.getDisplayName());
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newsCreateDTO", bindingResult);
		redirectAttributes.addFlashAttribute("newsCreateDTO", newsCreateDTO);
	}
	
}