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
 * Servlet implementation class classDelete
 */
@WebServlet("/classDelete")
public class classDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public classDelete() {
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

		// 班級刪除
		String id = request.getParameter("id");
		String result = "班級刪除失敗!";
		if (id != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				ClassInfoMapper ClassInfoMapper = session.getMapper(ClassInfoMapper.class);
				ClassInfo info = ClassInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
				if (info != null) {
					ClassInfoMapper.deleteByPrimaryKey(Integer.valueOf(id));
					result = "班級刪除成功!";
				}
				session.commit();
				session.close();
			}
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
