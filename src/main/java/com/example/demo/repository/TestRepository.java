package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}
