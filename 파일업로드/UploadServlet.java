package org.zerock.myapp.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.zerock.uuid.UUIDGenerator;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@WebServlet("/Upload")
@MultipartConfig(
		location="C:/temp/upload", 
		maxFileSize = 1* 1024 * 1024 * 20, 				// 최대 파일크기 : 20MB
		maxRequestSize = 1 * 1024 * 1024 * 20			// 한 요청 당 최대 크기 : 20MB
)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	log.trace("service(request, response) invoked.");
    	
    	request.setCharacterEncoding("UTF8");
    	response.setContentType("text/html; charset=utf8");
    	
       	@Cleanup
    	PrintWriter out = response.getWriter();
//    	---------------------------------------
//    	Common Step : 
//		---------------------------------------
    	
    	Collection<Part> parts = request.getParts();
    	parts.forEach( e -> log.info(e.getName()));
    	
    	// 멀티파트를 구성하는 각 Part 의 이름이란? => 각 파트를 구성하는 전송파라미터의 이름
//    	Part writerPart = request.getPart("writer");
//    	log.info("writerPart : {}", writerPart);
//    	
//    	Part uploadFilePart = request.getPart("uploadFile");
//    	log.info("uploadFilePart : {}", uploadFilePart);
    	
    	// 각 파트의 구성 헤더정보를 접근해보자!!
    	
    	// 방법 1 ) 
    	Iterator<Part> iter = parts.iterator();
    	
    	while(iter.hasNext()) {
    		Part part = iter.next();
    		
    		// 이 Part에 포함 된, 전송파라미터의 이름 ( = 파트이름)
    		log.info("======================================================");
    		log.info("\t+ 1. part.getName : {}", part.getName());							// 전송파라미터이름(=파트이름)
    		log.info("\t+ 2. part.getContentType : {}", part.getContentType());				// Content-Type 헤더의 값
    		log.info("\t+ 3. part.getSize : {}", part.getSize());							// Body의 Content Length		 
    		log.info("\t+ 4. part.getSubmittedFileName : {}", part.getSubmittedFileName());	// 파일의 원본 파일명
    		log.info("\t+ 5. part.getHeaderNames : {}", part.getHeaderNames());				// 헤더명의 목록
    		
    		// 첨부파일에 포함하고 있는 PArt를 필터링해서, 첨부파일 저장
    		// 하지만, 파일업로드는 불특정다수가 보내오기 때문에, 파일명 중복발생가능성이 있다.
    		// 따라서, 파일명은 고유한 이름으로 저장되어야 한다.
    		if(part.getSubmittedFileName() != null) {
    			try {
//    				Date today = new Date();
//    				SimpleDateFormat todayTime = new SimpleDateFormat("yyyyMMdd");
//    				String folderDir = "C:/temp/upload/"+todayTime.format(today);
//    				File newFolder = new File(folderDir);
//    				
//    				if(newFolder.exists() == false) {
//    					newFolder.mkdir();
//    				}

					String uuid = UUIDGenerator.generateUniqueKeysWithUUIDAndMessageDigest();
					part.write(uuid);
					part.delete(); 
					
					String encodeFilename = URLEncoder.encode(part.getSubmittedFileName(), "utf8");
					
					String link = String.format("<a href='/FileDown?file_name=%s&uuid=%s'>파일다운로드</a><br>", 
													encodeFilename, uuid);
					out.println(link);
					
				} catch (Exception e) {;;;}	
    		} // if
    				
    	} // while

    	out.flush();
   	
	} // service

} // end class
