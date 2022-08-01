package org.zerock.myapp;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// JUnit 기반의 테스트 클래스는 반드시 기본 생성자를 가져야 함(그 외의 다른 생성자를 가져서는 안됨)

// JUnit Jupyter Test Framework이, 개발자가 만든 테스트 클래스의 테스트 메소드를 수행할 때
// '테스트 인스턴스(객체)를 어떤 단위로 생성할 것인가?'를 지정하는 어노테이션
// (1) PER_CLASS : 테스트 메소드가 몇개가 있든, 하나의 인스턴스를 가지고 모든 테스트 메소드를 수행하겠다.
// (2) PER_METHOD : 테스트 메소드마다, 인스턴스를 생성해서 해당 테스트 메소드를 수행하겠다.
@TestInstance(Lifecycle.PER_CLASS)

// 이 테스트 클래스에 만들 테스트 메소드의 실행순서를 정하는 기준을 설정하는 어노테이션
// (1) MethodOrderer.MethodName		: 테스트 메소드 이름을 정렬해서 실행순서를 결정(사전순서)
// (2) MethodOrderer.DisplayName	: 테스트 메소드 표시이름을 정렬해서 실행순서를 결정(사전순서)
// (3) MethodOrderer.OrderAnnotation : @Order(순서를 의미하는 정수 >=1)어노테이션으로 실행순서 결정(**)
// (4) MethodOrderer.Random 	: 무작위로 테스트 메소드의 순서를 매번 결정
@TestMethodOrder(OrderAnnotation.class)
public class AppTest {
	
	// 테스트 수행전, 전처리(Pre-processing) 로직을 수행하는 메소드 (optional)
	
	@BeforeAll	 // 1회성 전처리 로직을 넣을 때 사용
	void beforeAll() {
		log.trace("beforeAll() invoked."); 
		}
	
	@BeforeEach  // 테스트 메소드 수행전 매번 수행되며, 전처리 로직을 넣을 때 사용
	void beforeEach() {
		log.trace("beforeEach() invoked.");
		}
	
	// Test Units + Orders => Test Scenario (***)
	
	// 테스트 메소드 (필수)
	@Test
	@Order(2)
	@DisplayName("2. 데이터소스 테스트")
	@Timeout(value=2000, unit=TimeUnit.MILLISECONDS) // 2초 = 1/1000초(1밀리초) * 2000
	void T1() {	// Test Unit
		log.trace("T1() invoked.");
	}
	
	@Test
//	@Order(1)
	@DisplayName("1. 자바빈즈 객체 테스트")
	@Timeout(value=2, unit=TimeUnit.SECONDS)
	void T2() { // Test Unit
		log.trace("T2() invoked.");
	}
	
	@Test
	@Order(99)
	void T3() { // Test Unit
		log.trace("T3() invoked.");
	}
	
	@Test
	void T4() { // Test Unit
		log.trace("T4() invoked.");
	}
	
	// 테스트 수행후, 후처리(Post-processing) 로직을 수행하는 메소드 (optional)
	
	@AfterEach	// 테스트 메소드 수행후 매번 수행되며, 후처리 로직을 넣을 때 사용
	void afterEach() {
		log.trace("afterEach() invoked.");
		}
	
	@AfterAll	// 1회성 전처리 로직을 넣을 때 사용
	void afterAll() {
		log.trace("afterAll() invoked.");
		}

} // end class
