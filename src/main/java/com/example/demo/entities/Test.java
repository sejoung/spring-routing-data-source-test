package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testId;

	private String name;

	public Test(String name) {
		this.name = name;
	}
}
