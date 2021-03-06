package com.pscsw.dao;

import org.apache.ibatis.annotations.Param;

import com.pscsw.po.StudentReport;

public interface StudentReportMapper {
	StudentReport[] selectByStudentClass(String student_class);

	StudentReport[] selectBySubjectNumber(String subject_number);

	StudentReport[] selectKey(@Param("key") String key, @Param("student_number") String student_number);

	StudentReport[] selectByStudentNumber(String student_number);

	StudentReport[] selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	int insert(StudentReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	int insertSelective(StudentReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	StudentReport selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	int updateByPrimaryKeySelective(StudentReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table student_report
	 *
	 * @mbg.generated Mon Oct 08 16:08:39 CST 2018
	 */
	int updateByPrimaryKey(StudentReport record);
}