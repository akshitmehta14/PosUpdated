var data = [];

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	console.log(baseUrl);
	return baseUrl + "/api/order";
}
function createOrder(event){
    url = getOrderUrl();
    console.log(data);
    var json = JSON.stringify(data);
    console.log(json);
    data = [];
    successFx = function(response){
    nativeToast({
        message: "Order Successfully Created" ,
        position: 'north',
        timeout: 2000,
        type: 'success',
        closeOnClick: true
        });
        displayOrderList();
    }
    ajaxRequest(url,'POST',json,successFx);
	return false;
}

function addItem(event){
    var b,q;

    b = $("#order-form input[name=barcode]").val();
    q = $("#order-form input[name=quantity]").val();
    console.log(b);
    console.log(q);
    var itembody = {
        barcode : b,
        quantity : q
    };
    data.push(itembody);
    $("#order-form input[name=barcode]").val("");
    $("#order-form input[name=quantity]").val("");
    displayOrderList();
}

function displayOrderList(){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	var j = 1;
	for(var i in data){
		var e = data[i];
		var f= j-1;
		var buttonHtml = '<button type="button" class="btn btn-group btn-sm btn-link" onclick="deleteItem(' + f + ')"><span class="material-icons">remove</span></button>';
//		var buttonHtml = ' <button onclick="displayEditBrand(' + e.brandId + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + j + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml +'</td>'
		+ '</tr>';
		j++;
        $tbody.append(row);
	}
}
function deleteItem(id){
    data.splice(id,1);
    displayOrderList();
}
function init(){
    $('#create-order').click(createOrder);
	$('#add-item').click(addItem);
	$('#refresh-data').click(displayOrderList);
}

$(document).ready(init);
$(document).ready(displayOrderList);