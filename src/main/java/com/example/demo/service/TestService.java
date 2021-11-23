package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Test;
import com.example.demo.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestRepository repository;

	@Transactional(readOnly = true)
	public List<Test> getTest() {
		return repository.findAll();
	}
}
