package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

@Service
public class ShowItemDetailService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	/**
	 * 
	 * アイテムの詳細を取得します.
	 * 
	 * @param id 商品ID
	 * @return　商品
	 */
	public Item ShowDetail(Integer id) {
		Item item = itemRepository.findById(id);
		List<Topping> toppingList = toppingRepository.findAll();
		item.setToppingList(toppingList);
		
		return item;
	}
}
