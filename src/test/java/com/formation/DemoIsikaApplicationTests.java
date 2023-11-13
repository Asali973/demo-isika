package com.formation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.formation.service.Calculator;

@SpringBootTest
class DemoIsikaApplicationTests {
	
	Calculator calculator = new Calculator();

	@Test
	void testSum() {
		
		assertEquals(6, calculator.sum(2, 4));
	}
	
	@Test
	void testmultiply() {
		
		assertEquals(8, calculator.multiply(2, 4));
	}
	
	@Test
	void testdiv() {
		
		assertEquals(1, calculator.divide(4, 4));
	}

}