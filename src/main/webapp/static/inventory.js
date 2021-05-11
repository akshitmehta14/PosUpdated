
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	//Set the values to update
	$('#add-inventory-modal').modal('toggle');
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl();
    successFx = function(response){
            getInventoryList();
        }
    ajaxRequest(url,'POST',json,successFx);

	return false;
}

function updateInventory(event){
	$('#edit-inventory-modal').modal('toggle');
	//Get the ID
	var barcode = $("#inventory-edit-form input[name=barcode]").val();
	var url = getInventoryUrl() + "/" + barcode;

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);
	console.log(json);
    successFx = function(response){
        getInventoryList();
    }
    ajaxRequest(url,'PUT',json,successFx);

	return false;
}


function getInventoryList(){
	var url = getInventoryUrl();
	successFx = function(data){
            displayInventoryList(data);
        }
    ajaxRequest(url,'GET',1,successFx);
}



// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(event){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getInventoryUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button type="button" class="btn btn-group btn-sm btn-link" onclick="displayEditInventory(\'' + e.barcode + '\')"><span class="material-icons">edit</span></button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditInventory(barcode){
	var url = getInventoryUrl() + "/" + barcode;
	console.log(url);
	console.log(barcode);
    successFx = function(data){
            displayInventory(data);
        }
    ajaxRequest(url,'GET',1,successFx);

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#InventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}

function displayInventory(data){
console.log(data.barcode);
	$("#inventory-edit-form input[name=barcode]").val(data.barcode);
	$('#edit-inventory-modal').modal('toggle');
}

function addInventoryModal(event){
    $('#add-inventory-modal').modal('toggle')
}

//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventoryModal);
	$('#adds-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName);
}

$(document).ready(init);
$(document).ready(getInventoryList);

