package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
			log.trace("service({}) invoked.");
			
			// Step.1 전송파라미터 획득
			String userid = req.getParameter("userid");
			String passwd = req.getParameter("passwd");
			
			log.info("\t + userid: {}, passed: {}", userid, passwd);
			
			// Step.2 에코(echo)
			res.setCharacterEncoding("utf8");
			
			@Cleanup
			PrintWriter out = res.getWriter();
			
			out.println("<ul>");
			out.println("   <li>"+ userid+ "<li>");
			out.println("   <li>"+ passwd+ "<li>");
			out.println("</ul>");			
	} // service

} // class
