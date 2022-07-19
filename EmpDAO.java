package org.zerock.myapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zerock.myapp.domain.EmpVO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class EmpDAO {
	
	private static DataSource dataSource;
	
	static {	// JNDI lookup을 통해, DataSource 자원객체를 필드에 초기화 시키자.
				// WAS가 생성한 DataSource 객체 획득 방법#2
				// WAS의 표준 API인 JNDI API를 이용해서, 설정에 의해 자동 생성된 데이터소스 획득
		
		try {
			// 1. JNDI tree의 뿌리(root)에 접근하게 해주는 객체를 획득
			Context ctx = new InitialContext(); // 100% 성공(if Web Application 안에서 수행된다면)
			
			// 2. Context 객체를 가지고, 지정된 이름을 가지는 리소스 열매를 찾아서 획득
			EmpDAO.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleCloudATP");
			
			log.info("\t+ this.dataSource: {}", EmpDAO.dataSource);

			} catch(Exception e) {
				e.printStackTrace();
			} // try-catch
		}	
	
	public List<EmpVO> selectAll() throws SQLException {
		log.trace("selectAll() invoked.");
		
		// Scott 스키마의 'emp' 테이블을 모두 조회해서, 리스트 컬렉션으로 반환
		// 리스트 컬렉션의 요소는 EmpVO이어야 함.
		
		
		Connection conn = EmpDAO.dataSource.getConnection();
		
		String sql = "SELECT * FROM emp ORDER BY empno";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<EmpVO> list = new Vector();
		
		try (conn; pstmt; rs;) {
			while(rs.next()) {
				Integer empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				Integer mgr = rs.getInt("mgr");
				Date hireDate = rs.getDate("hireDate");
				Double sal = rs.getDouble("sal");
				Double comm = rs.getDouble("comm");
				Integer deptno = rs.getInt("deptno");
				
				EmpVO vo = new EmpVO(empno, ename, job, mgr, hireDate, sal, comm, deptno);
				
				list.add(vo);
			}
			
			
		} // try-with-resources
		
		
		return list;
	} // select
	

} // class
