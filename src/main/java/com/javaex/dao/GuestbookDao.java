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
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guestbook";
	private String pw = "guestbook";

	// 생성자
	// 기본생성자 사용(그래서 생략)

	// 메소드 gs
	// 필드값을 외부에서 사용하면 안됨(그래서 생략)

	// 메소드 일반
	// DB연결 메소드
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}
	// 삭제하기

	public boolean deleteGuest(int no, String inputPassword) {
		Map<String, Object> params = new HashMap<>();
		params.put("no", no);
		params.put("password", inputPassword);
		
		int isDeleted = sqlSession.delete("guestbook.delete", params);

		return isDeleted >0;

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