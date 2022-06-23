package com.example.domain;

import java.util.List;

/**
 * 
 * orderItemテーブルのドメイン.
 * 
 * @author takuya.matsura
 *
 */
public class OrderItem {
	/** ID */
	private Integer id;
	/** 商品ID */
	private Integer itemId;
	/**　注文ID */
	private Integer orderId;
	/**　数量 */
	private Integer quantity;
	/**　サイズ */
	private Character size;
	/**　商品 */
	private Item item;
	/**　トッピング一覧 */
	private List<OrderTopping> orderToppingList;
	
	/**
	 * 
	 * 1つの商品の小計を計算する.
	 * 
	 * @return 小計
	 */
	public int getSubTotal() {
		int subTotal = 0;
		if (getSize().compareTo('M') == 0) {
			subTotal = item.getPriceM() + (200 * orderToppingList.size());
		} else {
			subTotal = item.getPriceL() + (300 * orderToppingList.size());
		}
		
		return subTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", toppingList=" + orderToppingList + "]";
	}
}
