package com.danger.insurance.infopages.controllers;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('PARTY')")
public class ProfileOverviewController {

}