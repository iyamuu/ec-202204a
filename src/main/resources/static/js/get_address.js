"use strict";

$(function() {
	$("#zipcode").blur(function() {
		if ($("#address").val() == "") {
			$.ajax({
				url: "http://zipcoda.net/api",
				dataType: "jsonp",
				data: {
					zipcode: $("#zipcode").val(),
				},
				async: true,
			})
				.done(function(data) {
					$("#address").val(data.items[0].components.join(""));
				})
				.fail(function(XMLHttpRequest, textStatus, errorThrown) {
					console.log("XMLHttpRequest : " + XMLHttpRequest);
					console.log("textStatus : " + textStatus);
					console.log("errorThrown : " + errorThrown);
				});
		}
	});
});
