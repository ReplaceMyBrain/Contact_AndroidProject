<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String email = request.getParameter("email");

	String url_mysql = "jdbc:mysql://localhost/contact?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
	
	int result = 0;   // 입력할때만 사용 
   	int count = 0;   // 값받아올때 사용


    PreparedStatement ps = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        ResultSet rs = null;
        
        String query = "SELECT pGroup, count(pName) FROM people where uEmail=? group by pGroup ";

       		ps = conn_mysql.prepareStatement(query);
				
		ps.setString(1, email);
		rs =ps.executeQuery();

%>
		{ 
  			"people"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"group" : "<%=rs.getString(1) %>",
			"count" : "<%=rs.getString(2) %>"
			}

<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
