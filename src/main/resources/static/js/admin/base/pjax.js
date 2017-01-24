var Pjax = function() {

	var init = function(aSelector, containerSelector, baseUrl, defaultUrl,
			startFunction, endFunction) {
		var $a = $(aSelector);
		$a.unbind("click");
		$a.bind("click", function() {
			var $current = $(this);
			if (startFunction) {
				startFunction($current);
			}
			var url = $(this).data("url");
			if(!url){
				return;
			}
			if(url==''){
				url=defaultUrl;
			}
			var promise = $.ajax({
				url : checkUrl(url),
				type : "get",
				dataType : "html"
			});
			promise.then(function(html) {
				$(containerSelector).html(html);
				pushPage(url, baseUrl);
				if (endFunction) {
					endFunction($current);
				}
			});
		});
	}

	var refresh = function(containerSelector, baseUrl, defaultUrl, endFunction) {
		var url=getCurrentUrl(baseUrl);
		if(url==''){
			url=defaultUrl;
		}
		var navUrl=getNavUrl(url);
		var requestUrl=getRequestUrl(url);
		var promise = $.ajax({
			url : checkUrl(requestUrl),
			type : "get",
			dataType : "html"
		});
		promise.then(function(html) {
			$(containerSelector).html(html);
			if (endFunction) {
				if(navUrl){
					endFunction(navUrl);
				}else{
					endFunction(requestUrl);
				}
			}
		});
	}

	var onpopstate = function(containerSelector, baseUrl, defaultUrl, endFunction) {
		window.addEventListener("popstate", function() {
			var currentState = history.state;
			if (currentState) {
				var index = currentState.indexOf(baseUrl);
				currentState = currentState.substring(index + baseUrl.length + 1, currentState.length);
				currentState = $.base64.decode(currentState);
				var requestUrl=getRequestUrl(currentState);
				var navUrl=getNavUrl(currentState);
				
				var promise = $.ajax({
					url : checkUrl(requestUrl),
					type : "get",
					dataType : "html"
				});
				promise.then(function(html) {
					$(containerSelector).html(html);
					if (endFunction) {
						if(navUrl){
							endFunction(navUrl);
						}else{
							endFunction(requestUrl);
						}
					}
				});
			} else {
				var currentUrl = window.location.href;
				var index = currentUrl.indexOf(baseUrl);
				if (index >= 0) {
					currentUrl = currentUrl
							.substring(0, index + baseUrl.length);
				}
				window.location.href = currentUrl;
			}
		});
	}
	
	var add = function(aSelector, containerSelector, baseUrl, defaultUrl,startFunction, endFunction){
		var $a = $(aSelector);
		$a.unbind("click");
		$a.bind("click", function() {
			var $current = $(this);
			if (startFunction) {
				startFunction($current);
			}
			var url = $(this).data("url");
			if(!url){
				return;
			}
			if(url==''){
				url=defaultUrl;
			}
			var promise = $.ajax({
				url : checkUrl(url),
				type : "get",
				dataType : "html"
			});
			promise.then(function(html) {
				$(containerSelector).html(html);
				var navUrl=getCurrentUrl(baseUrl);
				if(navUrl){
					url=navUrl+"##"+url;
				}
				pushPage(url, baseUrl);
				if (endFunction) {
					endFunction($current);
				}
			});
		});
	}
	
	function getCurrentUrl(baseUrl){
		var url = window.location.href;
		var index = url.indexOf(baseUrl);
		url = url.substring(index + baseUrl.length + 1, url.length);
		url = $.base64.decode(url);
		return url;
	}
	
	function getRequestUrl(url){
		//截取最后一段url，加载页面
		var lastIndex=url.lastIndexOf("##");
		if(lastIndex>=0){
			url=url.substring(lastIndex+2);
		}
		return url;
	}
	
	function getNavUrl(url){
		//截取第一级url，加上导航的选中样式
		var index=url.indexOf("##");
		var navUrl = null;
		if(index>=0){
			navUrl=url.substring(0,index);
		}
		return navUrl;
	}

	function pushPage(url, baseUrl) {
		url = $.base64.encode(url, true);
		var currentUrl = window.location.href;
		var index = currentUrl.indexOf(baseUrl);
		currentUrl = currentUrl.substring(0, index + baseUrl.length);
		currentUrl = currentUrl + "?" + url;
		history.pushState(currentUrl, null, currentUrl);
	}
	
	function checkUrl(url) {
		var ctxObj=$("input[name='ctx']");
		if(ctxObj.length>0){
			var ctx=ctxObj.val();
			url=ctx+"/"+url;
		}
		return url;
	}
	
	return {
		init : function(aSelector, containerSelector, baseUrl, defaultUrl, startFunction,
				endFunction) {
			init(aSelector, containerSelector, baseUrl, defaultUrl, startFunction,
					endFunction);
		},
		add : function(aSelector, containerSelector, baseUrl, defaultUrl, startFunction,
				endFunction) {
			add(aSelector, containerSelector, baseUrl, defaultUrl, startFunction,
					endFunction);
		},
		refresh : function(containerSelector, baseUrl, defaultUrl, endFunction) {
			refresh(containerSelector, baseUrl, defaultUrl, endFunction);
		},
		onpopstate : function(containerSelector, baseUrl, defaultUrl, endFunction) {
			onpopstate(containerSelector, baseUrl, defaultUrl, endFunction);
		},
		redirectAdd : function(aSelector){
			add(aSelector, ".page-content", "admin/home","defaultHomePage");
		},
		redirectSave : function(){
			add(".save-button", ".page-content", "admin/home","defaultHomePage");
		},
		redirectUpdate : function(){
			add(".update-button", ".page-content", "admin/home","defaultHomePage");
		}
	}
}();