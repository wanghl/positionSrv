<#list items as item>
	<tr value='${item.tagId!""}'>
	<td>${item.tagId!""} </td>
	<td>${item.readerId!""} </td>
	<td>${item.newTrigger!""} </td>
	<td>${item.oldTrigger!""} </td>
	<td><#if (item.batteryValue ==0)>��������<#else>��������</#if> </td>
	<td>${item.scanTimes!""} </td>
	<td>${item.updatetime?string("yyyy-MM-dd HH:mm:ss")!""} </td>
	</tr>
</#list>