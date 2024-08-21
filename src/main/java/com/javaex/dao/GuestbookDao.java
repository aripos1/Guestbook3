package com.javaex.dao;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestVo;
@Repository
public class GuestbookDao {

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
	//삭제하기
	public boolean deleteGuest(int no, String inputPassword) {
	    boolean isDeleted = false;
		
		this.getConnection();
		try {
			String query = "";
			query += " DELETE FROM guestbook ";
			query += " where no = ? ";
			query += " AND password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, inputPassword); 
			 
			int delete = pstmt.executeUpdate();
		    isDeleted = delete > 0;  // 삭제된 행이 있으면 성공
			System.out.println("삭제하기");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return isDeleted;
		
	}
	
	
	
	// 게스트 한명만 불러오기
	public GuestVo getGuestOne(int no) {
		GuestVo guestVo = null;

		this.getConnection();

		try {
			String query = "";
			query += " select no ";
			query += " from guestbook ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			rs.next();
			int num = rs.getInt("no");
			guestVo = new GuestVo(num);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		
		return guestVo;

	}

	// 등록하기
	public int insertGuest(GuestVo guestVo) {
		int count = -1;

		this.getConnection();

		try {
			
			String query = "";
			query += " insert into guestbook ";
			query += " values(null, ? , ? , ? , ?) ";
			LocalDateTime regDate = (guestVo.getRegDate() != null) ? guestVo.getRegDate() : LocalDateTime.now();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			pstmt.setTimestamp(4, Timestamp.valueOf(regDate));

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return count;
	}

	// 리스트 불러오기

	public List<GuestVo> getGuestList() {

		List<GuestVo> guestList = new ArrayList<GuestVo>();

		this.getConnection();

		try {

			// sql문 준비 / 바인딩(말랑말랑) / 실행

			String query = "";
			query += " select no ";
			query += " 		,name ";
			query += " 		,content ";
			query += "    	,reg_date ";
			query += " from guestbook ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");

				GuestVo guestVo = new GuestVo(no, name, content, date);

				guestList.add(guestVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return guestList;

	}

}