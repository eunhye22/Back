package org.zerock.myapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@WebServlet("/RequestRedierct")
public class RequestRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(request, response) invoked.", req, res);
		
		// Step.1 비지니스 데이터(Model)를 Request Scope에 속성바인딩
		req.setAttribute("name", "임춘식");
		req.setAttribute("address", "서울");
		
		// Step.2 Redirect 응답 전송
		res.sendRedirect("/ResponseRedirect");
		
		log.info("Succeed to send a Redirection Response to the web browser.");
		
		
	} // service

} // end class
