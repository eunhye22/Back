package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/ResponseRedierct")
public class ResponseRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(request, response) invoked.", req, res);
		
		// Step.1 공유데이터 영역(Req.Scope)에서, Model 얻어내기
		String name = (String) req.getAttribute("name");
		String address = (String) req.getAttribute("address");
		
		log.info("Model - name: {}, address: {}", name, address);
		
		// Step.2 Model을 이용한 응답문서의 생성 및 전송
		res.setContentType("text/html; charset=utf8");
		
		@Cleanup
		PrintWriter out = res.getWriter();		
		
		out.println("<h1>/Response</h1>");
		out.println("<hr>");
		out.println("<h2> 1. name: " + name + "</h2>");
		out.println("<h2> 2. address: " + address + "</h2>");
		
		out.flush();
		
	} // service

} // end class
