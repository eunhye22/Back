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

@WebServlet("/Hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
			log.trace("service({}, {}) incoked." , req, res);
			
			try {
			// 비즈니스 로직 처리 및 데이터 생성
			
			// 응답문서의 생성 및 전송
			res.setCharacterEncoding("utf8");
			
			@Cleanup
			PrintWriter out = res.getWriter();				// 2st. method to release resources.
			
//			try (out) {										// 1st. method to release resources.
//				out.println("<html><head></head><body>");
//				out.println("<h1>Hello, World!!!</h1>");
//				out.println("<body></html>");
//				
//				out.flush();
//			} // try-with-resources
				
				out.println("<html><head></head><body>");
				out.println("<h1>Hello, World!!!</h1>");
				out.println("<body></html>");
				
				out.flush();
				
				int value = Integer.parseInt("백");
			
			} catch (NumberFormatException e) {
				throw new IOException(e);
			} //try-catch
		
	} // service

}// class
