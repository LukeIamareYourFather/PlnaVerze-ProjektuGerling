package com.danger.insurance.infopages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Entry-point controller for the home or root path of the application.
 * This class handles requests to general landing pages, including the root index,
 * public welcome page, or high-level overviews. It can optionally redirect users
 * based on role, session status, or app state.
 *
 * Endpoints:
 * - @GetMapping("/") to render home or login redirect
 * 
 * Suggested Enhancements:
 * - Role-based greeting or landing logic
 * - Integration with session, alerts, or personalization metadata
 * - Dynamic quote, tips, or spotlight features
 */
@Controller
public class HomeController {
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;

	/**
	 * Renders the application's home page.
	 * This GET method handles requests to the root URL ("/") and serves as the welcome
	 * entry point for users. It resets any session-bound attributes, gathers metadata 
	 * for personalization or analytics, and populates the model with introductory content.
	 *
	 * Possible Enhancements:
	 * - Role-based redirect (e.g. admin dashboard vs public welcome)
	 * - Time-based greetings or feature highlights
	 * - System status indicators, announcements, or onboarding panels
	 *
	 * @param httpRequest provides access to client info and request metadata
	 * @param sessionStatus used to clear session attributes and reset state
	 * @param model the view model to pass welcome or dynamic attributes
	 * @return the name of the homepage view template
	 */
	@GetMapping("/")
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.HOME, httpRequest, sessionStatus, model);
		
		return "pages/home/index";
	}
	
}