package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dmpController {
	@GetMapping("/viewItem")
	public String viewItem() {
		return "viewSale";
		
	}
	@GetMapping("/test")
	public String test() {
		return "test";
		
	}
}
