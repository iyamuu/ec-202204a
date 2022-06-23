package com.example.domain;

/**
 * クレジットカードの決済処理のリクエスト情報が入ります.
 * 
 * @author keisuke.isoda
 *
 */
public class CreditCardRequest {

	/* クレジットカード番号 */
	private Integer card_number;
	/* カードの有効期限（年） */
	private Integer card_exp_year;
	/* カードの有効期限（月） */
	private Integer card_exp_month;
	/* カード名義 */
	private String card_name;
	/* セキュリティーコード */
	private Integer card_ccv;

	public Integer getCard_number() {
		return card_number;
	}

	public void setCard_number(Integer card_number) {
		this.card_number = card_number;
	}

	public Integer getCard_exp_year() {
		return card_exp_year;
	}

	public void setCard_exp_year(Integer card_exp_year) {
		this.card_exp_year = card_exp_year;
	}

	public Integer getCard_exp_month() {
		return card_exp_month;
	}

	public void setCard_exp_month(Integer card_exp_month) {
		this.card_exp_month = card_exp_month;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public Integer getCard_ccv() {
		return card_ccv;
	}

	public void setCard_ccv(Integer card_ccv) {
		this.card_ccv = card_ccv;
	}

	@Override
	public String toString() {
		return "CreditCardPayment [card_number=" + card_number + ", card_exp_year=" + card_exp_year
				+ ", card_exp_month=" + card_exp_month + ", card_name=" + card_name + ", card_ccv=" + card_ccv + "]";
	}
}
