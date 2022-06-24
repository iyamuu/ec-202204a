package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 注文のフォームクラス.
 * 
 * @author takato.tomizawa
 *
 */
public class OrderForm {

	/** 注文ID */
	private Integer orderId;
	/** 宛先氏名 */
	@NotBlank(message = "名前を入力してください")
	private String destinationName;
	/** 宛先Eメール */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String destinationEmail;
	/** 宛先郵便番号 */
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はxxx-xxxxの形式で入力してください")
	private String destinationZipCode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力してください")
	private String destinationAddress;
	/** 宛先電話番号 */
	@Pattern(regexp = "^[0-9]{3}[0-9]?-[0-9]{4}-[0-9]{4}$", message = "電話番号はxxx-xxxx-xxxxの形式で入力してください")
	private String destinationTel;
	/** 配達時間 */
	@NotBlank(message="配達日時を入力してください")
	private String deliveryTime;
	/** 支払方法 */
	private Integer paymentMethod;
	/** カード番号(数字14桁-16桁) */
	private String cardNumber;
	/** カード有効期限(月)(数字2桁) */
	private String cardExpMonth;
	/** カード有効期限(年)(数字4桁) */
	private String cardExpYear;
	/** セキュリティコード(数字3桁-4桁) */
	private String cardCvv;
	/** カード名義人名 */
	private String cardName;
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpMonth() {
		return cardExpMonth;
	}

	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}

	public String getCardExpYear() {
		return cardExpYear;
	}

	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}

	public String getCardCvv() {
		return cardCvv;
	}

	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * @return the destinationEmail
	 */
	public String getDestinationEmail() {
		return destinationEmail;
	}

	/**
	 * @param destinationEmail the destinationEmail to set
	 */
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	/**
	 * @return the destinationZipcode
	 */
	public String getDestinationZipCode() {
		return destinationZipCode;
	}

	/**
	 * @param destinationZipcode the destinationZipcode to set
	 */
	public void setDestinationZipCode(String destinationZipCode) {
		this.destinationZipCode = destinationZipCode;
	}

	/**
	 * @return the destinationAddress
	 */
	public String getDestinationAddress() {
		return destinationAddress;
	}

	/**
	 * @param destinationAddress the destinationAddress to set
	 */
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	/**
	 * @return the destinationTel
	 */
	public String getDestinationTel() {
		return destinationTel;
	}

	/**
	 * @param destinationTel the destinationTel to set
	 */
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	/**
	 * @return the deliveryTime
	 */
	public String getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	/**
	 * @return the paymentMethod
	 */
	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "OrderForm [orderId=" + orderId + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipCode=" + destinationZipCode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime
				+ ", paymentMethod=" + paymentMethod + ", cardNumber=" + cardNumber + ", cardExpMonth=" + cardExpMonth
				+ ", cardExpYear=" + cardExpYear + ", cardCvv=" + cardCvv + ", cardName=" + cardName + "]";
	}


}
