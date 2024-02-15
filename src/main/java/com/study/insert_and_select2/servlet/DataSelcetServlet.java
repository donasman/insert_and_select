package com.study.insert_and_select2.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.insert_and_select2.dao.StudentDao;
import com.study.insert_and_select2.entity.Student;


@WebServlet("/data/list")
public class DataSelcetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DataSelcetServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("조회 요청 들어옴!");
		StudentDao studentDao = StudentDao.getInstance();
		List<Student> students = studentDao.getStudentListAll();
		Gson gson = new Gson();
		
		Map<String, Object> responseMap = new HashMap<>();
		
		responseMap.put("data", students);
		
		response.setStatus(200);
		response.setContentType("application/json");
		response.getWriter().println(gson.toJson(responseMap));
	}


}
