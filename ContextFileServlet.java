package org.zerock.myapp.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
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

@WebServlet("/ContextFile")
public class ContextFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(req, res) invoked.");
		
		// "src/main/webapp/WEB-INF/testFile.txt" 읽어내기
		// "/WEB-INF/testFile.txt" ( 웹어플리케이션 기준 경로 -> Content Root가 시작지점)
		
		// 자원객체 총 4개 닫아줘야함 
		// --> InputStreamReader는 BufferedReader을 닫으면 닫힌다.
		// 보조스트림 (마지막->처음순) -> 기본스트림 순서로 닫아야한다.
		
		
		ServletContext sc = req.getServletContext();
		@Cleanup
		InputStream is = sc.getResourceAsStream("/WEB-INF/testFile.txt");
		@Cleanup
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		// 응답문서 생성
		res.setContentType("text/html; charset=utf8");
		@Cleanup
		PrintWriter out = res.getWriter();
		
		out.println("<ol>");
		
		String line;
		while((line = br.readLine()) != null) {		// null이 아닐때까지 읽어라
			out.println("<li>" + line + "</li>");
		} //while
		
		out.flush();
		out.close();
		
	}//service


}//class