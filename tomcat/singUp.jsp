<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");
	String pw = request.getParameter("pw");
	String tel = request.getParameter("tel");

		
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
	
	    String A = "insert into user (uEmail,uPw,uTel) values (?,?,?)";
	
		    ps = conn_mysql.prepareStatement(A);
		    ps.setString(1, email);
		    ps.setString(2, pw);
		    ps.setString(3, tel);
	
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

