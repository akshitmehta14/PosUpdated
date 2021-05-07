
//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}


function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	console.log(response);
	nativeToast({
      message: response.message ,
      position: 'north',
      timeout: 0,
      type: 'error',
      closeOnClick: true
    });
//	var toastElList = [].slice.call(document.querySelectorAll('.toast'))
//    var toastList = toastElList.map(function (toastEl) {
//      return new bootstrap.Toast(toastEl, option)
//    });
//    bs4pop.alert(response.message,function(){},{
//      title:'Error',
//      hideRemove:true,
//      width: 500,
//      btns: [
//        {
//          label:'Okay',
//          onClick(){
//          if(cb){
//              return cb();
//            }
//          }
//        }
//      ]
//    });
	//alert(response.message);
//	$("<div title='Basic dialog'>Test message</div>").dialog();
//	'<div role="alert" aria-live="assertive" aria-atomic="true" class="toast" data-bs-autohide="false"><div class="toast-header"><img src="..." class="rounded me-2" alt="..."><strong class="me-auto">Error</strong><button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button></div><div class="toast-body">' + response.message + '</div></div>';
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}
