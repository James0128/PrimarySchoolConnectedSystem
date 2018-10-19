package com.pscsw.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.ClassInfoMapper;
import com.pscsw.po.ClassInfo;

/**
 * Servlet implementation class classModify
 */
@WebServlet("/classModify")
public class classModify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public classModify() {
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
		// 班級修改
		String result = "班級修改失敗!";
		String id = request.getParameter("id");
		if (id != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				ClassInfoMapper ClassInfoMapper = session.getMapper(ClassInfoMapper.class);// 获取映射
				ClassInfo cla = ClassInfoMapper.selectByPrimaryKey(Integer.valueOf(id));// 查詢班級
				if (cla != null) {
					cla.setClassNumber(request.getParameter("classNumber"));
					cla.setClassName(request.getParameter("className"));
					cla.setClassTeacher(request.getParameter("classTeacher"));
					cla.setClassSchool(request.getParameter("classSchool"));
					ClassInfoMapper.updateByPrimaryKeySelective(cla);// 更新班級
					result = "班級修改成功!";
				}
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
