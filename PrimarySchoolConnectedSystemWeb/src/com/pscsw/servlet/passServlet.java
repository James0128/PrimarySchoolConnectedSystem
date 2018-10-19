package com.pscsw.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.UserMapper;
import com.pscsw.po.User;

/**
 * Servlet implementation class passServlet
 */
@WebServlet("/passServlet")
public class passServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public passServlet() {
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

		// 密碼修改
		String account = (String) request.getSession().getAttribute("account");
		String password_old = request.getParameter("password_old");
		String password_new = request.getParameter("password_new");
		String result = "密碼修改失敗!";

		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		if (session != null) {
			if (account != null && !account.isEmpty()) {
				UserMapper userMapper = session.getMapper(UserMapper.class);
				User user = new User();
				user.setAccount(account);
				user = userMapper.selectByAccount(user);
				if (user != null) {
					if (user.getPassword().equals(password_old)) {
						user.setPassword(password_new);
						userMapper.updateByPrimaryKeySelective(user);
						result = "密碼修改成功!";

						// 登出
						request.getSession().removeAttribute("account");
						request.getSession().removeAttribute("name");
					}
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
