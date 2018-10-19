package com.pscsw.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.FeeInfoMapper;
import com.pscsw.dao.FeeRecordMapper;
import com.pscsw.po.FeeInfo;
import com.pscsw.po.FeeRecord;

/**
 * Servlet implementation class orderServlet
 */
@WebServlet("/orderServlet")
public class orderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public orderServlet() {
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

		// 学生繳費
		String result = "学生繳費失敗!";
		String id = request.getParameter("id");// 需要繳費的ID
		if (id != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				FeeInfoMapper FeeInfoMapper = session.getMapper(FeeInfoMapper.class);// 获取映射
				FeeInfo fee = FeeInfoMapper.selectByPrimaryKey(Integer.valueOf(id));// 通过id，找到应交 费用
				if (fee != null) {
					String account = (String) request.getSession().getAttribute("account");
					float feeAlreadyPayed = getFeeAlreadyPayed(fee, account);// 已缴费用
					if (fee.getClassFee() > feeAlreadyPayed) {
						FeeRecordMapper FeeRecordMapper = session.getMapper(FeeRecordMapper.class);// 获取映射
						FeeRecord record = new FeeRecord();
						record.setAccount(account);
						record.setFeeNumber(fee.getFeeNumber());
						record.setFeeTotal(fee.getClassFee() - feeAlreadyPayed);
						Date date = new Date();
						date.setTime(System.currentTimeMillis());
						record.setFeeTime(date);
						FeeRecordMapper.insertSelective(record);// 生成缴费记录
						result = "已交" + (fee.getClassFee() - feeAlreadyPayed) + "元,学生繳費成功!";
					}
				} else {
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

	float getFeeAlreadyPayed(FeeInfo fee, String account) {
		float feeAlreadyPayed = 0;// 已缴费用
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		if (session != null) {
			FeeRecordMapper FeeRecordMapper = session.getMapper(FeeRecordMapper.class);// 获取映射
			FeeRecord[] records = FeeRecordMapper.selectByFeeNumber(fee.getFeeNumber());// 通过缴费代码，获取缴费记录
			if (records != null) {// 实缴费用记录
				for (FeeRecord r : records) {
					if (r.getAccount().equals(account)) {// 是我缴的费用？
						feeAlreadyPayed += r.getFeeTotal();
					}
				}
			}
			session.commit();
			session.close();
		}
		return feeAlreadyPayed;
	}

}
