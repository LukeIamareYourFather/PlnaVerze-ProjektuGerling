package com.danger.insurance.shared.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.shared.enums.ActivePageTokens;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CommonSupportServiceShared {

	public void removeAllSessionAttributes(ActivePageTokens activePageToken, HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		assignActivePageAttribute(activePageToken, model);
		sessionStatus.setComplete();       
		httpRequest.getSession().removeAttribute("incidentCommentDTO");
		httpRequest.getSession().removeAttribute("removalReasonsDTO");
		httpRequest.getSession().removeAttribute("incidentRemovalReasonsDTO");
		httpRequest.getSession().removeAttribute("contractDTO");
		httpRequest.getSession().removeAttribute("insurancesReasonsDTO");
		httpRequest.getSession().removeAttribute("reasonsDTO");
	}

	private void assignActivePageAttribute(ActivePageTokens activePageToken, Model model) {
		model.addAttribute("activePage", activePageToken.getDisplayName());
	}
	
}