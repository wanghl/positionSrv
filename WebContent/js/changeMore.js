//2011-7-25 zhangying
(function(){
function load_script(xyUrl, callback){
    var head = document.getElementsByTagName('head')[0];
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = xyUrl;
    //�����jQuery��script���򷽷�
    script.onload = script.onreadystatechange = function(){
        if((!this.readyState || this.readyState === "loaded" || this.readyState === "complete")){
        	callback && callback();
        	
            // Handle memory leak in IE
            script.onload = script.onreadystatechange = null;
            if ( head && script.parentNode ) {
                head.removeChild( script );
            }
        }
    };
    // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
    head.insertBefore( script, head.firstChild );
}
function transMore(points,type,callback){
	var xyUrl = "http://api.map.baidu.com/ag/coord/convert?from=" + type + "&to=4&mode=1";
	var xs = [];
	var ys = [];
	var maxCnt = 20;//ÿ�η��͵�������
	var send = function(){
		var url = xyUrl + "&x=" + xs.join(",") + "&y=" + ys.join(",") + "&callback=callback";
	    //��̬����script��ǩ
	    load_script(url);
		xs = [];
		ys = [];
	}
    for(var index in points){
		if(index % maxCnt == 0 && index != 0){
			send();
		}
		
    	xs.push(points[index].lng);
    	ys.push(points[index].lat);
		if(index == points.length - 1){
			send();
		}
    }
    
}

window.BMap = window.BMap || {};
BMap.Convertor = {};
BMap.Convertor.transMore = transMore;
})();
