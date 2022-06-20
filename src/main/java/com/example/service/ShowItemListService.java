package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品一覧情報を取得するサービス.
 * 
 * @author kohei.yamamura
 *
 */
@Service
public class ShowItemListService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ToppingRepository toppingRepository;

	/**
	 * 商品一覧情報を取得.
	 * 
	 * @return
	 */
	public List<Item> showList() {

		List<Item> itemList = itemRepository.findAll();
		List<Topping> toppingList = toppingRepository.findAll();

		for (Item item : itemList) {
			item.setToppingList(toppingList);
		}

		System.out.println(itemList);

		return itemList;
	}

}
