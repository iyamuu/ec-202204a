package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
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
	 * @param sort 並び順の設定
	 * @return 商品一覧情報
	 */
	public List<Item> showList(String sort) {

		List<Item> itemList = itemRepository.findAll(sort);
		List<Topping> toppingList = toppingRepository.findAll();

		for (Item item : itemList) {
			item.setToppingList(toppingList);
		}

		return itemList;
	}

	/**
	 * 検索した商品一覧情報.
	 * 
	 * @param name 検索する商品名
	 * @param sort 並び順の設定
	 * @return 検索した商品一覧
	 */
	public List<Item> search(String name, String sort) {
		List<Item> itemList = itemRepository.findByName(name, sort);
		List<Topping> toppingList = toppingRepository.findAll();

		for (Item item : itemList) {
			item.setToppingList(toppingList);
		}

		return itemList;
	}
	
	/**
	 * 
	 * ランダムでおすすめのアイテム3つを返します。
	 * 
	 * @return　おすすめ商品情報一覧
	 */
	public List<Item> getRecomendationItemList(){
		List<Item> itemList = showList("DESC");
		List<Item> recomendItemList = new ArrayList<>();
		
		Collections.shuffle(itemList);
		int cnt = 0;
		for (Item item : itemList) {
			recomendItemList.add(item);
			cnt++;
			if (cnt >= 3) break;
		}
		
		return recomendItemList;
	}

}
