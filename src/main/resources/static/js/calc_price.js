"use strict";

// 商品金額計算
$(function() {
	let size = 0;
	let itemPrice = 0;
	let toppingPrice = 0;
	let toppingNum = 0;
	let quantity = 1;
	CalcPrice(itemPrice, toppingPrice, quantity, toppingNum);

	// サイズ取得
	$("input[name='size']").on("change", function() {
		size = $('input[name="size"]:checked').val();
		if (size == "M") {
			itemPrice = parseInt($(".priceM").text().replace(/,/g, ''), 10);
			toppingPrice = 200;
		} else if (size == "L") {
			itemPrice = parseInt($(".priceL").text().replace(/,/g, ''), 10);
			toppingPrice = 300;
		}

		CalcPrice(itemPrice, toppingPrice, quantity, toppingNum);
	});

	// トッピング
	$("input[name = 'toppingList']").on("click", function() {
		let toppings = $("input[name='toppingList']");
		toppingNum = 0;
		for (let i = 0; i < toppings.length; i++) {
			if (toppings[i].checked) {
				toppingNum++;
			}
		}

		CalcPrice(itemPrice, toppingPrice, quantity, toppingNum);
	});

	// 数量
	$("select[name = 'quantity']").change(function() {
		quantity = $("select[name = 'quantity']").val();

		CalcPrice(itemPrice, toppingPrice, quantity, toppingNum);
	});
});

// 値段計算
let CalcPrice = (itemPrice, toppingPrice, quantity, toppingNum) => {
	let taxPrice = 0;
	let totalPrice = 0;
	let finalPrice = 0;

	// 文字列認識の対策
	itemPrice = Number(itemPrice);
	toppingPrice = Number(toppingPrice);
	quantity = Number(quantity);
	toppingNum = Number(toppingNum);

	totalPrice = (itemPrice + toppingPrice * toppingNum) * quantity;
	taxPrice = totalPrice * 0.1;
	finalPrice = totalPrice + taxPrice;

	$(function() {
		
		// カンマ区切りで出力
		$(".tax-price").text(String(taxPrice).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,'));
		$(".total-price").text(String(finalPrice).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,'));
	});
};
