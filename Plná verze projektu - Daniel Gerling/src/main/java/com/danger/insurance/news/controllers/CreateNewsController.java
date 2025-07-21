package com.danger.insurance.news.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.news.models.dto.mappers.NewsMapper;
import com.danger.insurance.news.models.service.NewsService;

@PreAuthorize("hasAnyRole('JOURNALIST', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class CreateNewsController {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsMapper newsMapper;
	
	public static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/news-pictures";
	
	@GetMapping("create")
	public String renderCreateNewsForm(Model model) {
		model.addAttribute("newsCreateDTO", new NewsCreateDTO());
		model.addAttribute("formName", "Vytvoření novinky");
		model.addAttribute("formAction", "create/process");
		model.addAttribute("buttonLabel", "Vytvořit");
		model.addAttribute("isEditForm", false);
		
		return "pages/news/create";
	}
	
	@PostMapping("create/process")
	public String processCreateNewsFormSubmit(@RequestParam("file") MultipartFile file, @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, Model model, BindingResult bindingResult) {
		long createdId = 0;
		
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
		} catch (Exception e) {
			model.addAttribute("error", "Upload failed: " + e.getMessage());
		}
		
		return "redirect:/news/" + createdId;
	}
	
	
}