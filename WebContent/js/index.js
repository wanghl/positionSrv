function getGPSInfo() {
	var gpsinfo = callServlet("/mcm/gpsinfo", {
		callType : "getGpsInfo"
	});
	if (gpsinfo.status.indexOf('Õý³£') >= 0) {
		$('#latitude').text(gpsinfo.latitude);
		$('#longitude').text(gpsinfo.longitude);
		$('#speed').text(gpsinfo.speed);
		$('#updatetime').text(gpsinfo.updatetime);

	}
	$('#gps_status').text(gpsinfo.status);
}
/*
function getLogicalDataList()
{
	var v =  callServlet("/mcm/logicaldata", {action : "logicalData"}, "html");
	$('#detectionTable tbody').append(v.split("&")[0]) ;
	$('#noDetectionTable tbody').append( v.split("&")[1] ) ;

}
*/

function getLogicalDataList()
{
	var v =  callServlet("/mcm/logicaldata", {action : "getAlarmlist"}, "html");
	$('#alarmTable tbody tr').remove();
	if( typeof(v.error_code) == "undefined" )
	{
		$('#alarmTable tbody').append(v) ;
	}
	else
	{
		alert( v.error_message) ;
	}
	

}
