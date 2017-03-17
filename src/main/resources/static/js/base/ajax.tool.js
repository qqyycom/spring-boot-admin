var AjaxTool = function() {
	
	function checkUrl(url) {
		var ctxObj=$("input[name='ctx']");
		if(ctxObj.length>0){
			var ctx=ctxObj.val();
			url=ctx+"/"+url;
		}
		return url;
	}

	var get = function(url, data, successFunction) {
		var promise = $.ajax({
			url : checkUrl(url),
			data : data,
			dataType : "json",
			type : "get"
		});
		promise.then(function(response) {
			if(response.code && response.code == '100'){
				Toast.show("提醒",response.message);
			}else{
				if (successFunction != null) {
					successFunction(response);
				}
			}
		});
	}

	var post = function(url, data, successFunction) {
		var promise = $.ajax({
			url : checkUrl(url),
			data : data,
			dataType : "json",
			type : "post"
		});
		promise.then(function(response) {
			if(response.code && response.code == '100'){
				Toast.show("提醒",response.message);
			}else{
				if (successFunction != null) {
					successFunction(response);
				}
			}
		});
	}
	
	var html = function(url, data, successFunction) {
		var promise = $.ajax({
			url : checkUrl(url),
			data : data,
			dataType : "html",
			type : "get"
		});
		promise.then(function(html) {
			try{
	            var jsonObject = JSON.parse(html);
	            if(jsonObject.code && jsonObject.code == '100'){
					Toast.show("提醒",jsonObject.message);
				}
	    	}catch(e){
	    		if (successFunction != null) {
					successFunction(html);
				}
	    	}
		});
	}

	return {
		get : function(url, data, successFunction) {
			get(url, data, successFunction);
		},
		post : function(url, data, successFunction) {
			post(url, data, successFunction);
		},
		html : function(url,data,successFunction){
			html(url, data, successFunction);
		}
	}
}();
