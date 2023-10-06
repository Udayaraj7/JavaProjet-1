package com.reg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistationServlet extends HttpServlet {
	
        
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String reupwd=request.getParameter("re_pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher  dispatcher =null;
		Connection con=null;
//		PrintWriter out =response.getWriter();
//		out.println(uname+" "+uemail+"  "+upwd+"  "+umobile);
		
		if(uname==null || uname.equals(""))
		{
			request.setAttribute("status", "invalidName");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if(uemail==null || uemail.equals(""))
		{
			request.setAttribute("status", "invalidEmail");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if(upwd==null || upwd.equals(""))
		{
			request.setAttribute("status", "invalidPassword");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if(!upwd.equals(reupwd))
			{
			request.setAttribute("status", "invalidConfirmPassword");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
			}
			
		
		else if(umobile==null || umobile.equals(""))
		{
			request.setAttribute("status", "invalidMobile");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		else if(umobile.length()>10)
		{
			request.setAttribute("status", "invalidMobileLength");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
		
		else {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/reg?useSSL=false","root","root");
			PreparedStatement ps=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,? )");
			ps.setString(1, uname);
			ps.setString(2, upwd);
			ps.setString(3, uemail);
			ps.setString(4, umobile);
			
			int rowcount=ps.executeUpdate();
			dispatcher=request.getRequestDispatcher("registration.jsp");
			
			if(rowcount >0)
			{
				request.setAttribute("status", "success");
			}
			else
			{
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

}
}
