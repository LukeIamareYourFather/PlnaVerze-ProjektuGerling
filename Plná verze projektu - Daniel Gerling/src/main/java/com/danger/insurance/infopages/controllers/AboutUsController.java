package com.danger.insurance.infopages.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for handling "About Us" page requests.
 * Mapped under "/about-us", this class serves static or semi-dynamic content
 * that describes the organization, mission, team, and key initiatives.
 */
@Controller
@RequestMapping("about-us")
public class AboutUsController {
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;

	/**
	 * Renders the "About Us" index page.
	 * This GET endpoint mapped to "/about-us" acts as the landing page for organizational
	 * information, values, and team background. It optionally logs client access details, resets 
	 * any previous session-bound content, and prepares model attributes for branding or dynamic data.
	 *
	 * Features that can be added:
	 * - IP-based greeting or visitor tracking
	 * - Dynamic quote of the day or spotlight section
	 * - Highlights of recent initiatives or leadership updates
	 *
	 * @param httpRequest used to extract request-related metadata (e.g. IP address)
	 * @param sessionStatus used to clear any lingering session attributes
	 * @param model used to populate view with organization details
	 * @return the name of the "About Us" view template
	 */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.ABOUT_US, httpRequest, sessionStatus, model);
		
		return "pages/about-us/index";
	}
	
}