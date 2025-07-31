package com.danger.insurance.shared.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danger.insurance.accounts.data.enums.AccountRoles;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.accounts.models.services.AccountsServiceImplementation;
import com.danger.insurance.news.models.dto.NewsDTO;
import com.danger.insurance.news.models.service.NewsServiceImplementation;

@Service
public class RequiredDataInjectorSupportService {

	@Autowired
	private AccountsServiceImplementation accountsService;
	
	@Autowired
	private NewsServiceImplementation newsService;
	
	public boolean checkIfAdminAccountIsMissing(String adminEmail) {
		
		List<AccountsDTO> existingAccounts = accountsService.getAll();
		
		//
		for (int i = 0; i < existingAccounts.size(); i++) {
			System.out.println(existingAccounts.get(i).getUserEmail());
			//
			if (existingAccounts.get(i).getUserEmail().equals(adminEmail)) {
				return false;
			}
			
		}
		
		return true;
	}
	
	public void createAdminAccount(String adminEmail) {
		String adminPassword = "asd";
		AccountsDTO adminAccount = new AccountsDTO();
		adminAccount.setUserEmail(adminEmail);
		adminAccount.setUserPassword(adminPassword);
		adminAccount.setUserRole(AccountRoles.ROLE_ADMINISTRATOR);
		accountsService.create(adminAccount, adminPassword);
	}
	
	public boolean checkIfNewsArticleIsMissing(String articleTitle) {
		List<NewsDTO> existingNews = newsService.getAll();
		
		//
		for (NewsDTO newsArticle : existingNews) {
			
			//
			if (newsArticle.getTitle().equals(articleTitle)) {
				return false;
			}
			
		}
		
		return true;
	}
	
	public void createNewsArticle(String articleTitle) {
		NewsDTO injectedNewsArticle = new NewsDTO();
		injectedNewsArticle.setTitle(articleTitle);
		injectedNewsArticle.setDescription("Díky první pobočce v České Republice jsme vám zase o něco blíže");
		injectedNewsArticle.setContent("Tento významný krok nám umožňuje být blíže našim zákazníkům."
									 + "Otevřením nové kanceláře jsme vytvořili prostor pro osobní kontakt a rychlejší řešení pojistných záležitostí."
					                 + "Těšíme se na společnou cestu.");
		injectedNewsArticle.setPostDate(LocalDate.of(2025, 7, 31));
		injectedNewsArticle.setPictureUrl("/uploads/news-pictures/grand-opening.jpg");
		newsService.create(injectedNewsArticle);
	}
	
}