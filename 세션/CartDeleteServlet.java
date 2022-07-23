package org.zerock.myapp.servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 장바구니 실습 - 장바구니 비우기 ( 로그아웃 기능과 유사 )
@WebServlet("/CartDelete")
public class CartDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(request, response) invoked.", req, res);
		
		// 장바구니 비우기 : 
		// 현재 브라우저의 세션아이디(이름)로 식별되는 Session Scope 공유영역 파괴 + 세션아이디 무효화
		HttpSession sess = req.getSession(false);
		
		try {
			Objects.requireNonNull(sess);
			
			sess.invalidate(); 			// Session ID 무효화 + Session Scope 영역 파괴
			
			// 마지막 응답 페이지는 요청포워딩(Request Forwarding)을 통해, JSP에서 생성하도록 함
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/cartdelete.jsp");
			dispatcher.forward(req,  res);
			
		} catch(Exception e) {
			throw new ServletException(e);
		} // try-catch
		
	} // service

} // end class
