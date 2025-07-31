package com.danger.insurance.news.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.news.models.dto.NewsCreateDTO;

@Service
public class NewsVerifyingServices {

	@Autowired
	private NewsSupportServices supportServices;
	
	public String verifyUpdateNewsFormPost(NewsCreateDTO newsCreateDTO, long newsId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//
		if (bindingResult.hasErrors()) {
			supportServices.assignFailRedirectAttributes(newsCreateDTO, FlashMessages.INVALID_INPUT, bindingResult, redirectAttributes);
			
			return "redirect:/news/" + newsId + "/edit";
		}
		
		redirectAttributes.addFlashAttribute("newsCreateDTO", newsCreateDTO);
		
		return "redirect:/news/" + newsId + "/edit/confirmed";
	}
	
}