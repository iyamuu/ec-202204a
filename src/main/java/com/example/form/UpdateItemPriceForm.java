package com.example.form;

/**
 * 商品の値段変更情報を受取るフォームクラスです.
 * 
 * @author isodakeisuke
 *
 */
public class UpdateItemPriceForm {
	/* 商品id */
	private String itemId;
	/* Mサイズの値段 */
	private String priceM;
	/* Lサイズの値段 */
	private String priceL;


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPriceM() {
		return priceM;
	}

	public void setPriceM(String priceM) {
		this.priceM = priceM;
	}

	public String getPriceL() {
		return priceL;
	}

	public void setPriceL(String priceL) {
		this.priceL = priceL;
	}

	@Override
	public String toString() {
		return "UpdateItemPriceForm [itemId=" + itemId + ", priceM=" + priceM + ", priceL=" + priceL + "]";
	}

}
