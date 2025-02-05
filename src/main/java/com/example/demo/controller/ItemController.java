package com.example.demo.controller;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.demo.entity.Category;

import com.example.demo.entity.Item;
import com.example.demo.entity.Auction.Auction;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.Auction.AuctionRepository;
import com.example.demo.repository.Auction.AuctionTrackRepository;

import org.springframework.ui.Model;

@Controller
public class ItemController {
	@Autowired
	ItemRepository itemRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AuctionRepository auctionRepo;
	@Autowired
	AuctionTrackRepository auctionTrackRepo;
	

	@GetMapping("/sell-item")
	public String showSellItemForm(Model model) {
	    // Fetch all top-level categories (where parentCategory is NULL)
	    List<Category> parentCategories = categoryRepo.findByParentCategoryIsNull();

	    // Fetch all subcategories mapped by parent category
	    Map<Category, List<Category>> categoryMap = parentCategories.stream()
	            .collect(Collectors.toMap(
	                    parent -> parent,
	                    parent -> categoryRepo.findByParentCategory(parent)
	            ));

	    // Add to model
	    model.addAttribute("categoryMap", categoryMap);
	    return "sellItemNormal";
	}
	

	@GetMapping("/itemList")
	public String viewItems(Model model) {
		List<Item> itemList = itemRepo.findAll();
		List<Auction> auctionList = auctionRepo.findAll();
		model.addAttribute("itemList", itemList);
		model.addAttribute("auctionList", auctionList);
		
		return "itemList";
		
	}
	
	@GetMapping("itemList/{itemID}")
	public String viewItem(@PathVariable("itemID") Long itemID, Model model) {
		Item item = itemRepo.getReferenceById(itemID);
		Auction auction = auctionRepo.findByItem_ItemID(itemID);
		
		Long auctionID = (auction != null) ? auction.getAuctionID() : null;
		int AuctionCount = auctionTrackRepo.countByAuction_AuctionID(auctionID);
		Double maxBid = auctionTrackRepo.findMaxPriceByAuctionID(auctionID);
		
		//tags

		List<String> tagNames = itemRepo.findTagNamesByItemId(itemID);
		String tagOutput = String.join(",", tagNames);

		
		model.addAttribute("item", item);
		model.addAttribute("auction", auction);
		
		model.addAttribute("auctionCount",AuctionCount);
		model.addAttribute("maxBid",maxBid);
		
		model.addAttribute("tagOutput", tagOutput);
		return "viewSale";
	}
	


}
