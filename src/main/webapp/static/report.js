function getReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	console.log(baseUrl);
	return baseUrl + "/api/report/sales";
}
function HideTable()
{
  $("#sales-table").hide();
}
function SalesReport(event){

	var $form = $("#report-form");
	var json = toJson($form);
	console.log(json);
	var url = getReportUrl();
    $("#sales-table").show();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(data) {
	   		displayReportData(data);
	   },
	   error: handleAjaxError
	});

	return false;
}
function displayReportData(data){

	var $tbody = $('#sales-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		console.log(data[i]);
		console.log(i);
		var row = '<tr>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function init(){
	$('#sales-report').click(SalesReport);
}

$(document).ready(init);
$(document).ready(HideTable);
