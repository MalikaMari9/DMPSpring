package com.example.demo.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.entity.tag.ItemTag;

public interface ItemTagRepository extends JpaRepository<ItemTag, Long>{
	
	
}
