"use strict";

$("#card-input").hide();
$(function () {
  $("input:radio[name='paymentMethod']").on("change", function () {
    if ($("input:radio[name='paymentMethod']:checked").val() === "2") {
      $("#card-input").show();
    } else {
		$("#card-input").hide();		
	}
  });
});
