"use strict";
$(function () {
    $(document).on("click", "#get-login-info", function () {
        fetch_login_info();
    });

    function fetch_login_info() {
        let hostUrl = "http://localhost:8080/ec-202204a/api/login-info";
        $.ajax({
            url: hostUrl,
            type: "GET",
            dataType: "json",
            data: {
            },
            async: true,
        })
            .done(function (data) {
                $("#destinationName").val(data.name);
                $("#destinationEmail").val(data.email);
                $("#zipcode").val(data.zipcode);
                $("#address").val(data.address);
                $("#destinationTel").val(data.telephone);
            })
            .fail(function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("XMLHttpRequest : " + XMLHttpRequest.status);
                console.log("textStatus     : " + textStatus);
                console.log("errorThrown    : " + errorThrown.message);
            });
    }
});