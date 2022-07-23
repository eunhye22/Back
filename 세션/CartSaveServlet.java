package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 장바구니 실습
@WebServlet("/CartSave")
public class CartSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(request, response) invoked.", req, res);
		
		// Step.1 장바구니에 저장할 상품항목을 전송파라미터로 획득
		req.setCharacterEncoding("utf8");
		
		String product = req.getParameter("product");
		log.info("\t+ product: {}", product);
		
		// Step.2 장바구니 생성 및 수신된 상품을 장바구니에 추가
		HttpSession sess = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) sess.getAttribute("basket");
		
		if(list == null) {	// Session Scope에서 장바구니가 없으면
			list = new ArrayList<>();			  // 장바구니 새로 만들고
			sess.setAttribute("basket",  list);   // Session Scope에 올려놓고
		} // if-else
		
		list.add(product);
		log.info("\t+ list: {}", list);
		
		// Step.3 응답화면 생성 및 전송
		res.setContentType("text/html; charset=utf8");
		
		@Cleanup
		PrintWriter out = res.getWriter();
		
		out.println("<h1>장바구니에 담기 성공</h1>");
		out.println("<a href='/CartBasket'>장바구니 보기</a>");
		
		out.flush();
		
	} // service

} // end class
