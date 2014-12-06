<#list items as item>
	<tr value='${item.physicalid!""}'><td> ${item_index +1}</td>
	<td>${item.physicalid!""} </td>
	<td>${item.cardid!""} </td>
	<td>${item.positionx!""} </td><td>${item.positiony!""}</td><td>${item.positionz!""} </td>
	<td>${item.updatetime?string("yyyy-MM-dd HH:mm:ss")!""} </td>
	</tr>
</#list>