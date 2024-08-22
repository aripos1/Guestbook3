package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestVo;

@Controller
public class GuestController {

	@Autowired
	private GuestbookService guestbookService;

	// 리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {

		List<GuestVo> exeGuestList = guestbookService.exeGuestList();
		model.addAttribute("guestList", exeGuestList);
		guestbookService.exeGuestList();

		return "addList";
	}

	// 추가
	@RequestMapping(value = "insert", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute GuestVo guestVo) {

		int count = guestbookService.exeWrite(guestVo);

		System.out.println(count);

		return "redirect:/list";
	}

	// 삭제 폼
	@RequestMapping(value = "deleteform", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteForm(@RequestParam("no") int no, Model model) {
		System.out.println("삭제폼 요청");

		GuestVo guestVo = guestbookService.exeDeleteForm(no);

		model.addAttribute("guestVo", guestVo);
		// deleteForm.jsp로 이동
		return "deleteForm";
	}

	// 삭제
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@RequestParam("no") int no, @RequestParam("password") String inputPassword) {
		System.out.println("delete 요청");

		// 데이터베이스에서 삭제 처리
		boolean isDeleted = guestbookService.exeDelete(no, inputPassword);

		if (isDeleted) {
			System.out.println("삭제 성공");
			// 삭제 성공 시 리스트 페이지로 리다이렉트
			return "redirect:/list";
		} else {

			return "" + no;
		}

	}

}
