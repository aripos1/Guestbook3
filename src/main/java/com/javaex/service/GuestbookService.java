package com.javaex.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookDao guestbookDao;

	public List<GuestVo> exeGuestList() {

		List<GuestVo> guestList = guestbookDao.getGuestList();

		return guestList;
	}

	public int exeWrite(GuestVo guestVo) {

		int count = guestbookDao.insertGuest(guestVo);

		return count;
	}

	public GuestVo exeDeleteForm(int no) {

		GuestVo guestVo = guestbookDao.getGuestOne(no);

		return guestVo;
	}

	public boolean exeDelete(int no, String password) {

		boolean isDeleted = guestbookDao.deleteGuest(no,password);

		return isDeleted;
	}

}
