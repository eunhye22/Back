package org.zerock.myapp.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
//@NoArgsConstructor

// Servlet의 Lifecycle과 그 callback 메소드들을 이해하자

@WebServlet({"/Lifecycle1", "/Lifecycle2"})
public class LifecycleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       

    @Override
	public void init(ServletConfig config) throws ServletException {	// 사전처리작업
		log.trace("init({}) invoked.", config);
		
		// 요청처리에 필요한 각종 자원객체의 획득(DataSource)
	} //init

    @Override
	public void destroy() {												// 사후처리작업
    	log.trace("destroy({}) invoked.");
	} //destroy

    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("service({}) invoked.");
	}//service


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
