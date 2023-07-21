$(document).ready(function () {
    let quoteData = null;
    const dailyQuoteBtn = $("#DailyQuoteBtn");
    const saveQuoteBtn = $("#SaveQuoteBtn");
    dailyQuoteBtn.prop('disabled', false);
    saveQuoteBtn.prop('disabled', true);
    $("#user_comment").prop('disabled', true);

    dailyQuoteBtn.click(function () {
        $.ajax({
            url: "/api/userQuote",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                quoteData = data;
                showQuote(data);
                $("#SaveQuoteBtn").prop('disabled', false);
                $("#user_comment").prop('disabled', false);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error getting quote', textStatus, errorThrown);
            }
        });
    });

    saveQuoteBtn.click(function () {
        if (quoteData) {
            quoteData.userComment = $("#user_comment").val();

            const httpMethod = quoteData.userQuoteId ? 'PUT' : 'POST';
            const url = '/api/userQuote';
            $.ajax({
                url: url,
                type: httpMethod,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                headers: createAuthorizationTokenHeader(),
                data: JSON.stringify(quoteData),
                success: function (data, textStatus, jqXHR) {
                    quoteData = data;
                    console.log('Quote saved successfully');
                    $("#SaveQuoteBtn").prop('disabled', true);
                    $("#user_comment").prop('disabled', true);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error('Error saving quote', textStatus, errorThrown);
                }
            });
        }
    });

    function showQuote(quote) {
        document.getElementById('quote_text').innerText = '"' + quote.quoteText + '"';
        document.getElementById('quote_author').innerText = '-- ' + quote.author;
        document.getElementById('update_date').innerText = 'Last comment: ' + quote.updatedDate ? quote.updatedDate : "N/A";
        document.getElementById('user_comment').value = quote.userComment ? quote.userComment : "";
    }

    $("#GetUserQuotesBtn").click(function () {
        $.ajax({
            url: '/api/userQuotes',
            type: 'GET',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: createAuthorizationTokenHeader(),
            success: function (data, textStatus, jqXHR) {
                $('#quotesContainer').empty();
                data.forEach(function (userQuote) {
                    let $card = $($("#quoteCardTemplate").html());
                    $card.find(".card-title").text('"' + userQuote.quote.text + '"');
                    $card.find(".card-text").eq(0).text('-- ' + userQuote.quote.author);
                    $card.find(".card-text").eq(1).text('Last Update: ' + new Date(userQuote.updatedDate).toLocaleDateString());
                    $card.find(".card-text").eq(2).text('Note: ' + userQuote.comment);
                    $card.data("quoteId", userQuote.id);
                    $("#quotesContainer").append($card);
                })
            }
        });
    });

    $("#quotesContainer").on("click", ".deleteBtn", function () {
        let $card = $(this).closest(".card");
        let quoteId = $card.data("quoteId");
        $.ajax({
            url: '/api/userQuote/' + quoteId,
            type: 'PATCH',
            contentType: "application/json; charset=utf-8",
            headers: createAuthorizationTokenHeader(),
            success: function (result) {
                $card.remove();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error('Error deleting UserQuote', textStatus, errorThrown);
            }
        });
    });
});
