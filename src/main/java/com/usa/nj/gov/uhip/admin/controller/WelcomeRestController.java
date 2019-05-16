package com.usa.nj.gov.uhip.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usa.nj.gov.uhip.util.UHIPAppProperties;

@RestController
public class WelcomeRestController {
	private UHIPAppProperties uhipAppProp;
	
	@GetMapping("/welcome")
	public String get() {
		return "welcome";
		
	}

}
