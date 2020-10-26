<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
//子页面JS方法
function getCloseModuleWin(){  
    console.log(window.parent.closeModuleWin()); 
}
</script>
</head>
<body style="text-align: center">
<h1> 操作成功！</h1>
<%
	if(request.getAttribute("balance")!=null){
		float balance = (Float)request.getAttribute("balance");
%>
<h3>余额为：<%=balance %></h3>
<%
	}
%>

<a href="trans/<%=request.getAttribute("type") %>"><input type="button" value="退 出 " style="width:10%;height:35px;" /></a>
</body>
</html>