package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;

@Repository
public class GuestbookDao {
	@Autowired
	private SqlSession sqlSession;
	// 필드
	// 필드
	

	public boolean deleteGuest(int no, String password) {
		System.out.println("delete 요청111");
		GuestVo guest = new GuestVo(no, password);
		int isDeleted = sqlSession.delete("guestbook.delete", guest);
		return isDeleted > 0;

	}

	// 게스트 한명만 불러오기
	public GuestVo getGuestOne(int no) {

		GuestVo guestVo = sqlSession.selectOne("guestbook.selectOne", no);
		System.out.println(guestVo);
		return guestVo;

	}

	// 등록하기
	public int insertGuest(GuestVo guestVo) {

		if (guestVo.getRegDate() == null) {
			guestVo.setRegDate(LocalDateTime.now());
		}

		int count = sqlSession.insert("guestbook.insert", guestVo);

		return count;
	}

	// 리스트 불러오기

	public List<GuestVo> getGuestList() {

		List<GuestVo> guestList = sqlSession.selectList("guestbook.selectList");
		System.out.println(guestList);
		return guestList;

	}

}