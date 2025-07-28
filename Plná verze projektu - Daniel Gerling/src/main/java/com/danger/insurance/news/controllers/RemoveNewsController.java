package com.danger.insurance.news.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.news.models.service.NewsService;

@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class RemoveNewsController {
	
	@Autowired
	private NewsService newsService;

	public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/news-pictures";

	@GetMapping("/remove/{newsId}")
	public String handleDeleteNewsForm(@PathVariable("newsId") long newsId, RedirectAttributes redirectAttributes) {
	    
		//
		try {
	        String pictureUrl = newsService.getById(newsId).getPictureUrl();

	        String filename = pictureUrl.substring(pictureUrl.lastIndexOf("/") + 1);

	        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
	        Files.deleteIfExists(filePath); 

	        newsService.delete(newsId); 
	        redirectAttributes.addFlashAttribute("success", FlashMessages.NEWS_REMOVED.getDisplayName());
	    } catch (IOException | NullPointerException e) {

	        return "redirect:/news/" + newsId; 
	    }

	    return "redirect:/news";
	}
	
}