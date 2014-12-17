<#list items as item>
	<tr value='${item.port!""}'><td> ${item_index +1}</td>
	<td>${item.ip!""} </td>
	<td>${item.port!""} </td>
	<td>${item.readerId!""} </td>
	<td>${item.readerName!""} </td>
	<td>${item.area!""} </td>
	<td> <#if (item.heartbeat_time?exists)> ${item.heartbeat_time?string("yyyy-MM-dd HH:mm:ss")}<#else> </#if> </td>
	<td>${item.updatetime?string("yyyy-MM-dd HH:mm:ss")!""} </td>
	</tr>
</#list>