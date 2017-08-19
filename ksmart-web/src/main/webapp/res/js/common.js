var pub_param;

function getRemoteData(url, params) {
   //确认提示
   var progress = dialog(progressOption);
   var result;
   $.ajax({
       type: 'POST',
       datatype: 'json',
       url: url,
       async: false,
       data: params,
       beforeSend: function () {
           progress.showModal()
       },
       complete: function () {
           progress.close().remove()
       },
       success: function (data) {
           if (typeof data === 'string') {
               data = JSON.parse(data)
           }
           progress.close().remove();
           if (data.statusCode == "200") {
        	   result= data;
           } else if (data.statusCode == "300") {
               onException(data);
           }
       }
   });
   return result;
}