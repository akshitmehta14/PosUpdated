
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	console.log(baseUrl);
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS

function getBrandList(){
	var url = getBrandUrl();
	let successFx = function(data) {
    	    displayBrandList(data);
    	}
    ajaxRequest(url,'GET',1,successFx);
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(event){
	var file = $('#brandFile')[0].files[0];
	console.log(file);
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
	var url = getBrandUrl();

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

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button type="button" class="btn btn-group btn-sm btn-link" type="button" class="btn btn-group btn-sm btn-primary" onclick="displayEditBrand(' + e.brand_id + ')"><span class="material-icons">edit</span></button>';
		var row = '<tr>'
//		+ '<td>' + e.brand_id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditBrand(id){
	var url = getBrandUrl() + "/" + id;
	console.log(id);
	let successFx = function(data) {
	    displayBrand(data);
	}
	ajaxRequest(url,'GET',1,successFx);

}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
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
	var $file = $('#brandFile');
	var fileName = $file.val();
	fileName = fileName.substr(12);
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brand-modal').modal('toggle');
}

function displayBrand(data){
    $("#brand-edit-form input[name=id]").val(data.brand_id);
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$('#edit-brand-modal').modal('toggle');

}

function updateBrand(){
	$('#edit-brand-modal').modal('toggle');
	id = $("#brand-edit-form input[name=id]").val();
	var url = getBrandUrl() + "/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

    successFx = function(response){
                getBrandList();
            }

    ajaxRequest(url,'PUT',json,successFx);


	return false;
}
function addBrandForm(event){
    $('#add-brand-modal').modal('toggle');
}
function addBrand(event){
	//Set the values to update
	$('#add-brand-modal').modal('toggle');
	var $form = $("#brand-form");
	var json = toJson($form);
	var url = getBrandUrl();
	successFx = function(response){
                    getBrandList();
                }
	ajaxRequest(url,'POST',json,successFx);


	return false;
}
//INITIALIZATION CODE
function init(){
	$('#add-brand').click(addBrandForm);
	$('#adds-brand').click(addBrand);
    $('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName);
}

$(document).ready(init);
$(document).ready(getBrandList);

