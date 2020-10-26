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

<title>客户信息管理</title>

<script type="text/javascript">
	$(function(){
		var account_num=<%=session.getAttribute("username") %>
		$("#customer").datagrid({
			loadMsg:"数据加载中，请等待...",
			iconCls:"icon-man",
			title:"客户信息管理",
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
			url:"CustomerServlet?caozuo=khinformation&account_num="+account_num
		})
		
	});
</script>



</head>
 <body style="margin-top:10px; margin-left: 4px; margin-right:4px;height:97%;">    
     <h1 style="text-align:center;">客户信息管理</h1>
     <!-- 明细及工具栏信息开始 -->	 
 	 <table id="customer" class="easyui-datagrid" style="height:94%" data-options="toolbar:'#tb'">
    	<thead>
    		<tr>   			
    			<th data-options="field:'customer_id',width:100,hidden:true">编 号</th>								
				<th data-options="field:'name_cn',width:100,align:'center'">中文名</th>
				<th data-options="field:'name_en',width:100,align:'center'">英文名</th>
				<th data-options="field:'sex',width:100,align:'center'">性 别</th>					
				<th data-options="field:'person_id',width:200,align:'center'">身份证号</th>	
				<th data-options="field:'email',width:200,align:'center'">电子邮件</th>	
				<th data-options="field:'phone',width:120,align:'center'">手机号码</th>
				<th data-options="field:'telphone',width:120,align:'center'">家庭电话</th>		
				<th data-options="field:'nationality',width:50,align:'center'">国 籍</th>
				<th data-options="field:'province',width:50,align:'center'">省 份</th>
				<th data-options="field:'city',width:50,align:'center'">城 市</th>
				<th data-options="field:'address',width:200,align:'center'">住址</th>
				<th data-options="field:'poscode',width:80,align:'center'">邮 编</th>
				<th data-options="field:'regdate',width:200,align:'center'">注册时间</th>
				<th data-options="field:'regplace',width:200,align:'center'">注册地点</th>
    		</tr>
    	</thead>
     </table>	 
   </body>
</html>