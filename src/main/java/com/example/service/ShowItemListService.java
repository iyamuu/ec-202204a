package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.OrderRepository;
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
	
	@Autowired
	private OrderRepository orderRepository;

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
	 * カート内の商品数を計算する.
	 * 
	 * @param userId ユーザーID
	 * @return　商品数
	 */
	public Integer calcItemCountInCart(Integer userId) {
		Order order = new Order();
		try {
			order = orderRepository.findByUserIdAndStatus(userId, 0).get(0);
		} catch (IndexOutOfBoundsException e) {
			order = null;
		}
		
		Integer itemCount = null;
		if(order == null) {
			itemCount = 0;
		} else {
			itemCount = order.getOrderItemList().size();			
		}
		return itemCount;
	}

}
