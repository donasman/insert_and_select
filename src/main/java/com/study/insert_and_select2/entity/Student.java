package com.study.insert_and_select2.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Student {
	private int studentId;
	private String name;
	private int age;
}
