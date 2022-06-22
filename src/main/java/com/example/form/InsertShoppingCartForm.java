package com.example.form;

import java.util.List;

/**
 * 
 * ショッピングカートに商品を追加するフォーム.
 * 
 * @author takuya.matsura
 *
 */
public class InsertShoppingCartForm {
	/** 商品ID */
	private Integer itemId;
	/**　数量 */
	private Integer quantity;
	/**　サイズ */
	private Character size;
	/**　トッピング一覧 */
	private List<Integer> toppingList;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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

	public List<Integer> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Integer> toppingList) {
		this.toppingList = toppingList;
	}

	@Override
	public String toString() {
		return "InsertShoppingCartForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size
				+ ", toppingList=" + toppingList + "]";
	}

}
