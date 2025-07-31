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
 * Controller responsible for managing insurance enrollment pages.
 * Mapped under "/enroll", this class facilitates navigation and interaction
 * with InsureMe's enrollment workflow—guiding users from the initial landing page
 * to plan selection, profile input, and submission.
 */
@Controller
@RequestMapping("enroll")
public class InsureMePageController {
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;

	/**
	 * Renders the enrollment landing page.
	 * This GET handler responds to "/enroll", resetting session attributes and populating
	 * introductory data for users beginning the insurance enrollment process. It may include 
	 * welcome text, plan options, eligibility tips, or progress hints based on prior session data.
	 *
	 * Optional enhancements:
	 * - Visitor IP tracking for analytics
	 * - Personalized onboarding based on cookie/session data
	 * - Pre-enrollment checklist or policy disclaimers
	 *
	 * @param httpRequest to retrieve client metadata (e.g. IP address)
	 * @param sessionStatus for clearing session-scoped attributes
	 * @param model to pass introductory data to view layer
	 * @return name of the enrollment landing page view
	 */
	@GetMapping
	public String renderIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.ENROLL, httpRequest, sessionStatus, model);
		
		return "pages/enroll/index";
	}
	
}