"use strict";

$(function () {
    if ($("#address").val() == "") {
        $("#zipcode").blur(function () {
            $.ajax({
                url: "http://zipcoda.net/api",
                dataType: "jsonp",
                data: {
                    zipcode: $("#zipcode").val(),
                },
                async: true,
            })
                .done(function (data) {
                    $("#address").val(data.items[0].components.join(""));
                })
                .fail(function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log("XMLHttpRequest : " + XMLHttpRequest);
                    console.log("textStatus : " + textStatus);
                    console.log("errorThrown : " + errorThrown);
                });
        });
    }
});
