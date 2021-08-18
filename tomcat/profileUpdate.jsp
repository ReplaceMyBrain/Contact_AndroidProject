<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String no = request.getParameter("no");
	String name = request.getParameter("name");
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
	
	    String A = "update people set pName=?, pTel=?, pImg=?, pGroup=?, pFavorite=?  where pNo=?";
	
		    ps = conn_mysql.prepareStatement(A);
		    ps.setString(1, name);
		    ps.setString(2, tel);
		    ps.setString(3, img);
		    ps.setString(4, group);
		    ps.setString(5, favorite);
 	  	    ps.setString(6, no);
	
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

