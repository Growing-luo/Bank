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

<title>职员信息管理</title>

<script type="text/javascript">
	var actionflag;
	$(function(){
		$("#employee").datagrid({
			loadMsg:"数据加载中，请等待...",
			iconCls:"icon-man",
			title:"职员信息管理",
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
			url:"EmployeeServlet?caozuo=initdatagrid"
		});
		
		//省市联动
		$("#province").combobox({
    		url:"Test?caozuo=initProvince",
    		valueField:"pid",
    		textField:"name",
    		panelHeight:"auto",
    		prompt:"请选择省份",
    		editable:false,
    		onChange:function(){
    			$("#city").combobox({
    				url:"Test?caozuo=initCity&province="+encodeURI($("#province").combobox("getValue")),
    				valueField:"cid",
    	    		textField:"name",
    				panelHeight:"auto",
    				prompt:"请选择城市",
    	    		editable:false,
    			});
    		}
    	});
		
		
		//初始化下拉列表：性别
		var data = [
	            {'text' : '男', 'value' : '男'},
	            {'text' : '女', 'value' : '女'}
			];
	 
		$('#sex').combobox({
			textField : 'text',
			valueField : 'value',
			panelHeight : 'auto',
			data : data,
			prompt:"请选择性别",
			editable:false
		});
		
		//添加
		$("#add").click(function(){
			actionflag="add";
			$("#dlgemployee").dialog("open").dialog("center").dialog("setTitle","添加职员信息");
			$("#dlgemployee").form("clear");
		});
		
		//保存
		$("#save").click(function(){
			if($("#name_cn").textbox("getValue")==""){
				$.messager.alert("提示","姓名不能为空！","info");
				return;
			}
			if($("#sex").combobox("getValue")==""){
				$.messager.alert("提示","未选择性别！","info");
				return;
			}
			if($("#person_id").textbox("getValue")==""){
				$.messager.alert("提示","未填写身份证号！","info");
				return;
			}
			if($("#email").textbox("getValue")==""){
				$.messager.alert("提示","未填写电子邮件！","info");
				return;
			}
			if($("#phone").textbox("getValue")==""){
				$.messager.alert("提示","未填写手机号码！","info");
				return;
			}
			if(actionflag=="add"){//add
				$.post("EmployeeServlet?caozuo=add",
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
			}else{//edit
				$.post("EmployeeServlet?caozuo=edit&employee_id="+employee_id,
				$("#fm").serialize(),  //{"sno":"20190009","sname":"wang"}
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
		
		$("#edit").click(function(){
			actionflag="edit";
			var row=$("#employee").datagrid("getSelected");  //获得datagrid选中的行
			if(row){
				employee_id=row.employee_id;
				$("#dlgemployee").dialog("open").dialog("center").dialog("setTitle","编辑职员信息");
				$("#name_cn").textbox("setValue",row.name_cn);
				$("#name_en").textbox("setValue",row.name_en);
				$("#sex").textbox("setValue",row.sex);
				$("#person_id").textbox("setValue",row.person_id);
				$("#email").textbox("setValue",row.email);
				$("#phone").textbox("setValue",row.phone);	
				$("#telphone").textbox("setValue",row.telphone);	
				$("#nationality").textbox("setValue",row.nationality);	
				$("#province").textbox("setValue",row.province);	
				$("#city").textbox("setValue",row.city);
				$("#address").textbox("setValue",row.address);
				$("#poscode").textbox("setValue",row.poscode);
				$("#regplace").textbox("setValue",row.regplace);
				$("#branch_bank").textbox("setValue",row.branch_bank);
			}else{
				$.messager.alert("提示","请选择要编辑的职员信息！","info");
				return;
			}
		});
		
		$("#delete").click(function(){
			var row=$("#employee").datagrid("getSelected");  //获得datagrid选中的行
			if(row=="" || row==null){
				$.messager.alert("提示","请选择要删除的职员！","info");
				return;
			}
			$.post("EmployeeServlet?caozuo=delete",
			{"employee_id":row.employee_id},
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
			if($("#qperson_id").textbox("getValue")!=""){
				condition="person_id='"+$("#qperson_id").textbox("getValue")+"'";
			}			
			$("#employee").datagrid("reload",{"condition":condition});			
		});
		
		
		function initdatagrid(){
			$("#employee").datagrid("reload");
		}
		
	});
</script>



</head>
 <body style="margin-top:10px; margin-left: 4px; margin-right:4px;height:97%;">    
     <h1 style="text-align:center;">职员信息管理</h1>
     <!-- 明细及工具栏信息开始 -->	 
 	 <table id="employee" class="easyui-datagrid" style="height:94%" data-options="toolbar:'#tb'">
    	<thead>
    		<tr>   			
    			<th data-options="field:'employee_id',width:100,hidden:true">编 号</th>								
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
				<th data-options="field:'branch_bank',width:200,align:'center'">所在单位</th>
    		</tr>
    	</thead>
     </table>	 
     <div id="tb" style="padding:5px;height:auto">
        <!-- 按钮部分 -->
		<div style="margin-bottom:5px">
			<a id="add" class="easyui-linkbutton" iconCls="icon-add"  plain="true" >增 加</a>	
			<a id="edit" class="easyui-linkbutton" iconCls="icon-edit"  plain="true" >编 辑</a>						
			<a id="delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删 除</a>
			<a id="print" class="easyui-linkbutton" iconCls="icon-print" plain="true">打 印</a>
		</div>
		<!-- 查询部分 -->
		<div>				
			&nbsp;&nbsp;身份证号&nbsp;<input required class="easyui-textbox" id="qperson_id" style="width:200px">		   
			&nbsp;&nbsp;<a id="query" class="easyui-linkbutton" iconCls="icon-search" plain="true">查 询</a>						
		</div>
	 </div> 
     <!--  点击增加按钮弹出的对话框开始  -->
     <div id="dlgemployee" class="easyui-dialog" style="width:360px;height:400px;padding:10px 10px" closed="true" buttons="#dlg-buttons">
    	<div class="ftitle">职员信息</div>
    	<form id="fm" method="post" novalidate>
	        <div class="fitem">
	            <label>中文名:</label>
	            <input class="easyui-textbox" id="name_cn" name="name_cn"/>
	        </div>
	        <div class="fitem">
	            <label>英文名:</label>
	            <input class="easyui-textbox" id="name_en"  name="name_en"/>
	        </div>
	        <div class="fitem">
	        	<label>性 别:</label>
	        	<input class="easyui-combobox" id="sex" name="sex"/>
	        </div>
	        <div class="fitem">
	        	<label>身份证号:</label>
	        	<input class="easyui-textbox" id="person_id" name="person_id"/>
	        </div>
	        <div class="fitem">
	        	<label>电子邮件:</label>
	        	<input class="easyui-textbox" id="email" name="email"/>
	        </div>	       
	        <div class="fitem">
	        	<label>手机号码:</label>
	        	<input class="easyui-textbox" id="phone" name="phone"/>
	        </div>        
	        <div class="fitem">
	        	<label>家庭电话:</label>
	        	<input class="easyui-textbox" id="telphone" name="telphone"/>
	        </div>
	        <div class="fitem">
	        	<label>国 籍:</label>
	        	<input class="easyui-textbox" id="nationality" name="nationality"/>
	        </div>
	        <div class="fitem">
	        	<label>省 份:</label>
	        	<input class="easyui-combobox" id="province" name="province"/>
	        </div>
	        <div class="fitem">
	        	<label>城 市:</label>
	        	<input class="easyui-combobox" id="city" name="city"/>
	        </div>
	        <div class="fitem">
	        	<label>住 址:</label>
	        	<input class="easyui-textbox" id="address" name="address"/>
	        </div>
	        <div class="fitem">
	        	<label>邮 编:</label>
	        	<input class="easyui-textbox" id="poscode" name="poscode"/>
	        </div>
	        <div class="fitem">
	        	<label>注册地点:</label>
	        	<input class="easyui-textbox" id="regplace" name="regplace"/>
	        </div>
	        <div class="fitem">
	        	<label>所在单位:</label>
	        	<input class="easyui-textbox" id="branch_bank" name="branch_bank"/>
	        </div>
		</form>
	 </div>
	 <div id="dlg-buttons">
		<a href="javascript:void(0)"  id="save" class="easyui-linkbutton"  iconCls="icon-ok" style="width:90px">保 存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="javascript:$('#dlgemployee').dialog('close')" style="width:90px">关 闭</a>
	 </div>
	 <!-- 点击增加按钮弹出的对话框结束 -->
   </body>
</html>