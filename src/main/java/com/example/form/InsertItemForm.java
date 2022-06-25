package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

/**
 * 商品追加の情報を受取るフォームクラス
 * 
 * @author isodakeisuke
 *
 */
public class InsertItemForm {

	/* 商品名 */
	@NotBlank(message = "商品名を入力してください")
	private String itemName;
	/* 説明文 */
	@NotBlank(message = "説明文を入力してください")
	private String description;
	/* Mサイズの値段 */
	@NotBlank(message = "Mサイズの値段を入力してください")
	private String priceM;
	/* Lサイズの値段 */
	@NotBlank(message = "Lサイズの値段を入力してください")
	private String priceL;
	/* 商品画像 */
	@NotNull(message = "商品画像を選択してください")
	private MultipartFile itemImage;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getpriceM() {
		return priceM;
	}

	public void setpriceM(String priceM) {
		this.priceM = priceM;
	}

	public String getpriceL() {
		return priceL;
	}

	public void setpriceL(String priceL) {
		this.priceL = priceL;
	}

	public MultipartFile getItemImage() {
		return itemImage;
	}

	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
	}

	@Override
	public String toString() {
		return "InsertItemForm [itemName=" + itemName + ", description=" + description + ", priceM=" + priceM
				+ ", priceL=" + priceL + ", itemImage=" + itemImage + "]";
	}

}
