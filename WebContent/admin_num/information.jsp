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

<title>业务员账户管理</title>

<script type="text/javascript">
	var actionflag;
	$(function(){
		$("#account").datagrid({
			loadMsg:"数据加载中，请等待...",
			iconCls:"icon-man",
			title:"业务员账户信息管理",
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
			url:"AdminnumServlet?caozuo=initdatagrid"
		});
		
		
		
		//添加
		$("#add").click(function(){
			actionflag="add";
			$("#dlgaccount").dialog("open").dialog("center").dialog("setTitle","新建");
			$("#dlgaccount").form("clear");
		});
		
		//保存
		$("#save").click(function(){
			if($("#person_id").textbox("getValue")==""){
				$.messager.alert("提示","未填写身份证号！","info");
				return;
			}
			if($("#name_cn").textbox("getValue")==""){
				$.messager.alert("提示","未填写姓名！","info");
				return;
			}
			if($("#password").textbox("getValue")==""){
				$.messager.alert("提示","未填写密码！","info");
				return;
			}
			
			if(actionflag=="add"){//add
				$.post("AdminnumServlet?caozuo=add",
				$("#fm").serialize(),  //序列化{"sno":"20190009","sname":"wang"}
				function(data){   ///data:{"status":"error","reason":"数据库操作失败"}
					var value=eval("("+data+")");
					if(value.status=="success"){
						initdatagrid();
						$.messager.alert("提示","操作成功！","info");
						return;
					}else{
						$.messager.alert("提示","操作失败！原因："+value.reason,"info");
						return;
					}
				});
			}
		});
		
		$("#cancel").click(function(){
			var row=$("#account").datagrid("getSelected");  //获得datagrid选中的行
			if(row=="" || row==null){
				$.messager.alert("提示","请选择要删除的业务员账号！","info");
				return;
			}
			$.post("AdminnumServlet?caozuo=cancel",
			{"account_id":row.account_id},
			function(data){
				var value=eval("("+data+")");
				if(value.status=="success"){
					initdatagrid();
					$.messager.alert("提示","操作成功！","info");
					return;
				}else{
					$.messager.alert("提示","操作失败！原因："+value.reason,"info");
					return;
				}
			});
		});
		
		//查询
		$("#query").click(function(){
			//alert("ni");
			var condition="";
			if($("#qaccount_num").textbox("getValue")!=""){
				condition=$("#qaccount_num").textbox("getValue");
			}			
			$("#account").datagrid("reload",{"condition":condition});			
		});
		
		
		function initdatagrid(){
			$("#account").datagrid("reload");
		}
		
	});
</script>



</head>
 <body style="margin-top:10px; margin-left: 4px; margin-right:4px;height:97%;">    
     <h1 style="text-align:center;">业务员账户管理</h1>
     <!-- 明细及工具栏信息开始 -->	 
 	 <table id="account" class="easyui-datagrid" style="height:94%" data-options="toolbar:'#tb'">
    	<thead>
    		<tr>   			
    			<th data-options="field:'employee_id',width:100,hidden:true">编 号</th>								
				<th data-options="field:'admin_num',width:200,align:'center'">业务员账号</th>
				<th data-options="field:'name_cn',width:200,align:'center'">姓 名</th>
				<th data-options="field:'manage_num',width:300,align:'center'">经理号</th>
    		</tr>
    	</thead>
     </table>	 
     <div id="tb" style="padding:5px;height:auto">
        <!-- 按钮部分 -->
		<div style="margin-bottom:5px">
			<a id="add" class="easyui-linkbutton" iconCls="icon-add"  plain="true" >创 建</a>	
			<a id="cancel" class="easyui-linkbutton" iconCls="icon-print" plain="true">删 除</a>
		</div>
		<!-- 查询部分 -->
		<div>				
			&nbsp;&nbsp;账户号&nbsp;<input required class="easyui-textbox" id="qaccount_num" style="width:200px">		   
			&nbsp;&nbsp;<a id="query" class="easyui-linkbutton" iconCls="icon-search" plain="true">查 询</a>						
		</div>
	 </div> 
     <!--  点击增加按钮弹出的对话框开始  -->
     <div id="dlgaccount" class="easyui-dialog" style="width:360px;height:400px;padding:10px 10px" closed="true" buttons="#dlg-buttons">
    	<div class="ftitle">客户信息</div>
    	<form id="fm" method="post" novalidate>
	        <div class="fitem">
	            <label>身份证号:</label>
	            <input class="easyui-textbox" id="person_id" name="person_id"/>
	        </div>
	        <div class="fitem">
	            <label>姓名:</label>
	            <input class="easyui-textbox" id="name_cn"  name="name_cn"/>
	        </div>
	        <div class="fitem">
	            <label>密码:</label>
	            <input class="easyui-textbox" id="password"  name="password"/>
	        </div>
		</form>
	 </div>
	 <div id="dlg-buttons">
		<a href="javascript:void(0)"  id="save" class="easyui-linkbutton"  iconCls="icon-ok" style="width:90px">确认开户</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="javascript:$('#dlgaccount').dialog('close')" style="width:90px">取 消</a>
	 </div>
	 <!-- 点击增加按钮弹出的对话框结束 -->
   </body>
</html>