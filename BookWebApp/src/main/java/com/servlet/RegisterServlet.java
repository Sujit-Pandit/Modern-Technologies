package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query="INSERT INTO bookdata(BookName,BookEdition,BookPrice) VALUES(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		//GET Book Info
		String BookName=req.getParameter("bookName");
		String BookEdition=req.getParameter("bookEdition");
		float BookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		//Load JDBC Driver
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		//generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","riya");
			PreparedStatement ps=con.prepareStatement(query);){
			ps.setString(1,BookName);
			ps.setString(2, BookEdition);
			ps.setFloat(3, BookPrice);
			int count=ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record Registered</h2>");
			}else {
				pw.println("<h2>Record Not-Registered</h2>");
			}
		}catch(SQLException se) {
				se.printStackTrace();
				pw.println("<h1>"+se.getMessage()+"</h2>");
			}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}
}
