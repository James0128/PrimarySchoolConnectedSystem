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
 * Servlet implementation class signupServlet
 */
@WebServlet("/signupServlet")
public class signupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public signupServlet() {
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

		// 註冊
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		if (account != null && password != null && !account.isEmpty() && !password.isEmpty()) {

			// insert
			User user = new User();

			user.setAccount(account);
			user.setPassword(password);
			user.setName(name);
			user.setPhone(phone);
			user.setAddress(address);

			user.setState(1);// 普通用户
			request.getSession().setAttribute("signup", user);

			if (!isAccountExist(request, account)) {// 注册新的用户
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					UserMapper userMsg = session.getMapper(UserMapper.class);
					userMsg.insertSelective(user);
					session.commit();
					session.close();
				}
				response.getWriter().append("注册成功,請登錄!");
			} else {// 账号已存在
				response.getWriter().append(account + ",账号已存在,请重试！");
			}
		} else {// 參數錯誤
			response.getWriter().append("参数錯誤(把空的參數補上),请重试！");
		}
	}

	boolean isAccountExist(HttpServletRequest request, String account) {// 账号已存在吗？
		User user = new User();
		user.setAccount(account);

		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		if (session != null) {
			UserMapper userMsg = session.getMapper(UserMapper.class);
			user = userMsg.selectByAccount(user);
			session.commit();
			session.close();
		}
		return user != null;
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
