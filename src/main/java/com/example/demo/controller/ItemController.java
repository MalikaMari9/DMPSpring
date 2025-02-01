package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.ui.Model;

@Controller
public class ItemController {
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/itemList")
	public String viewItems(Model model) {
		List<Item> itemList = itemRepo.findAll();
		model.addAttribute("itemList", itemList);
		
		return "itemList";
		
	}
	
	@GetMapping("itemList/{itemID}")
	public String viewItem(@PathVariable("itemID") Integer itemID, Model model) {
		Item item = itemRepo.getReferenceById(itemID);
		model.addAttribute("item", item);
		return "viewSale";
	}
	


}
