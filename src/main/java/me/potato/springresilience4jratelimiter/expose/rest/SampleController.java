
/*
 * SampleController.java 2022. 01. 10
 *
 * Copyright 2022 Naver Cloud Corp. All rights Reserved.
 * Naver Cloud Corp. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.springresilience4jratelimiter.expose.rest;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongju.paek
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SampleController {
	private final RateLimiterRegistry rateLimiterRegistry;

	@PostConstruct
	public void init() {
		rateLimiterRegistry.getAllRateLimiters().forEach(limiter -> log.info(limiter.toString()));
	}

	@GetMapping("/test01")
	public String test01() throws Throwable {
		var sampleRatelimiter = rateLimiterRegistry.rateLimiter("sampleRatelimiter");
		var decorator = RateLimiter.decorateCheckedSupplier(sampleRatelimiter, () -> {
			Thread.sleep(3000L);
			return "Hello World";
		});

		return Try.of(decorator).recover(Throwable.class, "Too much call").get();
	}
}