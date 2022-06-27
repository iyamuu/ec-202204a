package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品値段の変更における業務処理を行います.
 * 
 * @author isodakeisuke
 *
 */
@Service
public class updateItemPriceService {

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 商品の値段を変更します
	 * 
	 * @param item 商品情報
	 */
	public void updatePrice(Item item) {
		itemRepository.updatePrice(item);
	}
}
