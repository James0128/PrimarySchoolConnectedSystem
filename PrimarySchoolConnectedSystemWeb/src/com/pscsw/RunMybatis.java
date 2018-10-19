package com.pscsw;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.pscsw.dao.UserMapper;
import com.pscsw.po.User;

public class RunMybatis {
	public static void main(String[] args) {
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();

		// insert
		User user = new User();
		user.setName("Crossing Republic");
		UserMapper userMsg = session.getMapper(UserMapper.class);
		userMsg.insert(user);

		session.commit();
		session.close();
		System.out.println("---Data saved---");
	}
}
