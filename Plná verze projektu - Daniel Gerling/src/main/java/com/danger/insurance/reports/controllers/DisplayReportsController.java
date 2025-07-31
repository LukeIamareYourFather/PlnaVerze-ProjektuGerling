package com.danger.insurance.reports.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.reports.models.services.ReportsService;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for displaying system-generated reports.
 * <p>
 * Mapped under the "/reports" base path, this controller provides endpoints for rendering
 * various types of reports related to party entities, operations, audits, or performance metrics.
 * It supports both tabular and graphical views, and may include filtering, export, or drill-down capabilities.
 * </p>
 */
@PreAuthorize("hasRole('ADMINISTRATOR')")
@Controller
@RequestMapping("reports")
public class DisplayReportsController {
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;

	@Autowired
	private ReportsService reportsService;
	
	/**
	 * Renders the index view for the reports module.
	 * <p>
	 * Triggered via GET at "/reports", this method serves as the landing page for users
	 * accessing system-generated reports. It resets any session-scoped attributes to ensure
	 * a clean reporting context and prepares the model with metadata or navigation elements
	 * for rendering the reports dashboard.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Clear session attributes to reset previous report workflows</li>
	 *   <li>Prepare model attributes for rendering the reports index view</li>
	 *   <li>Optionally inspect the HTTP request for personalization or analytics</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears session-scoped data from prior report flows</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code reportCategories} – list of available report types or categories</li>
	 *   <li>{@code userContext} – optional user-specific data for personalization</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Acts as the entry point for viewing, filtering, or exporting reports</li>
	 *   <li>May include links to recent reports, scheduled exports, or analytics dashboards</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Display report usage statistics or last accessed timestamps</li>
	 *   <li>Support role-based visibility of report categories</li>
	 *   <li>Integrate quick filters or search across report types</li>
	 * </ul>
	 *
	 * @param httpRequest the incoming HTTP request, used for context or analytics
	 * @param sessionStatus used to clear session attributes and reset reporting state
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the reports index page
	 */
	@GetMapping
	public String renderReportsIndex(HttpServletRequest httpRequest, SessionStatus sessionStatus, Model model) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.REPORTS, httpRequest, sessionStatus, model);
		
		return "pages/reports/index";
	}
	
	/**
	 * Renders the overview page for company-level reports.
	 * <p>
	 * Triggered via GET at "/reports/company", this method prepares the model with
	 * aggregated data and metadata relevant to company-wide reporting. It serves as
	 * a centralized view for administrators or analysts to monitor operational metrics,
	 * compliance summaries, and performance indicators across all party entities.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch and aggregate company-wide report data</li>
	 *   <li>Populate the model with summary metrics and visual indicators</li>
	 *   <li>Forward to the view responsible for rendering the company reports dashboard</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code companyReportSummary} – high-level metrics such as total parties, active policies, or flagged records</li>
	 *   <li>{@code reportDateRange} – default or selected time window for the report</li>
	 *   <li>{@code complianceStatus} – optional indicators for regulatory alignment</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by executives, auditors, or system administrators</li>
	 *   <li>Supports strategic decision-making and operational oversight</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Integrate charts or graphs for visual analytics</li>
	 *   <li>Support export to PDF or Excel for offline review</li>
	 *   <li>Enable drill-down into department or region-specific reports</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass report data to the view
	 * @return name of the view template that displays the company reports overview
	 */
	@GetMapping("company")
	public String renderCompanyReportsOverview(Model model) {
		return reportsService.addCompanyReportsOverviewAttributes(model);
	}
	
	/**
	 * Renders the dashboard view for segment-level reports.
	 * <p>
	 * Triggered via GET at "/reports/segments", this method prepares the model with
	 * grouped data and visual summaries based on business segments, client categories,
	 * or operational divisions. It enables comparative analysis across segments to
	 * support strategic planning, performance tracking, and compliance oversight.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Retrieve and organize report data by predefined segments</li>
	 *   <li>Populate the model with summary metrics and visual indicators</li>
	 *   <li>Forward to the view responsible for rendering the segment reports dashboard</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code segmentSummaries} – list of aggregated metrics per segment</li>
	 *   <li>{@code segmentDefinitions} – metadata describing each segment’s scope</li>
	 *   <li>{@code reportFilters} – optional filters for time range, region, or status</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by analysts, managers, or compliance officers</li>
	 *   <li>Supports comparative analysis across business units or client types</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Enable drill-down into individual segment details</li>
	 *   <li>Integrate charts or heatmaps for visual comparison</li>
	 *   <li>Support export to Excel or PDF for offline analysis</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass report data to the view
	 * @return name of the view template that displays the segment reports overview
	 */
	@GetMapping("segments")
	public String renderSegmentsReportsOverview(Model model) {
		return reportsService.addSegmentsReportsOverviewAttributes(model);
	}
	
}