<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Arrays" %>

<jsp:useBean 
    id="loginBean"
    class="org.zerock.myapp.domain.LoginBean"
    scope="request" />
<jsp:setProperty name="loginBean" property="userid" value="Yoseph" /> 
<jsp:setProperty name="loginBean" property="passwd" value="1234" /> 
1. userid: <jsp:getProperty name="loginBean" property="userid" />   <br>
2. passwd: <jsp:getProperty name="loginBean" property="passwd" />  


<!-- EL변수에 대해서 이해하자! -->
<%-- 문법: ${ EL변수 } --%>

<!-- 1. 내장객체를 이용하자! -->
<!-- pageScope, requestScope, sessionScope, applicationScope -->

<%
    pageContext.setAttribute("__PAGE__", "PAGE_VALUE");
    request.setAttribute("__REQUEST__", "REQUEST_VALUE");
    session.setAttribute("__SESSION__", "SESSION_VALUE");
    application.setAttribute("__APPLICATION__", "APPLICATION_VALUE");
%>

<h2>1. 공유속성 - 진짜 EL변수임</h2>
<ol>
    <%-- 사용법: ${ 공유속성명 } : EL은 지정된 공유속성을 가장 짧은 공유영역을 시작으로
         가장 긴 공유영역 순으로 찾아들어감 : page > request > session > application --%>
    <li>pageScope : ${ __PAGE__ }</li>
    <li>requestScope: ${ __REQUEST__ }</li>
    <li>sessionScope: ${ __SESSION__ }</li>
    <li>applicationScope: ${ __APPLICATION__ }</li>
    <!-- 없는 공유속성을 EL변수로 지정하면, 빈문자열(Empty String) 출력 -->
    <li>Unknown Shared Attribute: ${ __TTT__ }</li>
</ol>

<h2>2. EL내장객체 - EL변수로 올 수 있음</h2>
<ol>
    <!-- EL변수로 내장객체 사용: 각공유영역Scope.공유속성명 -->
    <li>pageScope : ${ pageScope.__PAGE__ }</li>
    <li>requestScope: ${ requestScope.__REQUEST__ }</li>
    <li>sessionScope: ${ sessionScope.__SESSION__ }</li>
    <li>applicationScope: ${ applicationScope.__APPLICATION__ }</li>

    <!-- EL변수로 내장객체 사용: param.전송파라미터명 -->
    <li>param.name  : ${ param.name  }</li>
    <li>param.age   : ${ param.age   }</li>
    <li>param.hobby#1 : ${ param.hobby }</li>
    <li>param.hobby#2 : ${ Arrays.toString( paramValues.hobby ) }</li>

    <!-- EL변수로 내장객체 사용: header.요청메시지헤더명 -->
    <li>header.Connection : ${ header.Connection }</li>
    <li>header.Accept : ${ header.Accept }</li>

    <!-- EL변수로 내장객체 사용: cookie.요청메시지쿠키명 -->
    <li>cookie.JSESSIONID.getValue() : ${ cookie.JSESSIONID.getValue() }</li>

    <!-- web.xml 파일에 등록된 context 초기화 파라미터 획득 및 출력 : -->
    <!-- initParam.Context초기화파라미터명 -->
    <li>initParam.driver: ${ initParam.driver }</li>
    <li>initParam.jdbcUrl: ${ initParam.jdbcUrl }</li>
    <li>initParam.user: ${ initParam.user }</li>
    <li>initParam.pass: ${ initParam.pass }</li>

    <!-- EL변수로 내장객체 사용법: pageContext.getter메소드명.getter메소드명 -->
    <!-- 1. 아래는 다음과 같음: pageContext.getRequest().getRequestURI() 리턴값 출력 -->
    <li>pageContext #1 : ${ pageContext.request.requestURI }</li>
    <!-- 2. 아래는 다음과 같음: pageContext.getRequest().getSession().getId() 리턴값 출력 -->
    <li>pageContext #2 : ${ pageContext.request.session.id }</li>
</ol>

<hr>

<!-- 1. 공유속성의 값이 자바빈즈 객체라면, 어떻게 EL표현식으로 출력가능한가! -->
<!--    : 공유속성명.프로퍼티명 으로 출력 -->
1. LoginBean.userid: ${ loginBean.userid } == ${ loginBean.getUserid() } <br>
2. LoginBean.passwd: ${ loginBean.passwd } == ${ loginBean.getPasswd() }

