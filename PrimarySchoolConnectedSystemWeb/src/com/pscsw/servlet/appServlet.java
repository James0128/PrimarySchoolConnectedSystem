package com.pscsw.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.pscsw.MyBatisUtil;
import com.pscsw.dao.FeeInfoMapper;
import com.pscsw.dao.FeeRecordMapper;
import com.pscsw.dao.StudentInfoMapper;
import com.pscsw.dao.StudentReportMapper;
import com.pscsw.dao.SubjectInfoMapper;
import com.pscsw.dao.UserMapper;
import com.pscsw.po.FeeInfo;
import com.pscsw.po.FeeRecord;
import com.pscsw.po.StudentInfo;
import com.pscsw.po.StudentReport;
import com.pscsw.po.User;

/**
 * Servlet implementation class appServlet
 */
@WebServlet("/appServlet")
public class appServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public appServlet() {
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
		// 手机APP接口
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
		if (cmd != null && !cmd.isEmpty()) {
			if (cmd.equals("login")) {// 登录
				login(request, response);
			} else if (cmd.equals("modifyPassword")) {// 修改密码
				modifyPassword(request, response);
			} else if (cmd.equals("modifyInfo")) {// 修改信息
				modifyInfo(request, response);
			} else if (cmd.equals("register")) {// 注册
				register(request, response);
			} else if (cmd.equals("scoreList")) {// 成绩列表
				scoreList(request, response);
			} else if (cmd.equals("feeList")) {// 繳費列表
				feeList(request, response);
			} else if (cmd.equals("feeOrder")) {// 繳費下單
				feeOrder(request, response);
			}
		} else {
			System.out.println("Served at: " + request.getContextPath());
		}
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

	void feeOrder(HttpServletRequest request, HttpServletResponse response) {// 下單繳費
		try {
			String id = request.getParameter("id");// 需要繳費的ID
			if (id != null) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					FeeInfoMapper FeeInfoMapper = session.getMapper(FeeInfoMapper.class);// 获取映射
					FeeInfo fee = FeeInfoMapper.selectByPrimaryKey(Integer.valueOf(id));// 通过id，找到应交 费用
					if (fee != null) {
						String account = (String) request.getParameter("account");
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
						}
					} else {
					}
				}
				session.commit();
				session.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
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

