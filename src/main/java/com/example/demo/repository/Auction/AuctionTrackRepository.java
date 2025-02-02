package com.example.demo.repository.Auction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Auction.AuctionTrack;

public interface AuctionTrackRepository extends JpaRepository<AuctionTrack, Integer>{
	
	
}