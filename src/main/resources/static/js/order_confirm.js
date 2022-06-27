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

  $("#modal-order-confirm-button").on("click", function(){
    $("#orderForm").submit();
    $("#modal-order-confirm-button").prop("disabled", true);;
  })
});
