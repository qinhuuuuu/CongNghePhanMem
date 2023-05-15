$(document).ready(function () {

    $('#addProduct').click(function () {
        var name = $('#input-name').val();
        var price = $('#input-price').val().trim();
        var inventory = $('#input-inventory').val();
        // img: $()
        var detail = $('#input-detail').val();
        var decription = $('#input-decription').val();


        if (name == "" || price == ""  || inventory == "" || decription == "" ) {
            alert("Vui lòng kiểm tra lại")
            return;
        }
        $.ajax({
            url: "/addProduct",
            type: "get",
            data: {
                name: name,
                price: price,
                inventory: inventory,
                decription: decription,
            }, success: function (data) {

            }
        })
    })


})



