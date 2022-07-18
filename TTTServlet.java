package org.zerock.myapp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import javax.servlet.ServletConfig;
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

@WebServlet("/TTT")
public class TTTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	
	// 1. App.Scope에 공유되어있는 JDBC 연결정보를 이용해서,
	// JDBC Connection 1개를 생성하여, 필드를 저장
	@Override
	public void init(ServletConfig config) throws ServletException {
		log.trace("init({}) invoked.", config);
		
		super.init(config);
		
		try {	// To get a JDBC connection
			ServletContext sc = config.getServletContext();
			
			String driver = (String) sc.getAttribute("driver");
			String jdbcUrl = (String) sc.getAttribute("jdbcUrl");
			String user = (String) sc.getAttribute("user");
			String pass = (String) sc.getAttribute("pass");
			
			// JDBC programming
			Class.forName(driver);
			this.conn = DriverManager.getConnection(jdbcUrl, user, pass);
			
			// 연결객체가 null인지 여부 검증 (방법2가지)
			Objects.requireNonNull(this.conn);
//			assert this.conn != null;
			
		} catch(Exception e) {
			throw new ServletException(e);
		}//try-catch
		
	} // init
	
	// 2. 필드에 저장되어있는 JDBC Connection을 이용하여, 현재날짜와 시간을 응답문서로 출력
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
		throws ServletException, IOException {
		log.trace("service(req, res) invoked.");
		
		String now = null;
		
		try {	// 현재날짜와 시간정보를 얻는 SQL을 수행하고 응답문서로 출력
			String sql = "SELECT to_char(current_date, 'yyyy/MM/dd - HH24:MI:SS') AS now FROM dual";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				now = rs.getString("now");
			}//if
			
			// === 응답 ===
			res.setContentType("text/html; charset = UTF-8");
			
			@Cleanup
			PrintWriter out = res.getWriter();
			
			out.println("<h1>" +now+"</h1>");
			
			out.flush();
			
		} catch(Exception e) {
			throw new IOException(e);
		}//try-catch
		
	} // service

	// 3. 필드에 저장되어있는 JDBC Connection 자원을 해제
	@Override
	public void destroy() {
		log.trace("destroy() invoked.");
		
		
		
		try {	// 필드에 있는 JDBC Connection 해제
			if(this.conn != null & !this.conn.isClosed()) {
				this.conn.close();
			}//if
		} catch(Exception e) {;;}
		
	} // destroy


}//class
