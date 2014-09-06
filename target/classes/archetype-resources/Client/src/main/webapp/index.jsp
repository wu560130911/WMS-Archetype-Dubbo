#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<shiro:notAuthenticated>
	<%
		response.sendRedirect("Blogin.jsp");
	%>
</shiro:notAuthenticated>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>即聊</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="即时聊天,即聊">
	<meta http-equiv="description" content="即时聊天系统页面">
	<link rel="stylesheet" type="text/css" href="resources/extjs/resources/css/ext-all-neptune.css"  />
	<link rel="stylesheet" type="text/css" href="css/index.css">
  </head>
  
  <body class="body_theme_style">
	<script type="text/javascript" src="resources/jquery/jquery-2.1.0.js"></script>
	<script type="text/javascript" src="resources/extjs/bootstrap.js"></script>
	<script type="text/javascript" src="resources/extjs/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/Chart.js"></script>
	
	<script type="text/javascript">
  	Ext.onReady(function(){
  		Ext.create('Chart.view.MainWindow').show();
  	});
  </script>
  </body>
</html>