	void feeList(HttpServletRequest request, HttpServletResponse response) {// 查询学生繳費
		try {
			String account = (String) request.getParameter("account");
			if (account != null) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					StudentInfoMapper StudentInfoMapper = session.getMapper(StudentInfoMapper.class);// 获取映射
					StudentInfo student = StudentInfoMapper.selectByAccount(account);// 通过账号，找到学生
					if (student != null) {
						FeeInfoMapper FeeInfoMapper = session.getMapper(FeeInfoMapper.class);// 获取映射
						FeeInfo[] fees = FeeInfoMapper.selectByClass(student.getStudentClass());// 查詢學生所在班級的应交費用
						if (fees != null) {
							getFeeAlreadyPayed(fees, account);// 查询实缴费用
							// 返回成功的代码200
							responseJson res = new responseJson("status", responseJson.STATUS_OK);
							System.out.println(fees.length);
							if (fees != null && fees.length > 0) {
								for (FeeInfo f : fees) {
									Map<String, String> map = new HashMap<String, String>();
									map.put("id", "" + f.getId());
									map.put("feeNumber", f.getFeeNumber());
									map.put("className", f.getClassName());
									map.put("classFee", "" + f.getClassFee());
									map.put("classFeeRecord", "" + f.getClassFeeRecord());
									map.put("classFeeDetail", f.getClassFeeDetail());
									res.addArray("list", map);
								}
							}

							// JSON格式的数据,回传给 调用者
							System.out.println(res.response());
							response.getWriter().append(res.response());
						}
					}
				}
				session.commit();
				session.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void getFeeAlreadyPayed(FeeInfo[] fees, String account) {
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		if (session != null) {
			for (FeeInfo fee : fees) {
				FeeRecordMapper FeeRecordMapper = session.getMapper(FeeRecordMapper.class);// 获取映射
				FeeRecord[] records = FeeRecordMapper.selectByFeeNumber(fee.getFeeNumber());// 通过缴费代码，缴费记录
				float feeAlreadyPayed = 0;// 已缴费用
				if (records != null) {// 实缴费用记录
					for (FeeRecord r : records) {
						if (r.getAccount().equals(account)) {// 是我缴的费用？
							feeAlreadyPayed += r.getFeeTotal();
						}
					}
				}
				fee.setClassFeeRecord(feeAlreadyPayed);// 我的已缴费用
			}
			session.commit();
			session.close();
		}
	}

	void scoreList(HttpServletRequest request, HttpServletResponse response) {// 查询学生成绩报告
		try {
			String account = (String) request.getParameter("account");
			if (account != null) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					StudentInfoMapper StudentInfoMapper = session.getMapper(StudentInfoMapper.class);// 获取映射
					StudentInfo StudentInfo = StudentInfoMapper.selectByAccount(account);
					if (StudentInfo != null) {
						StudentReportMapper StudentReportMapper = session.getMapper(StudentReportMapper.class);// 获取映射
						StudentReport[] reports = StudentReportMapper
								.selectByStudentNumber(StudentInfo.getStudentNumber());
						if (reports != null) {
							getRanks(reports);// 獲取排名
							// 返回成功的代码200
							responseJson res = new responseJson("status", responseJson.STATUS_OK);
							System.out.println(reports.length);
							if (reports != null && reports.length > 0) {
								for (StudentReport s : reports) {
									Map<String, String> map = new HashMap<String, String>();
									map.put("id", "" + s.getId());
									map.put("studentName", s.getStudentName());
									map.put("studentNumber", s.getStudentNumber());
									map.put("studentClass", s.getStudentClass());
									map.put("subjectName", s.getSubjectName());
									map.put("examinationScore", "" + s.getExaminationScore());
									map.put("examinationRank", "" + s.getExaminationRank());
									res.addArray("list", map);
								}
							}

							// JSON格式的数据,回传给 调用者
							System.out.println(res.response());
							response.getWriter().append(res.response());
						}
					}

					session.commit();
					session.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void getRanks(StudentReport[] reports) {// 獲取班级排名
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		if (session != null) {
			StudentReportMapper StudentReportMapper = session.getMapper(StudentReportMapper.class);// 获取映射
			StudentReport[] all = StudentReportMapper.selectAll();// 按成绩升序排列的
			for (StudentReport r : reports) {
				int rank = 0;
				int total = 0;
				for (StudentReport a : all) {
					if (r.getStudentClass().equals(a.getStudentClass())
							&& r.getSubjectNumber().equals(a.getSubjectNumber())) {// 同一个班，同一个课程
						total++;
						if (a.getExaminationScore() < r.getExaminationScore()) {
							rank++;
						}
					}
				}
				r.setExaminationRank(total - rank);
			}
			session.commit();
			session.close();
		}
	}

	void login(HttpServletRequest request, HttpServletResponse response) {
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					User user = new User();
					user.setAccount(account);
					UserMapper userMap = session.getMapper(UserMapper.class);
					user = userMap.selectByAccount(user);
					if (user != null && user.getPassword().equals(password)) {// 验证通过
						StudentInfoMapper StudentInfoMapper = session.getMapper(StudentInfoMapper.class);
						StudentInfo StudentInfo = StudentInfoMapper.selectByAccount(account);// 获取学生信息

						responseJson res = new responseJson("status", responseJson.STATUS_OK);
						res.add("name", user.getName());
						if (StudentInfo != null) {
							res.add("userName", user.getName());
							res.add("userNick", user.getNick());
							res.add("userXuehao", StudentInfo.getStudentNumber());
							res.add("userClass", StudentInfo.getStudentClass());
						}
						response.getWriter().append(res.response());
					} else {
						responseJson res = new responseJson("status", responseJson.STATUS_ERR);
						response.getWriter().append(res.response());
					}

					session.commit();
					session.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void register(HttpServletRequest request, HttpServletResponse response) {
		try {
			String account = request.getParameter("account");
			if (account != null && !account.isEmpty()) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					User user = new User();
					user.setAccount(account);
					UserMapper userMap = session.getMapper(UserMapper.class);
					user = userMap.selectByAccount(user);// 检查账号
					if (user != null) {// 账号重复
						responseJson res = new responseJson("status", responseJson.STATUS_DUP);
						response.getWriter().append(res.response());
					} else {// 注册
						User reg = new User();
						reg.setAccount(account);
						reg.setPassword(request.getParameter("password"));
						reg.setName(request.getParameter("name"));
						reg.setState(1);// 顾客
						userMap.insertSelective(reg);
						responseJson res = new responseJson("status", responseJson.STATUS_OK);
						response.getWriter().append(res.response());
					}

					session.commit();
					session.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					User user = new User();
					user.setAccount(account);
					user.setPassword(password);
					UserMapper userMap = session.getMapper(UserMapper.class);
					user = userMap.selectByAccount(user);// 检查账号
					if (user != null && user.getPassword().equals(password)) {// 密码验证通过，更新密码
						User update = new User();
						update.setId(user.getId());
						update.setPassword(request.getParameter("password2"));
						userMap.updateByPrimaryKeySelective(update);// 修改密码
						responseJson res = new responseJson("status", responseJson.STATUS_OK);
						response.getWriter().append(res.response());
					} else {
						responseJson res = new responseJson("status", responseJson.STATUS_ERR);
						response.getWriter().append(res.response());
					}

					session.commit();
					session.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	void modifyInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			System.out.println(account);
			System.out.println(password);
			if (account != null && !account.isEmpty() && password != null && !password.isEmpty()) {
				SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
				if (session != null) {
					User user = new User();
					user.setAccount(account);
					user.setPassword(password);
					UserMapper userMap = session.getMapper(UserMapper.class);
					user = userMap.selectByAccount(user);// 检查账号
					if (user != null && user.getPassword().equals(password)) {// 密码验证通过，
						User update = new User();
						update.setId(user.getId());
						update.setName(request.getParameter("userName"));
						update.setNick(request.getParameter("userNick"));
						userMap.updateByPrimaryKeySelective(update);// 修改信息

						// 更新学生信息
						StudentInfoMapper StudentInfoMapper = session.getMapper(StudentInfoMapper.class);
						StudentInfo StudentInfo = StudentInfoMapper.selectByAccount(account);// 获取学生信息
						if (StudentInfo != null) {
							StudentInfo.setStudentName(request.getParameter("userName"));
							StudentInfo.setStudentNumber(request.getParameter("userXuehao"));
							StudentInfo.setStudentClass(request.getParameter("userClass"));
							StudentInfoMapper.updateByPrimaryKeySelective(StudentInfo);// 更新学生信息
						} else {
							StudentInfo = new StudentInfo();
							StudentInfo.setAccount(request.getParameter("account"));
							StudentInfo.setStudentName(request.getParameter("userName"));
							StudentInfo.setStudentNumber(request.getParameter("userXuehao"));
							StudentInfo.setStudentClass(request.getParameter("userClass"));
							StudentInfoMapper.insertSelective(StudentInfo);// 新的学生信息
						}

						responseJson res = new responseJson("status", responseJson.STATUS_OK);
						res.add("name", request.getParameter("userName"));
						if (StudentInfo != null) {
							res.add("userName", request.getParameter("userName"));
							res.add("userNick", request.getParameter("userNick"));
							res.add("userXuehao", request.getParameter("userXuehao"));
							res.add("userClass", request.getParameter("userClass"));
						}
						response.getWriter().append(res.response());
					} else {
						responseJson res = new responseJson("status", responseJson.STATUS_ERR);
						response.getWriter().append(res.response());
					}

					session.commit();
					session.close();
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}