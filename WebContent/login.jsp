<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <title>中国银行</title>
    
	<link rel="stylesheet" href="css/icon.css" />
	<link rel="stylesheet" href="css/easyui.css" />
	<link rel="stylesheet" href="css/dlgform.css" />
    <link rel="shortcut icon" href="favicon.ico"> 
	<link href="css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/style.min.css?v=4.0.0" rel="stylesheet"><base target="_blank">
    
	<script type="text/javascript" src="js/jquery.min.js" ></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js" ></script>
	<script type="text/javascript" src="js/easyui-lang-zh_CN.js" ></script>
    
    <script type="text/javascript">
    	$(function(){
	    	var data = [
				{'text' : '未选择', 'value' : '0'},
	            {'text' : '客 户', 'value' : '1'},
	            {'text' : '业务员', 'value' : '2'},
	            {'text' : '经理', 'value' : '3'}
			];
	 
		    $('#logtype').combobox({
				textField : 'text',
				valueField : 'value',
				panelHeight : 'auto',
				data : data,
				prompt:"请选择登陆类型",
				editable:false
			})
		});
    </script>
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                
                <h1 class="logo-name"><img style="height: auto;width: 200px;" src="img/zgyh.jpg"></h1>

            </div>
            <br />
            <h3>欢迎使用 中国银行系统</h3>

            <form class="m-t" role="form" action="LoginServlet" method="post">
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="账户" required="">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="密码" required="">
                </div>
                <div class="form-group">
                	登陆类型：<input id="logtype" class="easyui-combobox" name="logtype" required="" style="width: 200px"> 
                </div>
                <br />
                <button type="submit" style="background:#d43c31" class="btn btn-primary block full-width m-b" >登 录</button>
                
                <%
					String msg="";
					if(request.getAttribute("msg")!=null){
					msg = (String)request.getAttribute("msg");
					}
				%>
				<h3><font color="red"><%= msg %></font></h3>

				<br>
                <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码</small></a> | <a href="#">开户</a>
                </p>

            </form>
        </div>
    </div>
</body>

</html>