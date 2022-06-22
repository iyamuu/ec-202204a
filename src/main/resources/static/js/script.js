"use strict";

$(function() {
	$('.item-content').paginathing({//親要素のclassを記述
		perPage: 6,//1ページあたりの表示件数
		containerClass: 'pagination-container d-flex justify-content-center', // extend default container class
		ulClass: 'pagination', // extend default ul class
		liClass: 'page btn btn-outline-primary', // extend li class
		prevNext: false, // enable previous and next button
		firstLast: false,
	})

});