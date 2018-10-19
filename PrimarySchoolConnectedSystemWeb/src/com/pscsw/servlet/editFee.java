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
import com.pscsw.dao.FeeInfoMapper;
import com.pscsw.po.ClassInfo;
import com.pscsw.po.FeeInfo;

/**
 * Servlet implementation class editFee
 */
@WebServlet("/editFee")
public class editFee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public editFee() {
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
		// 編輯缴费
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");//
		String result = "編輯缴费失敗!";
		if (id != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				ClassInfoMapper ClassInfoMapper = session.getMapper(ClassInfoMapper.class);
				ClassInfo info = ClassInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
				if (info != null) {
					FeeInfoMapper FeeInfoMapper = session.getMapper(FeeInfoMapper.class);
					FeeInfo[] fees = FeeInfoMapper.selectByClass(info.getClassName());// 查询，本班级有关的费用
					if (fees != null) {
						for (FeeInfo f : fees) {// 把旧的，全部删除
							FeeInfoMapper.deleteByPrimaryKey(f.getId());
						}
					}					
					// FEE2018_01,288,2018秋季課本費用
					String editData = request.getParameter("editData");
					String[] data = editData.split("\n|\r");
					for (String d : data) {
						if (!d.isEmpty()) {
							String[] row = d.split(",");
							if (row.length == 3) {
								FeeInfo fee = new FeeInfo();
								fee.setClassName(info.getClassName());
								fee.setFeeNumber(row[0]);
								fee.setClassFee(Float.valueOf(row[1]));
								fee.setClassFeeDetail(row[2]);
								FeeInfoMapper.insertSelective(fee);
							}
						}
					}
					result = "編輯缴费成功!";
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
