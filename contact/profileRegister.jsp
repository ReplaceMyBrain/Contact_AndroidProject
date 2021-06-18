<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");	
	String name = request.getParameter(name");
	String tel = request.getParameter("tel");
	String img = request.getParameter("img");
	String group = request.getParameter("group");
	String favorite = request.getParameter("favorite");


		
//------
	String url_mysql = "jdbc:mysql://localhost/contact?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	int result = 0; // 입력 확인 

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
		Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into user (uEmail, pName, pTel, pImg, pGroup, pFavorite) values (?,?,?,?,?,?)";
	
		    ps = conn_mysql.prepareStatement(A);
 		    ps.setString(1, email);
		    ps.setString(2, name);
		    ps.setString(3, tel);
		    ps.setString(4, img);
		    ps.setString(5, group);
		    ps.setString(6, favorite);
 	  	   
	
		    result = ps.executeUpdate();
%>
		{
			"result" : "<%=result%>"
		}

<%		
	    conn_mysql.close();
	} 
	catch (Exception e){
%>
		{
			"result" : "<%=result%>"
		}
<%		
	    e.printStackTrace();
	} 
	
%>

