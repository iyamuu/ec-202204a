package com.example.form;

public class CreditCardPaymentForm {
	
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

	@Override
	public String toString() {
		return "CreditCardPaymentForm [cardNumber=" + cardNumber + ", cardExpMonth=" + cardExpMonth + ", cardExpYear="
				+ cardExpYear + ", cardCvv=" + cardCvv + ", cardName=" + cardName + "]";
	}

}
