<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html style="height:100%">
<head>
<base href="<%=basePath %>">
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/dlgform.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/icon.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/easyui.css" />           
<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/easyui-lang-zh_CN.js"></script>

<title>查询交易记录</title>

<script type="text/javascript">
	$(function(){
		var condition=<%=session.getAttribute("username") %>
		condition="account_num='"+condition+"'";
		$("#record").datagrid({
			loadMsg:"数据加载中，请等待...",
			iconCls:"icon-man",
			title:"<%=session.getAttribute("username") %>的交易记录",
			collapsible:true,
			nowrap:true,
			striped:true,
			rownumbers:true,
			pagination:true,
			singleSelect:true,
			autoRowHeight:false,
			fitColumn:false,
			pageSize:10,
			pageList:[10,20,30,40],
			url:"QueryRecordServlet?condition="+condition
		})
	});
		
</script>



</head>
 <body style="margin-top:10px; margin-left: 4px; margin-right:4px;height:97%;">    
     <h1 style="text-align:center;"><font color="red"><%=session.getAttribute("username") %></font>账户交易记录</h1>
     <!-- 明细及工具栏信息开始 -->	 
 	 <table id="record" class="easyui-datagrid" style="height:94%" data-options="toolbar:'#tb'">
    	<thead>
    		<tr>   			
    			<th data-options="field:'record_id',width:100,align:'center'">交易编号</th>								
				<th data-options="field:'account_num',width:100,align:'center'">账 户</th>
				<th data-options="field:'op_type',width:120,align:'center'">操 作</th>
				<th data-options="field:'transaction_amount',width:200,align:'center'">交易金额</th>					
				<th data-options="field:'free',width:120,align:'center'">费 率</th>	
				<th data-options="field:'balance',width:120,align:'center'">余 额</th>	
				<th data-options="field:'transaction_time',width:250,align:'center'">交易时间</th>
				<th data-options="field:'transaction_place',width:300,align:'center'">交易地点</th>	
				<th data-options="field:'inaccount_num',width:300,align:'center'">转账账户</th>		
    		</tr>
    	</thead>
     </table>	 
     
   </body>
</html>