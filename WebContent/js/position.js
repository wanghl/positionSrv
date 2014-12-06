function callServlet(url ,paras, returnType)
{
	var p3  ;
	if ( arguments [2] == undefined)
	{
		p3 = 'json' ;
	}
	else
	{
		p3 = returnType ;
	}

	var value ;
	$.ajax({
		url: url,
		type: 'POST',
		dataType: p3,
		async: false,
		data: paras,
	     error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	 alert( errorThrown ) ;
         },
		success: function(data){
			value  = data ;
		}
	});

	return value; 
	
}
