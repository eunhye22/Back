package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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

@WebServlet("/Sport")
public class SportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		log.trace("service(req, res) invoked.");
		
		// Step.1 전송파라미터 획득
		req.setCharacterEncoding("utf8");
		
		String gender = req.getParameter("gender");
		String[] sports = req.getParameterValues("sports");
		
		log.info("\t+ gender: {}, sports: {}", gender, Arrays.toString(sports));
				
		
		// Step.2 응답문서 생성 및 전송
		res.setContentType("text/html; charset=utf8");
		
		@Cleanup("close")
		PrintWriter out = res.getWriter();
		
		out.println("<ol>");
		
		for(String sport : sports) {
			out.println("\t<li>"+sport+"</li>");
		} // enhanced for
		
		out.println("</ol>");
		
		out.flush();
	} // service

} // end class