package com.pscsw.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.ClassInfoMapper;
import com.pscsw.dao.FeeInfoMapper;
import com.pscsw.dao.StudentReportMapper;
import com.pscsw.po.ClassInfo;
import com.pscsw.po.FeeInfo;
import com.pscsw.po.StudentReport;

/**
 * Servlet implementation class editScore
 */
@WebServlet("/editScore")
public class editScore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public editScore() {
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
		// 編輯成績
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");//
		String result = "編輯成績失敗!";
		if (id != null) {
			SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
			if (session != null) {
				ClassInfoMapper ClassInfoMapper = session.getMapper(ClassInfoMapper.class);
				ClassInfo info = ClassInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
				if (info != null) {
					StudentReportMapper StudentReportMapper = session.getMapper(StudentReportMapper.class);
					StudentReport[] scores = StudentReportMapper.selectByStudentClass(info.getClassName());// 查询，本班级有关的成績
					if (scores != null) {
						for (StudentReport r : scores) {// 把旧的，全部删除
							StudentReportMapper.deleteByPrimaryKey(r.getId());
						}
					}
					// 马玉1,XH1001,数学,SX1001,89,2018年10月06日 12时30分
					SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
					String editData = request.getParameter("editData");
					String[] data = editData.split("\n|\r");
					for (String d : data) {
						if (!d.isEmpty()) {
							String[] row = d.split(",");
							if (row.length == 6) {
								StudentReport score = new StudentReport();
								score.setStudentClass(info.getClassName());
								score.setStudentName(row[0]);
								score.setStudentNumber(row[1]);
								score.setSubjectName(row[2]);
								score.setSubjectNumber(row[3]);
								score.setExaminationScore(Integer.valueOf(row[4]));
								try {
									score.setExaminationTime(fmt.parse(row[5]));
								} catch (Exception e) {
									e.printStackTrace();
								}
								StudentReportMapper.insertSelective(score);
							}
						}
					}
					result = "編輯成績成功!";
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
