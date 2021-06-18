<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	String no = request.getParameter("no");

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
        
        String query = "SELECT pNo, pName, pTel, pImg, pGroup, pFavorite  FROM people WHERE pNo=?";

       		ps = conn_mysql.prepareStatement(query);
				
		ps.setString(1, no);
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
			"no" : "<%=rs.getString(1) %>",
			"name" : "<%=rs.getString(2) %>",
			"tel" : "<%=rs.getString(3) %>",
			"img" : "<%=rs.getString(4) %>",
			"group" : "<%=rs.getString(5) %>",
			"favorite" : "<%=rs.getString(6) %>"
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
