package com.pscsw.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.StudentInfoMapper;
import com.pscsw.po.StudentInfo;

/**
 * Servlet implementation class infoServlet
 */
@WebServlet("/infoServlet")
public class infoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public infoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 学生信息修改
		String result = "信息修改失敗!";
		String account = (String) request.getSession().getAttribute("account");
		if (account != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				StudentInfoMapper StudentInfoMapper = session.getMapper(StudentInfoMapper.class);// 获取映射
				StudentInfo student = StudentInfoMapper.selectByAccount(account);
				if (student != null) {
					student.setStudentNumber(request.getParameter("studentNumber"));
					student.setStudentParent(request.getParameter("studentParent"));
					student.setStudentClass(request.getParameter("studentClass"));
					StudentInfoMapper.updateByPrimaryKeySelective(student);// 学生信息修改
				} else {
					student = new StudentInfo();
					student.setAccount(account);
					student.setStudentName((String) request.getSession().getAttribute("name"));
					student.setStudentNumber(request.getParameter("studentNumber"));
					student.setStudentParent(request.getParameter("studentParent"));
					student.setStudentClass(request.getParameter("studentClass"));
					StudentInfoMapper.insertSelective(student);// 学生信息插入
				}
				result = "信息修改成功!";
			}
			session.commit();
			session.close();
		}
		response.getWriter().append(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
