package com.study.insert_and_select2.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.insert_and_select2.dao.StudentDao;
import com.study.insert_and_select2.entity.Student;


@WebServlet("/data/addition")
public class DataInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DataInsertServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		
		String readData = null;
		BufferedReader reader = request.getReader();
		/*
		 *  "{
		 *  	"name" = "정건희"
		 *  	"age" = "25"
		 *  
		 *  }"
		 *  
		 */
		
		while((readData = reader.readLine()) != null) {
			builder.append(readData);
		}
		
		// Json -> Map
		
		Gson gson = new Gson();
		
		Map<String, Object> map = gson.fromJson(builder.toString(), Map.class);
		

		
		// Json -> Entity객체
		// 객체 형태를 사용해서 key값을 외우지 않고 사용할 수 있다.
		Student student = gson.fromJson(builder.toString(), Student.class);
		System.out.println(student);
		System.out.println(student.getName());
		System.out.println(student.getAge());
			

		StudentDao studentDao = StudentDao.getInstance();
		
		Student findStudent = StudentDao.findStudentByName(student.getName());
		
			
		if(findStudent != null) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("errorMessage", "이미 등록된 이름입니다.");
			
			response.setStatus(400);
			response.setContentType("application/json");
			response.getWriter().println(gson.toJson(errorMap));
			return;
		}
		int successCount = studentDao.saveStudent(student);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("status", 201);
		responseMap.put("data","응답데이터");
		responseMap.put("successCount", successCount);
		
		response.setStatus(201);
		response.setContentType("application/json");
		
		PrintWriter writer = response.getWriter();
		writer.println(gson.toJson(responseMap));
		
		
				
	}

}
