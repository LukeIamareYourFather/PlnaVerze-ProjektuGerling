package com.danger.insurance.news.models.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.infopages.data.enums.FlashMessages;
import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.news.models.dto.mappers.NewsMapper;

@Service
public class NewsProcesingServices {
	
	@Autowired
	private NewsServiceImplementation newsService;
	
	@Autowired
	private NewsSupportServices supportServices;
	
	@Autowired
	private NewsMapper newsMapper;
	
	public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/news-pictures";
	

	public String processCreateNewsFormPost(MultipartFile file, NewsCreateDTO newsCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		long createdId = 0;
		
		if (file.isEmpty()) {
			supportServices.assignFailRedirectAttributes(newsCreateDTO, FlashMessages.MISSING_PICTURE, bindingResult, redirectAttributes);
			
			return "redirect:/news/create";
		}
		
		if (bindingResult.hasErrors()) {
			supportServices.assignFailRedirectAttributes(newsCreateDTO, FlashMessages.INVALID_INPUT, bindingResult, redirectAttributes);
			return "redirect:/news/create";
		}
		
		//
		try {
			NewsDTO newNewsDTO = newsMapper.mergeToNewsDTO(new NewsDTO(), newsCreateDTO);
	        Path uploadPath = Paths.get(UPLOAD_DIR);
	        
	        //
	        if (!Files.exists(uploadPath)) {
	        	
	        	String contentType = file.getContentType();
	            if (contentType == null || !contentType.startsWith("image/")) {
	                bindingResult.rejectValue("file", "invalid.file", "Soubor musí být obrázek.");
	                return "your-template";
	            }
	            
	            Files.createDirectories(uploadPath);
	        }

	        Path filePath = uploadPath.resolve(file.getOriginalFilename());
	        Files.write(filePath, file.getBytes());
	        newNewsDTO.setPictureUrl("/uploads/news-pictures/" + file.getOriginalFilename());
	        newNewsDTO.setPostDate(LocalDate.now());
	        createdId =  newsService.create(newNewsDTO);
	        redirectAttributes.addFlashAttribute("success", FlashMessages.NEWS_CREATED.getDisplayName());
		} catch (Exception e) {
			model.addAttribute("error", "Upload failed: " + e.getMessage());
		}
		
		return "redirect:/news/" + createdId;
	}

	public String processDeleteNewsFormPost(long newsId, RedirectAttributes redirectAttributes) {
		
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

	public String processUpdateNewsFormPost(long newsId, NewsCreateDTO newsCreateDTO, RedirectAttributes redirectAttributes) {
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