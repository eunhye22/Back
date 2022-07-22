package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 장바구니 실습 - 장바구니 비우기 ( 로그아웃 기능과 유사 )
@WebServlet("/CartDeleteCookie")
public class CartDeleteCookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 클라이언트 부라우저에 쿠키파일로 저장됭있는 모든 장바구니 데이터를 삭제
	// How? 각 쿠키의 만료기간을 조작 (*****)
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(request, response) invoked.", req, res);
		
		// Step.1 웹 브라우저가 보낸 request 메시지의 헤더에 있는 모든 쿠키를 배열로 획득
		Cookie[] basket = req.getCookies();	//1
		
		// Step.2 각 쿠키의 만료기간을 1 (0은 허용안함)로 바꾸어서, 바로 만료되게 만듬 (***)
		if(basket != null) { // 장바구니가 있다면
			
			// 클라이언트 브라우저에 저장된 모든 쿠키(= 현재 브라우저)
			for(Cookie cookie : basket) {
				cookie.setMaxAge(1);	//2 - in seconds, 만료기간=1초로 변경( 즉, 바로 삭제시켜라!)
				
			// 1초의 만료기간으로 변경된 쿠키들을 다시 웹브라우저로 보내면,
			// 웹브라우저는 이 사이트 주소에 대해 파일로 보관중이던 모든 쿠키의 만료기간이 1초로 단축되었기에
			// 1초 후에 모든 쿠키(=작은데이터, 이름=값 형태) 파괴시켜버림
				
				res.addCookie(cookie);  //3 - 시간만료가 설정된 쿠키를 브라우저로 전송
				
			} // enhanced for
		} // if
		
		// Step.3 응답문서 생성 및 전송
		res.setContentType("text/html; charset=utf8");
		
		@Cleanup
		PrintWriter out = res.getWriter();
		
		out.println("<a href='product.html'>상품 선택 페이지</a>");
		
		out.flush();
		
	} // service

} // end class
