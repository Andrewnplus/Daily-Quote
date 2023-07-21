$(function () {
    const $notLoggedIn = $("#notLoggedIn");
    const $loggedIn = $("#loggedIn").hide();
    const $login = $("#login");
    const $userInfo = $("#userInfo").hide();

    function setJwtToken(token) {
        localStorage.setItem(TOKEN_KEY, token);
    }

    function removeJwtToken() {
        localStorage.removeItem(TOKEN_KEY);
    }

    function doLogin(loginData) {
        $.ajax({
            url: "/api/authenticate",
            type: "POST",
            data: JSON.stringify(loginData),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                setJwtToken(data.id_token);
                $login.hide();
                $notLoggedIn.hide();
                showLoginSuccessMessage()
                showUserInformation();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 401) {
                    $('#loginErrorModal')
                        .modal("show")
                        .find(".modal-body")
                        .empty()
                        .html("<p>" + jqXHR.responseJSON.message + "</p>");
                } else {
                    throw new Error("an unexpected error occured: " + errorThrown);
                }
            }
        });
    }

    function doLogout() {
        removeJwtToken();
        $login.show();
        $userInfo
            .hide()
            .find("#userInfoBody").empty();
        $loggedIn
            .hide()
            .attr("title", "")
            .empty();
        $notLoggedIn.show();
    }

    function showUserInformation() {
        $.ajax({
            url: "/api/user",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                const $userInfoBody = $userInfo.find("#userInfoBody");

                $userInfoBody.append($("<div>").text("Username: " + data.username));
                const $authorityList = $("<ul>");
                data.authorities.forEach(function (authorityItem) {
                    $authorityList.append($("<li>").text(authorityItem.name));
                });
                const $authorities = $("<div>").text("Authorities:");
                $authorities.append($authorityList);

                $userInfoBody.append($authorities);
                $userInfo.show();
            }
        });
    }

    function showLoginSuccessMessage() {
        $loggedIn
            .text("You've successfully login!")
            .show();
    }

    $("#loginForm").submit(function (event) {
        event.preventDefault();

        const $form = $(this);
        const formData = {
            username: $form.find('input[name="username"]').val(),
            password: $form.find('input[name="password"]').val()
        };
        doLogin(formData);
    });

    $("#logoutButton").click(doLogout);

    $("#adminServiceBtn").click(function () {
        $.ajax({
            url: "/api/users",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                showAllResponse(jqXHR.status, formatMemberData(data));
            },
            error: function (jqXHR, textStatus, errorThrown) {
                showAllResponse(jqXHR.status, jqXHR.responseJSON.message)
            }
        });
    });

    function formatMemberData(dataArray) {
        let formattedData = "";
        dataArray.forEach(data => {
            formattedData += "Username: " + data.username + "\n";
            formattedData += "Authority: " + data.authorities.map(auth => auth.name).join(", ") + "\n\n";
        });
        return formattedData;
    }

    function showAllResponse(status, data) {
        let displayArea = $("#response_get_all_members");
        displayArea.empty();
        displayArea.append("Status Code: " + status + "\n-------------------------\n" + "\nData: \n" + data);
    }

    $loggedIn.click(function () {
        $loggedIn
            .toggleClass("text-hidden")
            .toggleClass("text-shown");
    });

    if (getJwtToken()) {
        $login.hide();
        $notLoggedIn.hide();
        showLoginSuccessMessage();
        showUserInformation();
    }
});
