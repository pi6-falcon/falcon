package com.falcon.falcon

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FalconApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun fail() {
		"1" shouldBe "2"
	}

}
