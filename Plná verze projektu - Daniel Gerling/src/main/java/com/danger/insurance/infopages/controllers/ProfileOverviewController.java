package com.danger.insurance.infopages.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PreAuthorize("hasRole('PARTY')")
@Controller
@RequestMapping("overview")
public class ProfileOverviewController {

}