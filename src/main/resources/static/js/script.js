"use strict";

$(function() {
	$('.item-content').paginathing({//親要素のclassを記述
		perPage: 4,//1ページあたりの表示件数
		prevText:'<',//1つ前のページへ移動するボタンのテキスト
		nextText:'>',//1つ次のページへ移動するボタンのテキスト
		firstText:'最初へ',
		lastText:'最後へ',
	})
});