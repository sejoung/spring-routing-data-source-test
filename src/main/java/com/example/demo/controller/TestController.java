package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Test;
import com.example.demo.service.TestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

	private final TestService service;

	@GetMapping("/test")
	public List<Test> getTest() {
		return service.getTest();
	}

}
