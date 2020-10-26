<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>余额查询界面</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/dlgform.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/icon.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/easyui.css" />           
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/easyui-lang-zh_CN.js"></script>
    <style>
        h1
        {
            color: rgb(128, 128, 128);
            text-align: center;
        }
        form
        {
            width: 400px;
            min-width: 400px;
            position: absolute;
            left: 40%;
            top: 15%;
            margin: 0;
            padding: 30px;
            background-color: white;
            border: 2px solid rgba(128, 128, 128, 0.2);
            border-radius: 10px;
        }

            form div
            {
                margin-bottom: 10px;
            }
    </style>
</head>
<body style="text-align: center">
<%
	String msg="";
	if(request.getAttribute("msg")!=null){
		msg = (String)request.getAttribute("msg");
		
	}
%>
<form action="${pageContext.request.contextPath }/QueryBalanceServlet" method="post">
	<div>
        <h1>余额查询</h1>
    </div>
    <div>
        <input class="easyui-textbox" name="account_num" data-options="iconCls:'icon-man',iconWidth:30,iconAlign:'left',required:true,prompt:'账户'" style="width:100%;height:35px;" />
    </div>
    <div>
        <input class="easyui-passwordbox" name="password" data-options="iconWidth:30,iconAlign:'left',required:true,prompt:'密码'" style="width:100%;height:35px;" />
    </div>
    <div>
        <input class="easyui-linkbutton" type="submit" value="查询" style="width:100%;height:35px;background: #3d83fa;color: #fefefe;" />
    </div>
	<div>
    	<h3><font color="red"><%=msg %></font></h3>
    </div>
</form>
</body>
</html>