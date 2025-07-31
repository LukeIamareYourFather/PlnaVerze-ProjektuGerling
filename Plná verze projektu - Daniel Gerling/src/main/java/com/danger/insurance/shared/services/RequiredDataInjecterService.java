package com.danger.insurance.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequiredDataInjecterService {

	@Autowired
	private RequiredDataInjectorSupportService supportService;
	
	public void injectRequiredAccountIfMissing() {
		String adminEmail = "admin@meyers.com";
		boolean isAdminAccountMissing = supportService.checkIfAdminAccountIsMissing(adminEmail);
		
		//
		if (isAdminAccountMissing) {
			supportService.createAdminAccount(adminEmail);
		}

	}
	
	public void injectRequiredArticleIfMissing() {
		String articleTitle = "Slavnostní otevření nové pobočky";
		boolean isNewsArticleMissing = supportService.checkIfNewsArticleIsMissing(articleTitle);
		
		//
		if (isNewsArticleMissing) {
			supportService.createNewsArticle(articleTitle);
		}
			
	}
	
	
}